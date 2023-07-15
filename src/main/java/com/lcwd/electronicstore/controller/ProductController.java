package com.lcwd.electronicstore.controller;

import com.lcwd.electronicstore.dto.ApiResponceMessage;
import com.lcwd.electronicstore.dto.PageableResponce;
import com.lcwd.electronicstore.dto.ProductDto;
import com.lcwd.electronicstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        ProductDto product = productService.createProduct(productDto);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@Valid @RequestBody ProductDto productDto, @PathVariable String productId) {
        ProductDto updatedProduct = productService.updateProduct(productDto, productId);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable String productId) {
        ProductDto updatedProduct = productService.getProduct(productId);
        return new ResponseEntity<>(updatedProduct, HttpStatus.FOUND);
    }

    @GetMapping
    public ResponseEntity<PageableResponce<ProductDto>> getAllProduct(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        PageableResponce<ProductDto> allProductS = productService.getAllProduct(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allProductS, HttpStatus.FOUND);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponceMessage> deleteProduct(@PathVariable String productId) {
        productService.deleteProduct(productId);
        ApiResponceMessage responce = ApiResponceMessage.builder().message("Product Deleted successful")
                .status(HttpStatus.OK)
                .success(true).build();
        return new ResponseEntity<ApiResponceMessage>(responce, HttpStatus.OK);
    }
    @GetMapping("/live")
    public ResponseEntity<PageableResponce<ProductDto>> getAllLiveProduct(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        PageableResponce<ProductDto> allProductS = productService.getAllLive(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allProductS, HttpStatus.FOUND);
    }

    @GetMapping("/search/{query}")
    public ResponseEntity<PageableResponce<ProductDto>> searchProduct(@PathVariable String query,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        PageableResponce<ProductDto> allProductS = productService.searchByTitle(query, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allProductS, HttpStatus.FOUND);
    }


}
