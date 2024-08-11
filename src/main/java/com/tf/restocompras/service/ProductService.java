package com.tf.restocompras.service;

import com.tf.restocompras.error.NotFoundException;
import com.tf.restocompras.model.product.ProductCreateRequestDto;
import com.tf.restocompras.model.product.ProductResponseDto;
import com.tf.restocompras.model.product.ProductUpdateRequestDto;
import com.tf.restocompras.repository.ProductRepository;
import com.tf.restocompras.service.mapper.ProductMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class ProductService {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public ProductService(ProductMapper productMapper,
                          ProductRepository productRepository,
                          CategoryService categoryService) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    public List<ProductResponseDto> findAll() {
        return productRepository.findAll().stream()
                .map(productMapper::mapEntityToDto).collect(Collectors.toList());
    }

    public ProductResponseDto findById(Long id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product with id: " + id + " not found"));
        return productMapper.mapEntityToDto(product);
    }

    public ProductResponseDto save(ProductCreateRequestDto productCreateRequestDto) {
        var product = productMapper.mapDtoToEntity(productCreateRequestDto);
        var category = categoryService.getCategoryByName(productCreateRequestDto.getCategoryName());
        product.setCategory(category);
        productRepository.save(product);
        return productMapper.mapEntityToDto(product);
    }

    public ProductResponseDto update(ProductUpdateRequestDto productCreateRequestDto) {
        var product = productRepository.findById(productCreateRequestDto.getId())
                .orElseThrow(() -> new NotFoundException("Product with id: " + productCreateRequestDto.getId() + " not found"));

        var category = categoryService.getCategoryByName(productCreateRequestDto.getCategoryName());

        product.setCategory(category);
        product.setName(productCreateRequestDto.getName());
        product.setPrice(productCreateRequestDto.getPrice());
        product.setDescription(productCreateRequestDto.getDescription());
        product.setImage(productCreateRequestDto.getImage());
        product.setPrice(productCreateRequestDto.getPrice());

        productRepository.save(product);
        return productMapper.mapEntityToDto(product);
    }

    public void deleteById(Long id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product with id: " + id + " not found"));
        productRepository.delete(product);
    }
}