package com.lcwd.electronicstore.controller;

import com.lcwd.electronicstore.constant.AppConstants;
import com.lcwd.electronicstore.dto.ApiResponceMessage;
import com.lcwd.electronicstore.dto.UserDto;
import com.lcwd.electronicstore.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Tejas Bahiram[2512]
 * UserController for Handling the api
 */
@RestController
@RequestMapping("/users")
public class UserController {

   private static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    /**
     * @param userDto
     * @return Created user
     * @apiNote This method for Create user
     */
    @PostMapping
    public ResponseEntity<UserDto> CreateUser(@Valid @RequestBody UserDto userDto) {
        logger.info("start request for save the user data");
        UserDto userCreate = userService.createUser(userDto);
        logger.info("complete request for save user data");
        return new ResponseEntity(userCreate, HttpStatus.CREATED);
    }

    /**
     * @param userId
     * @param userDto
     * @return updated user
     * @apiNote This method for update user
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @PathVariable String userId
            , @RequestBody UserDto userDto) {
        logger.info("start request for update user"+userId);
        UserDto updateUser = userService.updateUser(userDto, userId);
        logger.info("complete request for update user"+userId);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    /**
     * @param userId
     * @return message
     * @apiNote This method for delete user
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponceMessage> deleteUser(@PathVariable String userId) {
        logger.info("start request for delete user"+userId);
        userService.deleteUser(userId);
        ApiResponceMessage message = ApiResponceMessage.builder().message(AppConstants.DELETED).success(true).status(HttpStatus.OK).build();
       logger.info("request completed for delete user"+userId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * @return  List of User
     * @apiNote This method for getAll user
     */
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUser(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false)Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false)Integer pageSize
    ) {
        logger.info("start request for getAllUser");
        List<UserDto> allUser = userService.getAllUser(pageNumber,pageSize);
        logger.info("complete request for getAllUser");
        return new ResponseEntity<List<UserDto>>(allUser, HttpStatus.OK);


    }

    /**
     * @param userId
     * @return user by given userId
     * @apiNote This method for getSingle user by userId
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId) {
        logger.info("start request for get User by Id is"+userId);
        UserDto userById = userService.getUserById(userId);
        logger.info("complete request for getUser by Id"+userId);
        return new ResponseEntity<>(userById, HttpStatus.OK);

    }

    /**
     * @param email
     * @return user for given email
     * @apiNote This method for get user by given email
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        logger.info("start request for getUser by email" + email);
        UserDto userByEmail = userService.getUserByEmail(email);
        logger.info("complete request for getUser by email" + email);
        return new ResponseEntity<>(userByEmail, HttpStatus.OK);
    }
    /**
     * @param keyword
     * @return user
     * @apiNote This method for search user
     */
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword) {
        logger.info("start request for search User");
        List<UserDto> userDtos = userService.searchUser(keyword);
        logger.info("complete request for search User");
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }
}
