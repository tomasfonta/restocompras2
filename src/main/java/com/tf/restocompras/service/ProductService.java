package com.tf.restocompras.service;

import com.tf.restocompras.error.NotFoundException;
import com.tf.restocompras.model.product.Product;
import com.tf.restocompras.model.product.ProductCreateRequestDto;
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

}
