package com.lcwd.electronicstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lcwd.electronicstore.dto.CategoryDto;
import com.lcwd.electronicstore.entity.Category;
import com.lcwd.electronicstore.service.CategoryService;
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
public class CategoryControllerTest {

    @MockBean
    private CategoryService categoryService;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private MockMvc mockMvc;

    private Category category;


    @BeforeEach
    public void init() {
        category = Category.builder()
                .title("LG store ")
                .coverImage("abc.png")
                .description("best products of Tvs here")
                .build();
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



}
