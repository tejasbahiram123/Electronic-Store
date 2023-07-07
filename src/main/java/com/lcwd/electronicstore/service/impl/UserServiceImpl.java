package com.lcwd.electronicstore.service.impl;

import com.lcwd.electronicstore.dto.UserDto;
import com.lcwd.electronicstore.entity.User;
import com.lcwd.electronicstore.repository.UserRepository;
import com.lcwd.electronicstore.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public UserDto createUser(UserDto userDto) {

        //generate unique id in string format
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);
        //dto to entity
        User user = dtoToEntity(userDto);
        User saveUser = userRepository.save(user);
         //entity to dto
        UserDto newDto = entityToDto(saveUser);
        return newDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        user.setGender(userDto.getGender());
        user.setAbout(userDto.getAbout());
        user.setImageName(userDto.getImageName());

        User updatedUser = userRepository.save(user);
        UserDto updatedDto = entityToDto(updatedUser);
        return updatedDto;
    }


    @Override
    public void deleteUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);

    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> userList = userRepository.findAll();
        List<UserDto> dtoList = userList.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        return dtoList;
    }

    @Override
    public UserDto getUserById(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found by given Id"));

        return entityToDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User userByEmail = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("user not found with this Email Id"));
        return entityToDto(userByEmail);
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        List<User> users = userRepository.findByNameContaining(keyword);
        List<UserDto> dtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        return dtoList;
    }


    private UserDto entityToDto(User saveUser) {

        return mapper.map(saveUser,UserDto.class);
    }

    private User dtoToEntity(UserDto userDto) {

        return mapper.map(userDto ,User.class);
    }

}
