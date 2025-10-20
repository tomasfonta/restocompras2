package com.tf.restocompras.service;

import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
public class ExcelLogService {

    private static final Logger log = LoggerFactory.getLogger(ExcelLogService.class);

    public int processAndLog(MultipartFile file) {
        int totalRows = 0;
        try (InputStream is = file.getInputStream(); Workbook workbook = WorkbookFactory.create(is)) {
            DataFormatter formatter = new DataFormatter();
            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

            for (Sheet sheet : workbook) {
                log.info("Processing sheet: {}", sheet.getSheetName());
                for (Row row : sheet) {
                    StringBuilder sb = new StringBuilder();
                    boolean first = true;
                    for (int cn = 0; cn < row.getLastCellNum(); cn++) {
                        if (!first) sb.append(" | ");
                        Cell cell = row.getCell(cn, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        sb.append(formatter.formatCellValue(cell, evaluator));
                        first = false;
                    }
                    log.info("Row {} -> {}", row.getRowNum(), sb.toString());
                    totalRows++;
                }
            }
        } catch (Exception e) {
            log.error("Failed to process Excel file: {}", e.getMessage(), e);
            throw new IllegalArgumentException("Invalid excel file: " + e.getMessage());
        }
        log.info("Finished processing Excel. Total rows: {}", totalRows);
        return totalRows;
    }
}

