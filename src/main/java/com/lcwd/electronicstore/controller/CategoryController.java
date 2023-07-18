package com.lcwd.electronicstore.controller;

import com.lcwd.electronicstore.dto.ApiResponceMessage;
import com.lcwd.electronicstore.dto.CategoryDto;
import com.lcwd.electronicstore.dto.PageableResponce;
import com.lcwd.electronicstore.dto.ProductDto;
import com.lcwd.electronicstore.service.CategoryService;
import com.lcwd.electronicstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    //create
    @PostMapping("/create")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto category = categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @PutMapping("/update/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @PathVariable String categoryId, @RequestBody CategoryDto categoryDto) {
        CategoryDto updatedCategory = categoryService.updateCategory(categoryDto, categoryId);
        return new ResponseEntity<>(updatedCategory, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PageableResponce<CategoryDto>> getAllCategory(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "ase", required = false) String sortDir
    ) {
        PageableResponce<CategoryDto> allCategory = categoryService.getAllCategory(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<PageableResponce<CategoryDto>>(allCategory, HttpStatus.FOUND);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponceMessage> deleteCategory(@PathVariable String categoryId) {
        categoryService.deleteCategory(categoryId);
        ApiResponceMessage responceMessage = ApiResponceMessage.builder().message("category deleted Successfully").success(true).status(HttpStatus.OK).build();
        return new ResponseEntity<>(responceMessage, HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable String categoryId) {
        CategoryDto category = categoryService.getCategory(categoryId);
        return new ResponseEntity<>(category, HttpStatus.FOUND);

    }

    //create product with category
    @PostMapping("/{categoryId}/products")
    public ResponseEntity<ProductDto> createProductWithCategory(@RequestBody ProductDto productDto,
                                                                @PathVariable("categoryId") String categoryId) {

        ProductDto productWithCategory = productService.createWithCategory(productDto, categoryId);

        return new ResponseEntity<>(productWithCategory, HttpStatus.CREATED);
    }

    //update category of product
    @PutMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<ProductDto> updateCategoryOfProduct(@RequestBody
                                                              @PathVariable String productId,
                                                              @PathVariable String categoryId) {
        ProductDto productDto = productService.updateCategory(productId, categoryId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);

    }

    //get products by categories
    @GetMapping("/{categoryId}/products")
    public ResponseEntity<PageableResponce<ProductDto>> getProductsByCategory(
            @PathVariable String categoryId,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "ase", required = false) String sortDir
    ) {

        PageableResponce<ProductDto> responce = productService.getAllOfCategory(categoryId,pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(responce, HttpStatus.OK);
    }

}
