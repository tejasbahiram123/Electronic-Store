package com.lcwd.electronicstore.service;

import com.lcwd.electronicstore.dto.CategoryDto;
import com.lcwd.electronicstore.dto.UserDto;
import com.lcwd.electronicstore.entity.Category;
import com.lcwd.electronicstore.entity.User;
import com.lcwd.electronicstore.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest
public class CategoryServiceTest {

    @MockBean
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModelMapper mapper;

    Category category;

    @BeforeEach
    public void init(){
        category = Category.builder()
                .title("LG store").coverImage("abc.png").description("best products of Tvs here").build();

    }
    @Test
    public  void createCategory(){
        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);
        CategoryDto category1 = categoryService.createCategory(mapper.map(category, CategoryDto.class));
        Assertions.assertNotEquals(category1,category);
        Assertions.assertEquals("LG store",category1.getTitle());
    }

    @Test
    public  void updateCategory(){
        String categoryid = "jkjcgfxst";
        CategoryDto categoryDto = CategoryDto.builder()
                .title("Tvs new Brand")
                .description("this is best")
                .coverImage("hdbd.png").build();
        Mockito.when(categoryRepository.findById("jkjcgfxst")).thenReturn(Optional.of(category));
        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);

        CategoryDto updatedcategory = categoryService.updateCategory(categoryDto,categoryid);
        Assertions.assertNotNull(updatedcategory);
        Assertions.assertEquals(categoryDto.getTitle(),updatedcategory.getTitle());

    }


}
