package com.tf.restocompras.service;

import com.tf.restocompras.model.excel.ItemUpdateResponse;
import com.tf.restocompras.model.item.Item;
import com.tf.restocompras.model.product.Product;
import com.tf.restocompras.model.supplier.Supplier;
import com.tf.restocompras.model.unit.Unit;
import com.tf.restocompras.repository.ItemRepository;
import com.tf.restocompras.repository.ProductRepository;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.Normalizer;
import java.util.*;


@Service
public class ExcelService {

    private static final Logger log = LoggerFactory.getLogger(ExcelService.class);

    private final ItemRepository itemRepository;
    private final ProductRepository productRepository;

    public ExcelService(ItemRepository itemRepository, ProductRepository productRepository) {
        this.itemRepository = itemRepository;
        this.productRepository = productRepository;
    }

    public ItemUpdateResponse processAndLog(MultipartFile file) {
        return updateItemsFromExcel(file);
    }

    /**
     * Reads an Excel file where the first row contains headers:
     * ID, Nombre, Marca, Descripción, Precio, Imagen, Producto ID, Unidad, Cantidad
     * Each subsequent row represents an item to update or create.
     * If ID is empty, a new item will be created using the supplier from the first existing item found.
     *
     * @param file the Excel file to process
     * @return ItemUpdateResponse containing lists of updated and created item IDs
     */
    public ItemUpdateResponse updateItemsFromExcel(MultipartFile file) {
        List<Long> updatedItemIds = new ArrayList<>();
        List<Long> createdItemIds = new ArrayList<>();

        Supplier supplier = null;

        try (InputStream is = file.getInputStream(); Workbook workbook = WorkbookFactory.create(is)) {
            DataFormatter formatter = new DataFormatter();
            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

            for (Sheet sheet : workbook) {
                log.info("Updating items from sheet: {}", sheet.getSheetName());

                Row headerRow = sheet.getRow(0);
                if (headerRow == null) {
                    log.warn("Sheet {} has no header row, skipping", sheet.getSheetName());
                    continue;
                }

                // Build a map of normalized column name -> column index
                Map<String, Integer> columnMap = new HashMap<>();
                for (int cn = 0; cn < headerRow.getLastCellNum(); cn++) {
                    Cell cell = headerRow.getCell(cn, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    String columnName = normalizeColumnName(formatter.formatCellValue(cell, evaluator));
                    if (!columnName.isEmpty()) {
                        columnMap.put(columnName, cn);
                    }
                }

                // Normalized column keys
                String KEY_ID = "ID";
                String KEY_NOMBRE = "NOMBRE";
                String KEY_MARCA = "MARCA";
                String KEY_DESCRIPCION = "DESCRIPCION";
                String KEY_PRECIO = "PRECIO";
                String KEY_IMAGEN = "IMAGEN";
                String KEY_PRODUCTO_ID = "PRODUCTOID";
                String KEY_UNIDAD = "UNIDAD";
                String KEY_CANTIDAD = "CANTIDAD";

                // Process each data row
                int lastRowNum = sheet.getLastRowNum();
                for (int rowNum = 1; rowNum <= lastRowNum; rowNum++) {
                    Row row = sheet.getRow(rowNum);
                    if (row == null) continue;

                    try {
                        // Get ID from first column (if exists)
                        Integer idColIndex = columnMap.get(KEY_ID);
                        String idStr = "";
                        if (idColIndex != null) {
                            idStr = formatter.formatCellValue(
                                    row.getCell(idColIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK),
                                    evaluator
                            ).trim();
                        }

                        Item item;
                        boolean isNewItem = false;

                        // If ID is empty, create a new item
                        if (idStr.isEmpty()) {
                            log.debug("Row {} has no ID, creating new item", rowNum);

                            // Check if we have a supplier from a previous item
                            if (supplier == null) {
                                log.warn("Row {} has no ID and no supplier has been determined yet, skipping", rowNum);
                                continue;
                            }

                            item = new Item();
                            item.setSupplier(supplier);
                            isNewItem = true;
                        } else {
                            // Try to parse and find existing item
                            Long itemId = parseId(idStr);
                            if (itemId == null) {
                                log.warn("Unable to parse ID '{}' on row {}, skipping", idStr, rowNum);
                                continue;
                            }

                            Optional<Item> itemOpt = itemRepository.findById(itemId);
                            if (!itemOpt.isPresent()) {
                                log.warn("Item with ID {} not found (row {}), skipping", itemId, rowNum);
                                continue;
                            }
                            item = itemOpt.get();

                            // Obtain supplier from the first existing item found
                            if (supplier == null && item.getSupplier() != null) {
                                supplier = item.getSupplier();
                                log.info("Using supplier '{}' (ID: {}) from existing items for new item creation",
                                        supplier.getName(), supplier.getId());
                            }
                        }

                        boolean updated = false;

                        // Update Nombre
                        if (columnMap.containsKey(KEY_NOMBRE)) {
                            String value = getCellValue(row, columnMap.get(KEY_NOMBRE), formatter, evaluator);
                            if (!value.isEmpty()) {
                                item.setName(value);
                                updated = true;
                            }
                        }

                        // Update Marca
                        if (columnMap.containsKey(KEY_MARCA)) {
                            String value = getCellValue(row, columnMap.get(KEY_MARCA), formatter, evaluator);
                            if (!value.isEmpty()) {
                                item.setBrand(value);
                                updated = true;
                            }
                        }

                        // Update Descripción
                        if (columnMap.containsKey(KEY_DESCRIPCION)) {
                            String value = getCellValue(row, columnMap.get(KEY_DESCRIPCION), formatter, evaluator);
                            if (!value.isEmpty()) {
                                item.setDescription(value);
                                updated = true;
                            }
                        }

                        // Update Precio
                        if (columnMap.containsKey(KEY_PRECIO)) {
                            String value = getCellValue(row, columnMap.get(KEY_PRECIO), formatter, evaluator);
                            if (!value.isEmpty()) {
                                Double price = parseDouble(value);
                                if (price != null) {
                                    item.setPrice(price);
                                    updated = true;
                                } else {
                                    log.warn("Invalid price '{}' on row {}", value, rowNum);
                                }
                            }
                        }

                        // Update Imagen
                        if (columnMap.containsKey(KEY_IMAGEN)) {
                            String value = getCellValue(row, columnMap.get(KEY_IMAGEN), formatter, evaluator);
                            if (!value.isEmpty()) {
                                item.setImage(value);
                                updated = true;
                            }
                        }

                        // Update Producto ID
                        if (columnMap.containsKey(KEY_PRODUCTO_ID)) {
                            String value = getCellValue(row, columnMap.get(KEY_PRODUCTO_ID), formatter, evaluator);
                            if (!value.isEmpty()) {
                                Long productId = parseId(value);
                                if (productId != null) {
                                    Optional<Product> productOpt = productRepository.findById(productId);
                                    if (productOpt.isPresent()) {
                                        item.setProduct(productOpt.get());
                                        updated = true;
                                    } else {
                                        log.warn("Product with ID {} not found on row {}", productId, rowNum);
                                    }
                                } else {
                                    log.warn("Invalid product ID '{}' on row {}", value, rowNum);
                                }
                            }
                        }

                        // Update Unidad
                        if (columnMap.containsKey(KEY_UNIDAD)) {
                            String value = getCellValue(row, columnMap.get(KEY_UNIDAD), formatter, evaluator);
                            if (!value.isEmpty()) {
                                try {
                                    Unit unit = Unit.valueOf(value.toUpperCase(Locale.ROOT));
                                    item.setUnit(unit);
                                    updated = true;
                                } catch (IllegalArgumentException ex) {
                                    log.warn("Unknown unit '{}' on row {}", value, rowNum);
                                }
                            }
                        }

                        // Update Cantidad
                        if (columnMap.containsKey(KEY_CANTIDAD)) {
                            String value = getCellValue(row, columnMap.get(KEY_CANTIDAD), formatter, evaluator);
                            if (!value.isEmpty()) {
                                Double quantity = parseDouble(value);
                                if (quantity != null) {
                                    item.setQuantity(quantity);
                                    updated = true;
                                } else {
                                    log.warn("Invalid quantity '{}' on row {}", value, rowNum);
                                }
                            }
                        }

                        // Save if any field was updated or if it's a new item
                        if (isNewItem) {
                            // Validate required fields for new items
                            if (item.getName() == null || item.getName().isEmpty()) {
                                log.warn("Row {} is missing required field 'Nombre', skipping creation", rowNum);
                                continue;
                            }
                            if (item.getBrand() == null || item.getBrand().isEmpty()) {
                                log.warn("Row {} is missing required field 'Marca', skipping creation", rowNum);
                                continue;
                            }
                            if (item.getDescription() == null || item.getDescription().isEmpty()) {
                                log.warn("Row {} is missing required field 'Descripción', skipping creation", rowNum);
                                continue;
                            }
                            if (item.getUnit() == null) {
                                log.warn("Row {} is missing required field 'Unidad', skipping creation", rowNum);
                                continue;
                            }
                            if (item.getQuantity() == null) {
                                log.warn("Row {} is missing required field 'Cantidad', skipping creation", rowNum);
                                continue;
                            }

                            Item savedItem = itemRepository.save(item);
                            createdItemIds.add(savedItem.getId());
                            log.debug("Created new item with ID {} from row {}", savedItem.getId(), rowNum);
                        } else if (updated) {
                            Item savedItem = itemRepository.save(item);
                            updatedItemIds.add(savedItem.getId());
                            log.debug("Updated item with ID {} from row {}", savedItem.getId(), rowNum);
                        }

                    } catch (Exception ex) {
                        log.error("Error processing row {}: {}", rowNum, ex.getMessage(), ex);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Failed to update items from Excel: {}", e.getMessage(), e);
            throw new IllegalArgumentException("Invalid excel file: " + e.getMessage());
        }

        log.info("Finished updating items from Excel. Total updated: {}, Total created: {}", updatedItemIds.size(), createdItemIds.size());

        return ItemUpdateResponse.builder()
                .updatedItemIds(updatedItemIds)
                .createdItemIds(createdItemIds)
                .totalUpdated(updatedItemIds.size())
                .totalCreated(createdItemIds.size())
                .totalProcessed(updatedItemIds.size() + createdItemIds.size())
                .build();
    }

    /**
     * Normalizes column names by removing accents, special characters, and converting to uppercase.
     */
    private String normalizeColumnName(String columnName) {
        if (columnName == null) return "";
        // Remove accents
        String normalized = Normalizer.normalize(columnName, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        // Remove non-alphanumeric characters and convert to uppercase
        return normalized.replaceAll("[^A-Za-z0-9]", "").toUpperCase(Locale.ROOT);
    }

    /**
     * Gets the cell value as a trimmed string.
     */
    private String getCellValue(Row row, int columnIndex, DataFormatter formatter, FormulaEvaluator evaluator) {
        Cell cell = row.getCell(columnIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
        return formatter.formatCellValue(cell, evaluator).trim();
    }

    /**
     * Parses a string to a Long ID, handling both integer and decimal formats.
     */
    private Long parseId(String value) {
        try {
            BigDecimal bd = new BigDecimal(value.replace(",", "."));
            return bd.longValue();
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Parses a string to a Double, handling both comma and dot as decimal separators.
     */
    private Double parseDouble(String value) {
        try {
            return Double.parseDouble(value.replace(",", "."));
        } catch (Exception ex) {
            return null;
        }
    }
}

