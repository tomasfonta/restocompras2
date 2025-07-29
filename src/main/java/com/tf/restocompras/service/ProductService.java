package com.tf.restocompras.service;

import com.tf.restocompras.error.NotFoundException;
import com.tf.restocompras.model.product.Product;
import com.tf.restocompras.model.product.ProductCreateRequestDto;
import com.tf.restocompras.model.product.ProductResponseDto;
import com.tf.restocompras.repository.ProductRepository;
import com.tf.restocompras.service.mapper.ProductMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
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
        Product productSaved = productRepository.save(product);

        return productMapper.mapEntityToDto(productSaved);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}

