package com.lcwd.electronicstore.service.impl;

import com.lcwd.electronicstore.dto.CategoryDto;
import com.lcwd.electronicstore.dto.PageableResponce;
import com.lcwd.electronicstore.entity.Category;
import com.lcwd.electronicstore.exception.ResourceNotFoundException;
import com.lcwd.electronicstore.helper.Helper;
import com.lcwd.electronicstore.repository.CategoryRepository;
import com.lcwd.electronicstore.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepo;
    @Autowired
    private ModelMapper mapper;

    Logger logger= LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        logger.info("Initiating logic for create Category {} ");
        String randomId = UUID.randomUUID().toString();
        categoryDto.setCategoryId(randomId);
        Category category = this.mapper.map(categoryDto, Category.class);
        Category newCategory = this.categoryRepo.save(category);
        logger.info("complete logic for create Category {} ");
        return this.mapper.map(newCategory, CategoryDto.class);

    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, String categoryId) {
        logger.info("Initiating logic for update Category {} ,"+categoryId);
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category not found with this Id"));
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());
        Category updated_category = this.categoryRepo.save(category);
        logger.info("complete logic for update Category {} ,"+categoryId);
        return this.mapper.map(updated_category, CategoryDto.class);

    }

    @Override
    public PageableResponce<CategoryDto> getAllCategory(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        logger.info("Initiating logic for getAll Category {} ,");
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        PageRequest pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Category> page = this.categoryRepo.findAll(pageable);
        logger.info("complete logic for getAll Category {} ,");
        PageableResponce<CategoryDto> pageableResponce = Helper.getPageableResponce(page, CategoryDto.class);

        return pageableResponce;
    }

    @Override
    public CategoryDto getCategory(String categoryId) {
        logger.info("Initiating logic for get Category {} ,"+categoryId);
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category not found with this Id.."));
        logger.info("complete logic for get Category {} ,"+categoryId);
        return this.mapper.map(category, CategoryDto.class);
    }

    @Override
    public void deleteCategory(String categoryId) {

        logger.info("Initiating logic for delete Category {} ,"+categoryId);
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category not found with this Id"));
        logger.info("complete logic for delete Category {} ,"+categoryId);
        this.categoryRepo.delete(category);
    }
}
