package com.lcwd.electronicstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lcwd.electronicstore.dto.CategoryDto;
import com.lcwd.electronicstore.dto.PageableResponce;
import com.lcwd.electronicstore.dto.ProductDto;
import com.lcwd.electronicstore.entity.Category;
import com.lcwd.electronicstore.entity.Product;
import com.lcwd.electronicstore.service.CategoryService;
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

import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {

    @MockBean
    private CategoryService categoryService;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private MockMvc mockMvc;

    private Category category;

    @MockBean
    private ProductService productService;

    Product product;

    @BeforeEach
    public void init() {
        category = Category.builder()
                .title("LG store ")
                .coverImage("abc.png")
                .description("best products of Tvs here")
                .build();

        product = Product.builder().live(true).price(252.00).discountedPrice(250.00).stock(true).title("mobile1")
                .description("all types of mobiles").productImageName("abc.png").quantity(100).build();

    }
    @Test
    public void createCategoryTest() throws Exception {

        CategoryDto dto = mapper.map(category, CategoryDto.class);
        Mockito.when(categoryService.createCategory(Mockito.any())).thenReturn(dto);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/categories/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonString(category))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").exists());

    }
    private String convertObjectToJsonString(Object category) {

        try{
            return  new ObjectMapper().writeValueAsString(category);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    @Test
    public  void updateCategoryTest() throws Exception {
        String categoryId="cat123";
        CategoryDto dto = mapper.map(category, CategoryDto.class);

        Mockito.when(categoryService.updateCategory(dto,categoryId)).thenReturn(dto);
        mockMvc.perform(MockMvcRequestBuilders.put("/categories/update/"+categoryId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonString(category))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
               // .andExpect(jsonPath("$.title").exists());
    }
    @Test
    public void getAllCategoryTest() throws Exception {

        CategoryDto dto = CategoryDto.builder().title("Tvs").description("all categories of electronics appliences ").coverImage("abc.png").build();
        CategoryDto dto2 = CategoryDto.builder().title("Music system").description("all categories of electronics appliences ").coverImage("abc.png").build();
        CategoryDto dto3 = CategoryDto.builder().title("AC").description("all categories of electronics appliences ").coverImage("abc.png").build();
        CategoryDto dto4 = CategoryDto.builder().title("Smartphones").description("all categories of electronics appliences ").coverImage("abc.png").build();

        PageableResponce<CategoryDto> pageableResponce=new PageableResponce<>();
        pageableResponce.setContent(Arrays.asList(dto,dto2,dto3,dto4));
        pageableResponce.setPageNumber(0);
        pageableResponce.setPageSize(2);
        pageableResponce.setLastPage(false);
        pageableResponce.setTotalElements(4);

        Mockito.when(categoryService.getAllCategory(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pageableResponce);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound());
    }

    @Test
    public void deleteCategoryTest() throws Exception {

        String catId="2512";
        CategoryDto dto = mapper.map(category, CategoryDto.class);

        Mockito.when(categoryService.getCategory(catId)).thenReturn(dto);
        categoryService.deleteCategory(catId);

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/categories/"+catId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonString(category))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public  void getCategoryTest() throws Exception {

        String catId="cat123";
        CategoryDto dto = mapper.map(category, CategoryDto.class);
        Mockito.when(categoryService.getCategory(catId)).thenReturn(dto);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/categories/"+catId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonString(category))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound());
    }

    @Test
    public void createProductWithCategoryTest() throws Exception {
        String catId="cat123";
        ProductDto dto = mapper.map(product, ProductDto.class);
        Mockito.when(productService.createWithCategory(dto,catId)).thenReturn(dto);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/categories/catId/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonString(category))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
               // .andExpect(jsonPath("$.title").exists());
    }
    @Test
    public void updateCategoryOfProductTest() throws Exception {
        String catId="cat123";
        String proId="pro123";

        ProductDto dto = mapper.map(product, ProductDto.class);
        Mockito.when(productService.updateCategory(proId,catId)).thenReturn(dto);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/categories/catId/products/proId")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonString(category))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }


}
