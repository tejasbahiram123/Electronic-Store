package com.lcwd.electronicstore.controller;

import com.lcwd.electronicstore.constant.AppConstants;
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
    /**
     * @apiNote This method for create the product
     * @param productDto
     * @return created product
     */
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        logger.info("start request for create Product");
        ProductDto product = productService.createProduct(productDto);
        logger.info("complete request for create Product");
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    /**
     * @apiNote This method for update product
     * @param productDto
     * @param productId
     * @return updated product
     */
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@Valid @RequestBody ProductDto productDto, @PathVariable String productId) {
        logger.info("start request for update Product{} ,"+productId);
        ProductDto updatedProduct = productService.updateProduct(productDto, productId);
        logger.info("complete request for update Product{} ,"+productId);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    /**
     * @apiNote This method for get product by Id
     * @param productId
     * @return product by Id
     */
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable String productId) {
        logger.info("start request for get Product{} ,"+productId);
        ProductDto getProduct = productService.getProduct(productId);
        logger.info("complete request for get Product{} ,"+productId);
        return new ResponseEntity<>(getProduct, HttpStatus.FOUND);
    }

    /**
     * @apiNote This method for getAll product in List
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return List of product
     */
    @GetMapping
    public ResponseEntity<PageableResponce<ProductDto>> getAllProduct(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        logger.info("start request for update Product{} ");
        PageableResponce<ProductDto> allProductS = productService.getAllProduct(pageNumber, pageSize, sortBy, sortDir);
        logger.info("start request for update Product{} ");
        return new ResponseEntity<>(allProductS, HttpStatus.FOUND);
    }

    /**
     * @apiNote This method for delete product by Id
     * @param productId
     * @return deleted product message
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponceMessage> deleteProduct(@PathVariable String productId) {
        productService.deleteProduct(productId);
        logger.info("start request for delete Product{} ,"+productId);
        ApiResponceMessage responce = ApiResponceMessage.builder().message(AppConstants.PRODUCT_DELETED)
                .status(HttpStatus.OK)
                .success(true).build();
        logger.info("start request for delete Product{} ,"+productId);
        return new ResponseEntity<ApiResponceMessage>(responce, HttpStatus.OK);
    }

    /**
     * @apiNote This method getAll live product in list
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return list of live products
     */
    @GetMapping("/live")
    public ResponseEntity<PageableResponce<ProductDto>> getAllLiveProduct(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        logger.info("start request for update Product {} ");
        PageableResponce<ProductDto> allProductS = productService.getAllLive(pageNumber, pageSize, sortBy, sortDir);
        logger.info("start request for update Product {}");
        return new ResponseEntity<>(allProductS, HttpStatus.FOUND);
    }

    /**
     * @apiNote This method for search product
     * @param query
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return Product
     */
    @GetMapping("/search/{query}")
    public ResponseEntity<PageableResponce<ProductDto>> searchProduct(@PathVariable String query,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        logger.info("start request for update Product{}");
        PageableResponce<ProductDto> allProductS = productService.searchByTitle(query, pageNumber, pageSize, sortBy, sortDir);
        logger.info("start request for update Product{}");
        return new ResponseEntity<>(allProductS, HttpStatus.FOUND);
    }
    /**
     * @apiNote This method for Upload image of Product
     * @param image
     * @param productId
     * @return Message
     * @throws IOException
     */
    @PostMapping("/image/{productId}")
    public ResponseEntity<ImageResponce> uploadProductImage(@RequestParam("productImage")MultipartFile image,
                                                            @PathVariable String productId
                                                            ) throws IOException {
    logger.info("start request for upload Image of Product{} ,"+productId);
        String fileName = fileService.uplodFile(image, imagePath);
        ProductDto productDto = productService.getProduct(productId);
        productDto.setProductImageName(fileName);
        ProductDto updatedProduct = productService.updateProduct(productDto, productId);

        ImageResponce responce = ImageResponce.builder().imageName(updatedProduct.getProductImageName()).message(AppConstants.PRODUCT_IMAGE).status(HttpStatus.OK).success(true).build();
    logger.info("complete request for upload Image of Product{} ,"+productId);
        return new ResponseEntity<>(responce,HttpStatus.CREATED);

    }

    /**
     * @apiNote This method for serve the product Image
     * @param productId
     * @param response
     * @throws IOException
     */
    @GetMapping("/image/{productId}")
    public void serveProductImage(@PathVariable String productId , HttpServletResponse response) throws IOException {
        logger.info("start request for serve Image of Product{} ,"+productId);
        ProductDto productDto = productService.getProduct(productId);
        logger.info("User image name {}", productDto.getProductImageName());
        InputStream resource = fileService.getResource(imagePath, productDto.getProductImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
        logger.info("complete request for serve Image of Product{} ,"+productId);

    }

}
