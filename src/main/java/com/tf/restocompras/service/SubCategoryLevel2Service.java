package com.tf.restocompras.service;

import com.tf.restocompras.error.NotFoundException;
import com.tf.restocompras.model.category.SubCategoryLevel2;
import com.tf.restocompras.model.category.SubCategoryLevel2CreateRequestDto;
import com.tf.restocompras.model.category.SubCategoryLevel2ResponseDto;
import com.tf.restocompras.repository.SubCategoryLevel1Repository;
import com.tf.restocompras.repository.SubCategoryLevel2Repository;
import com.tf.restocompras.service.mapper.SubCategoryLevel2Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubCategoryLevel2Service {
    private final SubCategoryLevel2Repository subCategoryLevel2Repository;
    private final SubCategoryLevel1Repository subCategoryLevel1Repository;
    private final SubCategoryLevel2Mapper subCategoryLevel2Mapper;

    public SubCategoryLevel2Service(SubCategoryLevel2Repository subCategoryLevel2Repository, SubCategoryLevel1Repository subCategoryLevel1Repository, SubCategoryLevel2Mapper subCategoryLevel2Mapper) {
        this.subCategoryLevel2Repository = subCategoryLevel2Repository;
        this.subCategoryLevel1Repository = subCategoryLevel1Repository;
        this.subCategoryLevel2Mapper = subCategoryLevel2Mapper;
    }

    public List<SubCategoryLevel2ResponseDto> getAllSubCategoriesLevel2() {
        return subCategoryLevel2Repository.findAll().stream()
                .map(subCategoryLevel2Mapper::mapEntityToDto)
                .toList();
    }

    public SubCategoryLevel2ResponseDto getSubCategoryLevel2ById(Long id) {
        return subCategoryLevel2Repository.findById(id)
                .map(subCategoryLevel2Mapper::mapEntityToDto)
                .orElseThrow(() -> new NotFoundException("SubCategoryLevel2: " + id + " not found"));
    }

    public SubCategoryLevel2ResponseDto createSubCategoryLevel2(SubCategoryLevel2CreateRequestDto dto) {
        var subCategoryL1 = subCategoryLevel1Repository.findById(dto.subCategoryLevel1Id())
                .orElseThrow(() -> new NotFoundException("SubCategoryLevel1: " + dto.subCategoryLevel1Id() + " not found"));
        var subCategory = SubCategoryLevel2.builder()
                .name(dto.name())
                .subCategoryLevel1(subCategoryL1)
                .build();
        return subCategoryLevel2Mapper.mapEntityToDto(subCategoryLevel2Repository.save(subCategory));
    }

    public void deleteSubCategoryLevel2(Long id) {
        subCategoryLevel2Repository.findById(id)
                .orElseThrow(() -> new NotFoundException("SubCategoryLevel2: " + id + " not found"));
        subCategoryLevel2Repository.deleteById(id);
    }
}

