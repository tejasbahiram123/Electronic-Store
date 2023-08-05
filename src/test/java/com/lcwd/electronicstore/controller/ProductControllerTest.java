package com.lcwd.electronicstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lcwd.electronicstore.dto.ProductDto;
import com.lcwd.electronicstore.entity.Product;
import com.lcwd.electronicstore.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @MockBean
     private ProductService productService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    Product product;
    @BeforeEach
    public void init(){

        product = Product.builder().live(true).price(252.00).discountedPrice(250.00).stock(true).title("mobile1")
                .description("all types of mobiles").productImageName("abc.png").quantity(100).build();


    }
    @Test
    public  void createProductTest() throws Exception {
        ProductDto dto = mapper.map(product, ProductDto.class);
        Mockito.when(productService.createProduct(Mockito.any())).thenReturn(dto);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonString(product))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").exists());

    }

    private String convertObjectToJsonString(Object product) {

        try{
            return  new ObjectMapper().writeValueAsString(product);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
