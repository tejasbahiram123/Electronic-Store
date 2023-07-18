package com.lcwd.electronicstore.service;

import com.lcwd.electronicstore.dto.PageableResponce;
import com.lcwd.electronicstore.dto.ProductDto;

import java.util.List;

public interface ProductService {

    //create
    ProductDto createProduct(ProductDto productDto);

    //update
    ProductDto updateProduct(ProductDto productDto,String productId);

    //delete
    void deleteProduct(String productId);

    //get single product
    ProductDto getProduct(String productId);

    //get All
    PageableResponce<ProductDto> getAllProduct(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    //get All live
    PageableResponce<ProductDto> getAllLive(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    //search
    PageableResponce<ProductDto> searchByTitle(String subTitle,Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    //create product with category
    ProductDto createWithCategory(ProductDto productDto,String categoryId);

    //update category of product
    ProductDto updateCategory(String productId,String categoryId);

    //getAll product by category
    PageableResponce<ProductDto> getAllOfCategory(String categoryId,Integer pageNumber, Integer pageSize, String sortBy, String sortDir);


}
