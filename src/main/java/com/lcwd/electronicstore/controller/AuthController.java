package com.lcwd.electronicstore.controller;

import com.lcwd.electronicstore.dto.JwtRequest;
import com.lcwd.electronicstore.dto.JwtResponse;
import com.lcwd.electronicstore.dto.UserDto;
import com.lcwd.electronicstore.exception.BadApiRequestException;
import com.lcwd.electronicstore.security.JwtHelper;
import com.lcwd.electronicstore.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtHelper helper;


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
        this.doAuthenticate(request.getEmail(), request.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = this.helper.generateToken(userDetails);

        UserDto userDto = mapper.map(userDetails, UserDto.class);
        JwtResponse response = JwtResponse.builder().jwtToken(token).user(userDto).build();

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authentication);
        } catch (BadCredentialsException e) {

            throw new BadApiRequestException("Invalid Username or Password Exception");
        }
    }

    @GetMapping("/current")
    public ResponseEntity<UserDto> getCurrentUser(Principal principal){
        String name = principal.getName();
        return new ResponseEntity<>(mapper.map(userDetailsService.loadUserByUsername(name),UserDto.class),HttpStatus.OK);

    }

}
