package com.lcwd.electronicstore.service.impl;

import com.lcwd.electronicstore.constant.AppConstants;
import com.lcwd.electronicstore.dto.UserDto;
import com.lcwd.electronicstore.entity.User;
import com.lcwd.electronicstore.exception.ResourceNotFoundException;
import com.lcwd.electronicstore.repository.UserRepository;
import com.lcwd.electronicstore.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
/**
 * @author Tejas Bahiram[2512]
 * UserService implementation for api
 */
@Service
public class UserServiceImpl implements UserService {

    Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public UserDto createUser(UserDto userDto) {

        logger.info("Initiating logic for create user");
        //generate unique id in string format
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);
        User user = this.mapper.map(userDto, User.class);
        User saveUser = userRepository.save(user);
        UserDto newDto = this.mapper.map(saveUser, UserDto.class);
        logger.info("complete logic for create user"+userId);
        return newDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        logger.info("Initiating logic for update user"+userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND));
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        user.setGender(userDto.getGender());
        user.setAbout(userDto.getAbout());
        user.setImageName(userDto.getImageName());

        User updatedUser = userRepository.save(user);
        UserDto updatedUserDto = this.mapper.map(user, UserDto.class);
        logger.info("complete logic for update user"+userId);
        return updatedUserDto;
    }
    
    @Override
    public void deleteUser(String userId) {
        logger.info("Initiating logic for delete user"+userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        logger.info("Initiating logic for delete user"+userId);
        userRepository.delete(user);
    }

    @Override
    public List<UserDto> getAllUser(Integer pageNumber,Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber ,pageSize);
        logger.info("Initiating logic for getAllUser ");
        Page<User> page = userRepository.findAll(pageable);

        List<User> content = page.getContent();
        List<UserDto> list_Of_User_Dtos = content.stream().map((user) -> mapper.map(user, UserDto.class)).collect(Collectors.toList());
        logger.info("complete logic for getAllUser");
        return list_Of_User_Dtos;
    }

    @Override
    public UserDto getUserById(String userId) {
        logger.info("Initiating logic for getUserById "+userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND));
        logger.info("complete logic for getUserById "+userId);
        return this.mapper.map(user,UserDto.class);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        logger.info("Initiating logic for  getuserByEmail"+email);
        User userByEmail = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND));
        logger.info("complete  logic for  getuserByEmail"+email);
        return this.mapper.map(userByEmail,UserDto.class);
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        logger.info("Initiating logic for  serarchUser");
        List<User> users = userRepository.findByNameContaining(keyword);
        List<UserDto> userDtos = users.stream().map((user) -> mapper.map(user, UserDto.class)).collect(Collectors.toList());
        logger.info("complete logic for  serarchUser");
        return userDtos;
    }

}
