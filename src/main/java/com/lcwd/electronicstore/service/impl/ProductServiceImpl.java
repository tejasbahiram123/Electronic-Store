package com.lcwd.electronicstore.service.impl;

import com.lcwd.electronicstore.dto.PageableResponce;
import com.lcwd.electronicstore.dto.ProductDto;
import com.lcwd.electronicstore.entity.Product;
import com.lcwd.electronicstore.exception.ResourceNotFoundException;
import com.lcwd.electronicstore.helper.Helper;
import com.lcwd.electronicstore.repository.ProductRepository;
import com.lcwd.electronicstore.service.ProductService;
import org.modelmapper.ModelMapper;
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
    private ProductRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = this.mapper.map(productDto, Product.class);
       //set product Id
        String productId = UUID.randomUUID().toString();
        product.setProductId(productId);
        //date
        product.setAddedDate(new Date());
        Product saveProduct = this.repository.save(product);
        return mapper.map(saveProduct, ProductDto.class);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, String productId) {
        Product product = repository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product Not found with this Id"));
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setQuantity(productDto.getQuantity());
        // product.setAddedDate(new Date());
        product.setLive(productDto.isLive());
        product.setStock(productDto.isStock());

        Product updatedProduct = this.repository.save(product);
        return mapper.map(updatedProduct, ProductDto.class);
    }

    @Override
    public void deleteProduct(String productId) {
        Product product = this.repository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product Not found with this Id"));
        repository.delete(product);

    }

    @Override
    public ProductDto getProduct(String productId) {
        Product product = this.repository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product Not found with this Id"));
        ProductDto productDto = this.mapper.map(product, ProductDto.class);
        return productDto;
    }

    @Override
    public PageableResponce<ProductDto> getAllProduct(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = repository.findAll(pageable);
        return Helper.getPageableResponce(page, ProductDto.class);
    }

    @Override
    public PageableResponce<ProductDto> getAllLive(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> products = repository.findByLiveTrue(pageable);
        return Helper.getPageableResponce(products, ProductDto.class);

    }

    @Override
    public PageableResponce<ProductDto> searchByTitle(String subTitle, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> products = repository.findByTitleContaining(subTitle, pageable);
        return Helper.getPageableResponce(products, ProductDto.class);
    }
}
