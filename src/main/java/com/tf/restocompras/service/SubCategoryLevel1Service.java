package com.tf.restocompras.service;

import com.tf.restocompras.error.NotFoundException;
import com.tf.restocompras.model.category.SubCategoryLevel1;
import com.tf.restocompras.model.category.SubCategoryLevel1CreateRequestDto;
import com.tf.restocompras.model.category.SubCategoryLevel1ResponseDto;
import com.tf.restocompras.repository.CategoryRepository;
import com.tf.restocompras.repository.SubCategoryLevel1Repository;
import com.tf.restocompras.service.mapper.SubCategoryLevel1Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubCategoryLevel1Service {
    private final SubCategoryLevel1Repository subCategoryLevel1Repository;
    private final CategoryRepository categoryRepository;
    private final SubCategoryLevel1Mapper subCategoryLevel1Mapper;

    public SubCategoryLevel1Service(SubCategoryLevel1Repository subCategoryLevel1Repository, CategoryRepository categoryRepository, SubCategoryLevel1Mapper subCategoryLevel1Mapper) {
        this.subCategoryLevel1Repository = subCategoryLevel1Repository;
        this.categoryRepository = categoryRepository;
        this.subCategoryLevel1Mapper = subCategoryLevel1Mapper;
    }

    public List<SubCategoryLevel1ResponseDto> getAllSubCategoriesLevel1() {
        return subCategoryLevel1Repository.findAll().stream()
                .map(subCategoryLevel1Mapper::mapEntityToDto)
                .toList();
    }

    public SubCategoryLevel1ResponseDto getSubCategoryLevel1ById(Long id) {
        return subCategoryLevel1Repository.findById(id)
                .map(subCategoryLevel1Mapper::mapEntityToDto)
                .orElseThrow(() -> new NotFoundException("SubCategoryLevel1: " + id + " not found"));
    }

    public SubCategoryLevel1ResponseDto createSubCategoryLevel1(SubCategoryLevel1CreateRequestDto dto) {
        var category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new NotFoundException("Category: " + dto.categoryId() + " not found"));
        var subCategory = SubCategoryLevel1.builder()
                .name(dto.name())
                .category(category)
                .build();
        return subCategoryLevel1Mapper.mapEntityToDto(subCategoryLevel1Repository.save(subCategory));
    }

    public void deleteSubCategoryLevel1(Long id) {
        subCategoryLevel1Repository.findById(id)
                .orElseThrow(() -> new NotFoundException("SubCategoryLevel1: " + id + " not found"));
        subCategoryLevel1Repository.deleteById(id);
    }
}
