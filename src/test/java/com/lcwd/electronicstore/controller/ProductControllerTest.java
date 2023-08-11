package com.lcwd.electronicstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lcwd.electronicstore.dto.PageableResponce;
import com.lcwd.electronicstore.dto.ProductDto;
import com.lcwd.electronicstore.entity.Product;
import com.lcwd.electronicstore.service.FileService;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @MockBean
    private ProductService productService;

    @MockBean
    private FileService fileService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    Product product;

    @BeforeEach
    public void init() {

        product = Product.builder().live(true).price(252.00).discountedPrice(250.00).stock(true).title("mobile1")
                .description("all types of mobiles").productImageName("abc.png").quantity(100).build();


    }

    @Test
    public void createProductTest() throws Exception {
        ProductDto dto = mapper.map(product, ProductDto.class);
        Mockito.when(productService.createProduct(Mockito.any())).thenReturn(dto);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(product))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").exists());

        ProductDto productDto = ProductDto.builder().live(true).price(252.00).discountedPrice(250.00).stock(true).title("mobile1")
                .description("all types of mobiles").productImageName("abc.png").quantity(100).build();
        ProductDto productDto1 = ProductDto.builder().live(true).price(252.00).discountedPrice(250.00).stock(true).title("mobile1")
                .description("all types of mobiles").productImageName("abc.png").quantity(100).build();
        ProductDto productDto2 = ProductDto.builder().live(true).price(252.00).discountedPrice(250.00).stock(true).title("mobile1")
                .description("all types of mobiles").productImageName("abc.png").quantity(100).build();


    }

    private String convertObjectToJsonString(Object product) {
        try {
            return new ObjectMapper().writeValueAsString(product);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Test
    public void updateProductTest() throws Exception {
        String proId = "pro123";
        ProductDto productDto = mapper.map(product, ProductDto.class);
        Mockito.when(productService.updateProduct(Mockito.any(), Mockito.anyString())).thenReturn(productDto);
        this.mockMvc.perform(MockMvcRequestBuilders.put("/products/proId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(product))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists());
    }

    @Test
    public void getProductTest() throws Exception {
        String proId = "pro123";
        ProductDto productDto = mapper.map(product, ProductDto.class);

        Mockito.when(productService.getProduct(Mockito.anyString())).thenReturn(productDto);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/products/proId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(product))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.title").exists());
    }

    @Test
    public void getAllProductTest() throws Exception {
        ProductDto productDto = ProductDto.builder().live(true).price(252.00).discountedPrice(250.00).stock(true).title("mobile1")
                .description("all types of mobiles").productImageName("abc.png").quantity(100).build();
        ProductDto productDto1 = ProductDto.builder().live(true).price(252.00).discountedPrice(250.00).stock(true).title("mobile1")
                .description("all types of mobiles").productImageName("abc.png").quantity(100).build();
        ProductDto productDto2 = ProductDto.builder().live(true).price(252.00).discountedPrice(250.00).stock(true).title("mobile1")
                .description("all types of mobiles").productImageName("abc.png").quantity(100).build();

        PageableResponce<ProductDto> pageableResponce = new PageableResponce<>();
        pageableResponce.setContent(Arrays.asList(productDto, productDto1, productDto2));
        pageableResponce.setPageNumber(1);
        pageableResponce.setPageSize(2);
        pageableResponce.setLastPage(false);
        pageableResponce.setTotalElements(4);

        Mockito.when(productService.getAllProduct(1, 10, "title", "asc")).thenReturn(pageableResponce);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound());

    }

    @Test
    public void deleteProductTest() throws Exception {
        String proId = "pro123";
        ProductDto productDto = mapper.map(product, ProductDto.class);

        Mockito.when(productService.getProduct(Mockito.anyString())).thenReturn(productDto);
        productService.deleteProduct(proId);

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/products/proId")
                        .contentType(MediaType.APPLICATION_JSON))
                //  .content(convertObjectToJsonString(product)))
                //  .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void getAllLiveProductTest() throws Exception {

        ProductDto productDto = ProductDto.builder().live(true).price(252.00).discountedPrice(250.00).stock(true).title("mobile1")
                .description("all types of mobiles").productImageName("abc.png").quantity(100).build();
        ProductDto productDto1 = ProductDto.builder().live(true).price(252.00).discountedPrice(250.00).stock(true).title("mobile1")
                .description("all types of mobiles").productImageName("abc.png").quantity(100).build();
        ProductDto productDto2 = ProductDto.builder().live(true).price(252.00).discountedPrice(250.00).stock(true).title("mobile1")
                .description("all types of mobiles").productImageName("abc.png").quantity(100).build();

        PageableResponce<ProductDto> pageableResponce = new PageableResponce<>();
        pageableResponce.setContent(Arrays.asList(productDto, productDto1, productDto2));
        pageableResponce.setPageNumber(1);
        pageableResponce.setPageSize(2);
        pageableResponce.setLastPage(false);
        pageableResponce.setTotalElements(4);

        Mockito.when(productService.getAllLive(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pageableResponce);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/products/live")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound());
    }

    @Test
    public void searchProductTest() throws Exception {
        String query="mobile";
        ProductDto productDto = ProductDto.builder().live(true).price(252.00).discountedPrice(250.00).stock(true).title("mobile1")
                .description("all types of mobile").productImageName("abc.png").quantity(100).build();
        ProductDto productDto1 = ProductDto.builder().live(true).price(252.00).discountedPrice(250.00).stock(true).title("mobile1")
                .description("all types of mobiles").productImageName("abc.png").quantity(100).build();
        ProductDto productDto2 = ProductDto.builder().live(true).price(252.00).discountedPrice(250.00).stock(true).title("mobile1")
                .description("all types of mobil").productImageName("abc.png").quantity(100).build();

        PageableResponce<ProductDto> pageableResponce = new PageableResponce<>();
        pageableResponce.setContent(Arrays.asList(productDto, productDto1, productDto2));
        pageableResponce.setPageNumber(1);
        pageableResponce.setPageSize(2);
        pageableResponce.setLastPage(false);
        pageableResponce.setTotalElements(4);

        Mockito.when(productService.searchByTitle(query,1,2,"title","acs")).thenReturn(pageableResponce);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/products/search/"+query)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound());
    }
    @Test
    public void uploadProductImage() throws Exception {

        String proId="123";
        String imgname="abc.png";

        ProductDto dto = mapper.map(product, ProductDto.class);
      //  MockMultipartFile productimg= new MockMultipartFile();
        Mockito.when(fileService.uplodFile(Mockito.any(),Mockito.anyString())).thenReturn(imgname);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/products/image"+proId)
                .contentType(MediaType.IMAGE_PNG_VALUE)
                .accept(MediaType.IMAGE_PNG_VALUE))
                .andDo(print())
                .andExpect(status().isCreated());
    }

}
