package com.lcwd.electronicstore.controller;

import com.lcwd.electronicstore.constant.AppConstants;
import com.lcwd.electronicstore.dto.ApiResponceMessage;
import com.lcwd.electronicstore.dto.CategoryDto;
import com.lcwd.electronicstore.dto.PageableResponce;
import com.lcwd.electronicstore.dto.ProductDto;
import com.lcwd.electronicstore.service.CategoryService;
import com.lcwd.electronicstore.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    private Logger logger= LoggerFactory.getLogger(CategoryController.class);
    /**
     * @apiNote This method for create Category
     * @param categoryDto
     * @return created category
     */
    @PostMapping("/create")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        logger.info("start request for create Category");
        CategoryDto category = categoryService.createCategory(categoryDto);
        logger.info("complete request for create Category");
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    /**
     * @auther Tejas Bahiram
     * @apiNote This method for update Category by Id
     * @param categoryId
     * @param categoryDto
     * @return updated category
     */
    @PutMapping("/update/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @PathVariable String categoryId, @RequestBody CategoryDto categoryDto) {
        logger.info("start request for update Category {}",categoryId);
        CategoryDto updatedCategory = categoryService.updateCategory(categoryDto, categoryId);
        logger.info("complete request for update Category {}",categoryId);
        return new ResponseEntity<>(updatedCategory, HttpStatus.CREATED);
    }

    /**
     * @apiNote This method for get all category
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return list of category
     */
    @GetMapping
    public ResponseEntity<PageableResponce<CategoryDto>> getAllCategory(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY_TITLE, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue =AppConstants.SORT_DIR, required = false) String sortDir
    ) {
        logger.info("start request for getAll Category");
        PageableResponce<CategoryDto> allCategory = categoryService.getAllCategory(pageNumber, pageSize, sortBy, sortDir);
        logger.info("complete request for getAll Category");
        return new ResponseEntity<PageableResponce<CategoryDto>>(allCategory, HttpStatus.FOUND);
    }

    /**
     * @apiNote This method for delete Category by provided Id
     * @param categoryId
     * @return message
     */
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponceMessage> deleteCategory(@PathVariable String categoryId) {
        logger.info("start request for delete Category {}",categoryId);
        categoryService.deleteCategory(categoryId);
        ApiResponceMessage responceMessage = ApiResponceMessage.builder().message(AppConstants.CATEGORY_DELETED).success(true).status(HttpStatus.OK).build();
        logger.info("complete request for delete Category {}",categoryId);
        return new ResponseEntity<>(responceMessage, HttpStatus.OK);
    }

    /**
     * @apiNote This method for get Category By provided Id
     * @param categoryId
     * @return category
     */
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable String categoryId) {
        logger.info("start request for get Category {}",categoryId);
        CategoryDto category = categoryService.getCategory(categoryId);
        logger.info("complete request for get Category {}",categoryId);
        return new ResponseEntity<>(category, HttpStatus.FOUND);

    }


    /**
     * @apiNote This method create product with category
     * @param productDto
     * @param categoryId
     * @return
     */
    @PostMapping("/{categoryId}/products")
    public ResponseEntity<ProductDto> createProductWithCategory(@RequestBody ProductDto productDto,
                                                                @PathVariable("categoryId") String categoryId) {
        logger.info("start request for create product with  Category {}",categoryId);
        ProductDto productWithCategory = productService.createWithCategory(productDto, categoryId);
        logger.info("complete request for create product with Category {}",categoryId);
        return new ResponseEntity<>(productWithCategory, HttpStatus.CREATED);
    }

    /**
     * @apiNote This method for update category of product
     * @param productId
     * @param categoryId
     * @return updated category of product
     */
    @PutMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<ProductDto> updateCategoryOfProduct(@RequestBody
                                                              @PathVariable String productId,
                                                              @PathVariable String categoryId) {
        logger.info("start request for update Category of product {}",categoryId);
        ProductDto productDto = productService.updateCategory(productId, categoryId);
        logger.info("complete request for update Category of product {}",categoryId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);

    }

    /**
     * @apiNote This method for get products by category
     * @param categoryId
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return category of products
     */
    @GetMapping("/{categoryId}/products")
    public ResponseEntity<PageableResponce<ProductDto>> getProductsByCategory(
            @PathVariable String categoryId,
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY_TITLE, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
    ) {
        logger.info("start request for get products by Category {}",categoryId);
        PageableResponce<ProductDto> responce = productService.getAllOfCategory(categoryId,pageNumber,pageSize,sortBy,sortDir);
        logger.info("complete request for get products by Category {}",categoryId);
        return new ResponseEntity<>(responce, HttpStatus.OK);
    }

}
