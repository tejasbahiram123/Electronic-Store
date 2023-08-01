package com.lcwd.electronicstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lcwd.electronicstore.dto.PageableResponce;
import com.lcwd.electronicstore.dto.UserDto;
import com.lcwd.electronicstore.entity.User;
import com.lcwd.electronicstore.service.UserService;
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
public class UserControllerTest {

    @MockBean
    private UserService userService;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private MockMvc mockMvc;
    private  User user;
    @BeforeEach
    public  void init(){
        user = User.builder()
                .name("tejas")
                .email("tejas12@gmail.com")
                .about("this is")
                .gender("male")
                .imageName("abc.png").
                password("tyfyt5554").build();
    }
    @Test
    public  void createUserTest() throws Exception {

        UserDto dto = mapper.map(user, UserDto.class);
        Mockito.when(userService.createUser(Mockito.any())).thenReturn(dto);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonString(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").exists());
    }
    @Test
    public void updateUser() throws Exception {

        String userId ="user123";
        UserDto dto = mapper.map(user, UserDto.class);
        Mockito.when(userService.updateUser(Mockito.any(),Mockito.anyString())).thenReturn(dto);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/users/"+userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonString(user))
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists());

    }

    private String convertObjectToJsonString(Object user) {
        try{
    return new ObjectMapper().writeValueAsString(user);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Test
    public void getAllUserTest() throws Exception {

        UserDto object1 = UserDto.builder().name("tejas").email("tejas12@gmail.com").password("tejas2512").about("Testing").build();
        UserDto object2 = UserDto.builder().name("aniket").email("tejas12@gmail.com").password("tejas2512").about("Testing").build();
        UserDto object3 = UserDto.builder().name("sumit").email("tejas12@gmail.com").password("tejas2512").about("Testing").build();
        UserDto object4 = UserDto.builder().name("elvish").email("tejas12@gmail.com").password("tejas2512").about("Testing").build();

        PageableResponce<UserDto> pageableResponce= new PageableResponce<>();
        pageableResponce.setContent(Arrays.asList(object1,object2,object3,object4));
        pageableResponce.setPageNumber(10);
        pageableResponce.setLastPage(false);
        pageableResponce.setPageSize(100);
        pageableResponce.setTotalElements(1000);

        Mockito.when(userService.getAllUser(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString()
                                            ,Mockito.anyString())).thenReturn(pageableResponce);
    this.mockMvc.perform(
            MockMvcRequestBuilders.get("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
    ).andDo(print())
            .andExpect(status().isOk());


    }
}
