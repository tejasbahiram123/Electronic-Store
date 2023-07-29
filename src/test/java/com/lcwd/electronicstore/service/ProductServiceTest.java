package com.lcwd.electronicstore.service;

import com.lcwd.electronicstore.dto.ProductDto;
import com.lcwd.electronicstore.entity.Product;
import com.lcwd.electronicstore.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class ProductServiceTest {
    @MockBean
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private ModelMapper mapper;

    Product product;
    @BeforeEach
    public void  init(){
        product= Product.builder().live(true).price(252.00).discountedPrice(250.00).stock(true).title("mobile")
                .description("all types of mobiles").productImageName("abc.png").quantity(100).build();
    }

    @Test
    public void createProduct(){
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);
        ProductDto product1 = productService.createProduct(mapper.map(product, ProductDto.class));
        Assertions.assertNotNull(product1);
        Assertions.assertEquals(product.getTitle(),product1.getTitle());

    }

}
