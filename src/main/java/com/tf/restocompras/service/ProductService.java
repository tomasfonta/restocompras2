package com.tf.restocompras.service;

import com.tf.restocompras.error.NotFoundException;
import com.tf.restocompras.model.product.Product;
import com.tf.restocompras.model.product.ProductCreateRequestDto;
import com.tf.restocompras.model.product.ProductResponseDto;
import com.tf.restocompras.repository.CategoryRepository;
import com.tf.restocompras.repository.ProductRepository;
import com.tf.restocompras.service.mapper.ProductMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.categoryRepository = categoryRepository;
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

        product.setCategory(  categoryRepository.getById(productCreateRequestDto.categoryId())
        .orElseThrow(() -> new NotFoundException("Category not found with id " + productCreateRequestDto.categoryId())));
        Product productSaved = productRepository.save(product);

        return productMapper.mapEntityToDto(productSaved);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
