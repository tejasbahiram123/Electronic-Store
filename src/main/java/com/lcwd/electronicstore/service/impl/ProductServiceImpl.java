package com.lcwd.electronicstore.service.impl;

import com.lcwd.electronicstore.dto.PageableResponce;
import com.lcwd.electronicstore.dto.ProductDto;
import com.lcwd.electronicstore.entity.Category;
import com.lcwd.electronicstore.entity.Product;
import com.lcwd.electronicstore.exception.ResourceNotFoundException;
import com.lcwd.electronicstore.helper.Helper;
import com.lcwd.electronicstore.repository.CategoryRepository;
import com.lcwd.electronicstore.repository.ProductRepository;
import com.lcwd.electronicstore.service.ProductService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;

    Logger logger= LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    public ProductDto createProduct(ProductDto productDto) {

        logger.info("Initiating logic for create Product");
        Product product = this.mapper.map(productDto, Product.class);
       //set product Id
        String productId = UUID.randomUUID().toString();
        product.setProductId(productId);
        //date
        product.setAddedDate(new Date());
        Product saveProduct = this.productRepository.save(product);
        logger.info("complete logic for create Product");
        return mapper.map(saveProduct, ProductDto.class);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, String productId) {
        logger.info("Initiating logic for update Product {} ,"+productId);
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product Not found with this Id"));
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setQuantity(productDto.getQuantity());
        // product.setAddedDate(new Date());
        product.setLive(productDto.isLive());
        product.setStock(productDto.isStock());
        product.setProductImageName(productDto.getProductImageName());

        Product updatedProduct = this.productRepository.save(product);
        logger.info("complete logic for update Product {} ,"+productId);
        return mapper.map(updatedProduct, ProductDto.class);
    }

    @Override
    public void deleteProduct(String productId) {
        logger.info("Initiating logic for delete Product {} ,"+productId);
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product Not found with this Id"));
        productRepository.delete(product);
        logger.info("complete logic for delete Product {} ,"+productId);

    }

    @Override
    public ProductDto getProduct(String productId) {
        logger.info("Initiating logic for get Product {} ,"+productId);
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product Not found with this Id"));
        ProductDto productDto = this.mapper.map(product, ProductDto.class);
        logger.info("complete logic for get Product {} ,"+productId);
        return productDto;
    }

    @Override
    public PageableResponce<ProductDto> getAllProduct(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        logger.info("Initiating logic for getAll Product {} ");
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = productRepository.findAll(pageable);
        logger.info("complete logic for getAll Product {} ");
        return Helper.getPageableResponce(page, ProductDto.class);
    }

    @Override
    public PageableResponce<ProductDto> getAllLive(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        logger.info("Initiating logic for update Product {}");
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> products = productRepository.findByLiveTrue(pageable);
        logger.info("complete logic for update Product {} ");
        return Helper.getPageableResponce(products, ProductDto.class);

    }

    @Override
    public PageableResponce<ProductDto> searchByTitle(String subTitle, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        logger.info("Initiating logic for search Product {} ");
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> products = productRepository.findByTitleContaining(subTitle, pageable);
        logger.info("Initiating logic for search Product {} ");
        return Helper.getPageableResponce(products, ProductDto.class);
    }

    @Override
    public ProductDto createWithCategory(ProductDto productDto, String categoryId) {
        logger.info("Initiating logic for create product With Category, {} "+categoryId);
       //fetch first category id
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category not found with this Id"));
        Product product = this.mapper.map(productDto, Product.class);
        //set product Id
        String productId = UUID.randomUUID().toString();
        product.setProductId(productId);
        //date
        product.setAddedDate(new Date());
        //set product in category
        product.setCategory(category);
        Product saveProduct = this.productRepository.save(product);
        logger.info("complete logic for create product With Category, {} "+categoryId);
        return mapper.map(saveProduct, ProductDto.class);

    }
//update category in product
    @Override
    public ProductDto updateCategory(String productId, String categoryId) {
        logger.info("Initiating logic for  Category, {} "+categoryId);
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product not found with this Id"));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category not found with thiis Id"));
product.setCategory(category);
        Product updatedProduct = productRepository.save(product);
        logger.info("complete logic for Category, {} "+categoryId);
        return mapper.map(updatedProduct,ProductDto.class);
    }

    @Override
    public PageableResponce<ProductDto> getAllOfCategory(String categoryId,Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        logger.info("Initiating logic getAll Category, {} "+categoryId);
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found with this Id"));
       Sort sort=(sortDir.equalsIgnoreCase("desc")) ?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending()) ;
        Pageable pageable=PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepository.findByCategory(category,pageable);
        logger.info("Initiating logic for getAll Category, {} "+categoryId);
        return Helper.getPageableResponce(page,ProductDto.class);
    }


}
