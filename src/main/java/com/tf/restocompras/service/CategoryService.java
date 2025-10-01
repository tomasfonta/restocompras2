package com.tf.restocompras.service;

import com.tf.restocompras.error.NotFoundException;
import com.tf.restocompras.model.category.Category;
import com.tf.restocompras.model.category.CategoryCreateRequestDto;
import com.tf.restocompras.model.category.CategoryResponseDto;
import com.tf.restocompras.model.category.CategoryUpdateRequestDto;
import com.tf.restocompras.repository.CategoryRepository;
import com.tf.restocompras.service.mapper.CategoryMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }


    public List<CategoryResponseDto> getAllCategories() {

        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(categoryMapper::mapEntityToDto).collect(Collectors.toList());
    }

    public CategoryResponseDto getCategoryById(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category : " + id + " not found"));

        return categoryMapper.mapEntityToDto(category);

    }

    public CategoryResponseDto updateCategory(CategoryUpdateRequestDto categoryUpdateRequestDto) {
        categoryRepository.findById(categoryUpdateRequestDto.id())
                .orElseThrow(() -> new NotFoundException("Category : " + categoryUpdateRequestDto.id() + " not found"));
        Category category = categoryRepository.save(categoryMapper.mapDtoToEntity(categoryUpdateRequestDto));
        return categoryMapper.mapEntityToDto(category);
    }

    public CategoryResponseDto createCategory(CategoryCreateRequestDto categoryCreateRequestDto) {
        Category category = categoryRepository.save(categoryMapper.mapDtoToEntity(categoryCreateRequestDto));
        return categoryMapper.mapEntityToDto(category);
    }

    public void deleteCategory(Long id) {
        categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category : " + id + " not found"));
        categoryRepository.deleteById(id);
    }

    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Category : " + name + " not found"));
    }
}