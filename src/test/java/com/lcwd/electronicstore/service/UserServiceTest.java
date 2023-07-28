package com.lcwd.electronicstore.service;

import com.lcwd.electronicstore.dto.PageableResponce;
import com.lcwd.electronicstore.dto.UserDto;
import com.lcwd.electronicstore.entity.User;
import com.lcwd.electronicstore.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class UserServiceTest {
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper mapper;
    User user;
    @BeforeEach
    public void init() {
        user = User.builder()
                .name("tejas")
                .email("tejas12@gmail.com")
                .about("this is")
                .gender("male")
                .imageName("abc.png").
                password("tyfyt5").build();

    }
    @Test
    public void createUserTest() {
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        UserDto user1 = userService.createUser(mapper.map(user, UserDto.class));
        Assertions.assertNotEquals(user1, user);
        Assertions.assertEquals("tejas", user1.getName());
    }
    @Test
    public void updateUserTest() {
        String userId = "ghchgcgfxst";
        UserDto userDto = UserDto.builder()
                .name("tejas bahiram")
                .about("this is update")
                .gender("male")
                .imageName("abc.png").
                build();
        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        UserDto updateUser = userService.updateUser(userDto, userId);
        System.out.println(updateUser.getName());
        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(userDto.getName(), updateUser.getName());
    }
    @Test
    public void getUserByIdTest() {
        String userId = "ghchgcgfxst";
        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        UserDto id = userService.getUserById(userId);
        Assertions.assertNotNull(id);
        Assertions.assertEquals(user.getName(), id.getName());
    }
    @Test
    public void getUserByEmailTest() {
        String mail = "tejas12@gmail.com";
        Mockito.when(userRepository.findByEmail(mail)).thenReturn(Optional.of(user));
        UserDto email = userService.getUserByEmail(mail);
        Assertions.assertNotNull(email);
        Assertions.assertEquals(user.getEmail(), email.getEmail());
    }

    @Test
    public void searchUserTest() {
        User user1 = User.builder()
                .name("abhi")
                .email("tejas12@gmail.com")
                .about("this is guftyfytfyt")
                .gender("male")
                .imageName("abc.png").
                password("tyfyt5").build();

        User user2 = User.builder()
                .name("tejas")
                .email("tejas12@gmail.com")
                .about("this gjg gu")
                .gender("male")
                .imageName("abc.png").
                password("tyfyt5").build();

        User user3 = User.builder()
                .name("dipak")
                .email("tejas12@gmail.com")
                .about("this isug ugu")
                .gender("male")
                .imageName("abc.png").
                password("tyfyt5").build();

        String keyword = "a";
        Mockito.when(userRepository.findByNameContaining(keyword)).thenReturn(Arrays.asList(user, user1, user2, user3));
        List<UserDto> userDtos = userService.searchUser(keyword);
        Assertions.assertEquals(4, userDtos.size(),"size not mathched");

    }
    @Test
    public void deleteUserTest() {
        String userid = "userId123";

        Mockito.when(userRepository.findById("userId123")).thenReturn(Optional.of(user));

        userService.deleteUser(userid);
        Mockito.verify(userRepository, Mockito.times(1)).delete(user);
    }
    @Test
    public  void  getAllUserTest(){
        User user1 = User.builder()
                .name("elvish")
                .email("tejas12@gmail.com")
                .about("this is guftyfytfyt")
                .gender("male")
                .imageName("abc.png").
                password("tyfyt5").build();

        User user2 = User.builder()
                .name("kataria")
                .email("tejas12@gmail.com")
                .about("this gjg gu")
                .gender("male")
                .imageName("abc.png").
                password("tyfyt5").build();

        List<User> userList= Arrays.asList(user,user1,user2);
        Page<User> page=new PageImpl<>(userList);
        Mockito.when(userRepository.findAll((Pageable) Mockito.any())).thenReturn(page);

        PageableResponce<UserDto> allUser = userService.getAllUser(1,2,"name","asc");
        Assertions.assertEquals(3,allUser.getContent().size());


    }



}
