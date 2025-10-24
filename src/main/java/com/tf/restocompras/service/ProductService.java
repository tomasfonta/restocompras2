package com.tf.restocompras.service;

import com.tf.restocompras.error.NotFoundException;
import com.tf.restocompras.model.product.Product;
import com.tf.restocompras.model.product.ProductCreateRequestDto;
import com.tf.restocompras.model.product.ProductMatchResponseDto;
import com.tf.restocompras.model.product.ProductResponseDto;
import com.tf.restocompras.repository.ProductRepository;
import com.tf.restocompras.repository.SubCategoryLevel2Repository;
import com.tf.restocompras.service.mapper.ProductMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final SubCategoryLevel2Repository subCategoryLevel2Repository;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper, SubCategoryLevel2Repository subCategoryLevel2Repository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.subCategoryLevel2Repository = subCategoryLevel2Repository;
    }

    public List<ProductResponseDto> findAll() {
        return productMapper.mapEntitiesToDtos(productRepository.findAll());
    }

    public ProductResponseDto findById(Long id) {
        return productMapper.mapEntityToDto(productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found with id " + id)));
    }

    public ProductResponseDto save(ProductCreateRequestDto productCreateRequestDto) {

        Product product = productMapper.mapDtoToEntity(productCreateRequestDto);

        product.setSubCategoryLevel2(subCategoryLevel2Repository.getById(productCreateRequestDto.subCategoryLevel2Id())
                .orElseThrow(() -> new NotFoundException("Category not found with id " + productCreateRequestDto.subCategoryLevel2Id())));
        Product productSaved = productRepository.save(product);

        return productMapper.mapEntityToDto(productSaved);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    /**
     * Finds the best matching product based on a search query (name or description).
     * Considers the product name and category hierarchy for scoring.
     *
     * @param searchQuery the search term to match against product names and categories
     * @return ProductMatchResponseDto with the best matching product and its score
     */
    public ProductMatchResponseDto findBestMatch(String searchQuery) {
        List<Product> allProducts = productRepository.findAll();

        if (allProducts.isEmpty()) {
            throw new NotFoundException("No products found in the database");
        }

        String normalizedQuery = normalizeString(searchQuery);
        String[] queryWords = normalizedQuery.split("\\s+");

        // Calculate match score for each product
        Product bestMatch = null;
        double bestScore = 0.0;

        for (Product product : allProducts) {
            double score = calculateMatchScore(product, normalizedQuery, queryWords);

            if (score > bestScore) {
                bestScore = score;
                bestMatch = product;
            }
        }

        if (bestMatch == null || bestScore == 0.0) {
            throw new NotFoundException("No matching product found for query: " + searchQuery);
        }

        // Build response DTO
        return new ProductMatchResponseDto(
                bestMatch.getId(),
                bestMatch.getName(),
                bestMatch.getSubCategoryLevel2().getSubCategoryLevel1().getCategory().getName(),
                bestMatch.getSubCategoryLevel2().getSubCategoryLevel1().getName(),
                bestMatch.getSubCategoryLevel2().getName(),
                bestScore
        );
    }

    /**
     * Calculates match score for a product based on the search query.
     * Scoring considers:
     * - Exact product name match (100 points)
     * - Partial product name match (50 points per word)
     * - SubCategory Level 2 match (30 points)
     * - SubCategory Level 1 match (20 points)
     * - Category match (10 points)
     */
    private double calculateMatchScore(Product product, String normalizedQuery, String[] queryWords) {
        double score = 0.0;

        String productName = normalizeString(product.getName());
        String subCategoryLevel2 = normalizeString(product.getSubCategoryLevel2().getName());
        String subCategoryLevel1 = normalizeString(product.getSubCategoryLevel2().getSubCategoryLevel1().getName());
        String category = normalizeString(product.getSubCategoryLevel2().getSubCategoryLevel1().getCategory().getName());

        // Exact match with product name (highest priority)
        if (productName.equals(normalizedQuery)) {
            score += 100.0;
        }

        // Partial word matches in product name
        for (String word : queryWords) {
            if (word.length() > 2) { // Ignore very short words
                if (productName.contains(word)) {
                    score += 50.0;
                }
                if (subCategoryLevel2.contains(word)) {
                    score += 30.0;
                }
                if (subCategoryLevel1.contains(word)) {
                    score += 20.0;
                }
                if (category.contains(word)) {
                    score += 10.0;
                }
            }
        }

        // Bonus for query being contained in product name
        if (productName.contains(normalizedQuery)) {
            score += 40.0;
        }

        return score;
    }

    /**
     * Normalizes a string by converting to lowercase and removing accents.
     */
    private String normalizeString(String input) {
        if (input == null) return "";

        String normalized = java.text.Normalizer.normalize(input, java.text.Normalizer.Form.NFD);
        normalized = normalized.replaceAll("\\p{M}", ""); // Remove diacritical marks
        return normalized.toLowerCase().trim();
    }

}
