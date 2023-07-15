package com.lcwd.electronicstore.service.impl;

import com.lcwd.electronicstore.dto.CategoryDto;
import com.lcwd.electronicstore.dto.PageableResponce;
import com.lcwd.electronicstore.entity.Category;
import com.lcwd.electronicstore.exception.ResourceNotFoundException;
import com.lcwd.electronicstore.helper.Helper;
import com.lcwd.electronicstore.repository.CategoryRepository;
import com.lcwd.electronicstore.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepo;
    @Autowired
    private ModelMapper mapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        String randomId = UUID.randomUUID().toString();
        categoryDto.setCategoryId(randomId);
        Category category = this.mapper.map(categoryDto, Category.class);
        Category newCategory = this.categoryRepo.save(category);
        return this.mapper.map(newCategory, CategoryDto.class);

    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, String categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category not found with this Id"));
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());
        Category updated_category = this.categoryRepo.save(category);
        return this.mapper.map(updated_category, CategoryDto.class);

    }

    @Override
    public PageableResponce<CategoryDto> getAllCategory(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        PageRequest pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Category> page = this.categoryRepo.findAll(pageable);
        PageableResponce<CategoryDto> pageableResponce = Helper.getPageableResponce(page, CategoryDto.class);

        return pageableResponce;
    }

    @Override
    public CategoryDto getCategory(String categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category not found with this Id.."));
        return this.mapper.map(category, CategoryDto.class);
    }

    @Override
    public void deleteCategory(String categoryId) {

        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category not found with this Id"));
        this.categoryRepo.delete(category);
    }
}
