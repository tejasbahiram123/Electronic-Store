package com.lcwd.electronicstore.service;

import com.lcwd.electronicstore.dto.PageableResponce;
import com.lcwd.electronicstore.dto.UserDto;

import java.util.List;

public interface UserService {

    //create
    UserDto createUser(UserDto userDto);

    //update
    UserDto updateUser(UserDto  userDto, String userId);

    //delete
    void deleteUser(String  userId);

    //get all users
    PageableResponce<UserDto> getAllUser(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    //get single user by id
    UserDto getUserById(String userId);

    // get user by email
    UserDto getUserByEmail(String email);

    //search user
    List<UserDto> searchUser(String keyword);



}
