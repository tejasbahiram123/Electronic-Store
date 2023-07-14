package com.lcwd.electronicstore.service;

import com.lcwd.electronicstore.dto.CategoryDto;
import com.lcwd.electronicstore.dto.PageableResponce;

public interface CategoryService {

    //create
    CategoryDto createCategory(CategoryDto categoryDto);

    //update
    CategoryDto updateCategory(CategoryDto categoryDto ,String categoryId);

    //getAll
    PageableResponce<CategoryDto> getAllCategory(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    //Get single
    CategoryDto getCategory(String categoryId);

    //delete
    void deleteCategory(String categoryId);

}
