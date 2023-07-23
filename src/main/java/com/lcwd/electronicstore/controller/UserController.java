package com.lcwd.electronicstore.controller;

import com.lcwd.electronicstore.constant.AppConstants;
import com.lcwd.electronicstore.dto.ApiResponceMessage;
import com.lcwd.electronicstore.dto.ImageResponce;
import com.lcwd.electronicstore.dto.PageableResponce;
import com.lcwd.electronicstore.dto.UserDto;
import com.lcwd.electronicstore.service.FileService;
import com.lcwd.electronicstore.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
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

    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")
    private String  imageUploadPath;

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
        logger.info("start request for update user{},",userId);
        UserDto updateUser = userService.updateUser(userDto, userId);
        logger.info("complete request for update user{}",userId);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    /**
     * @param userId
     * @return message
     * @apiNote This method for delete user
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponceMessage> deleteUser(@PathVariable String userId) {
        logger.info("start request for delete user{}",userId);
        userService.deleteUser(userId);
        ApiResponceMessage message = ApiResponceMessage.builder().message(AppConstants.USER_DELETED).success(true).status(HttpStatus.OK).build();
       logger.info("request completed for delete user{}",userId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * @return  List of User
     * @apiNote This method for getAll user
     */
    @GetMapping
    public ResponseEntity<PageableResponce<UserDto>> getAllUser(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false)Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false)Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = "name",required = false)String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir
    ) {

        logger.info("start request for getAllUser");
        PageableResponce<UserDto> allUser = userService.getAllUser(pageNumber, pageSize, sortBy, sortDir);
        logger.info("complete request for getAllUser");
        return new ResponseEntity<>(allUser, HttpStatus.OK);
    }

    /**
     * @param userId
     * @return user by given userId
     * @apiNote This method for getSingle user by userId
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId) {
        logger.info("start request for get User by Id is{}",userId);
        UserDto userById = userService.getUserById(userId);
        logger.info("complete request for getUser by Id{}",userId);
        return new ResponseEntity<>(userById, HttpStatus.OK);

    }

    /**
     * @param email
     * @return user for given email
     * @apiNote This method for get user by given email
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        logger.info("start request for getUser by email {}", email);
        UserDto userByEmail = userService.getUserByEmail(email);
        logger.info("complete request for getUser by email {}",email);
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

    //uplode user image
    @PostMapping("/Image/{userId}")
    public  ResponseEntity<ImageResponce> uplodeUserImage(@RequestParam ("userImage") MultipartFile image,
                                                          @PathVariable String userId) throws IOException {
        String imageName = fileService.uplodFile(image, imageUploadPath);
        UserDto user = userService.getUserById(userId);
        user.setImageName(imageName);
        UserDto userDto = userService.updateUser(user, userId);
        ImageResponce imageResponce= ImageResponce.builder().imageName(imageName).success(true).message(AppConstants.SUCCESS).status(HttpStatus.CREATED).build();
        return new ResponseEntity<>(imageResponce,HttpStatus.CREATED);
    }

    //serve user image
    @GetMapping("/image/{userId}")
    public void serveUserImage(@PathVariable String userId , HttpServletResponse response) throws IOException {

        UserDto userDto = userService.getUserById(userId);
        logger.info("User image name {}", userDto.getImageName());
        InputStream resource = fileService.getResource(imageUploadPath, userDto.getImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());

    }
}
