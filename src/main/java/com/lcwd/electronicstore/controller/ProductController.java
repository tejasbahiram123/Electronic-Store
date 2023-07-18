package com.lcwd.electronicstore.controller;

import com.lcwd.electronicstore.dto.*;
import com.lcwd.electronicstore.service.FileService;
import com.lcwd.electronicstore.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private FileService fileService;

    @Value("${product.image.path}")
    private  String imagePath;

    private Logger logger= LoggerFactory.getLogger(ProductController.class);

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

    //image upload
@PostMapping("/image/{productId}")
    public ResponseEntity<ImageResponce> uploadProductImage(@RequestParam("productImage")MultipartFile image,
                                                            @PathVariable String productId
                                                            ) throws IOException {
        String fileName = fileService.uplodFile(image, imagePath);
        ProductDto productDto = productService.getProduct(productId);
        productDto.setProductImageName(fileName);
        ProductDto updatedProduct = productService.updateProduct(productDto, productId);

        ImageResponce responce = ImageResponce.builder().imageName(updatedProduct.getProductImageName()).message("product image is successful").status(HttpStatus.OK).success(true).build();
        return new ResponseEntity<>(responce,HttpStatus.CREATED);

    }
    //serve image
    @GetMapping("/image/{productId}")
    public void serveProductImage(@PathVariable String productId , HttpServletResponse response) throws IOException {

        ProductDto productDto = productService.getProduct(productId);
        logger.info("User image name {}", productDto.getProductImageName());

        InputStream resource = fileService.getResource(imagePath, productDto.getProductImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());

    }

}
