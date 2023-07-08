package com.lcwd.electronicstore.exception;

import com.lcwd.electronicstore.dto.ApiResponceMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //resource not found Handler
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponceMessage> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {

        log.info("Exception Handler invoked");
        ApiResponceMessage responce = ApiResponceMessage.builder().message(ex.getMessage()).success(true).status(HttpStatus.NOT_FOUND).build();

        return new ResponseEntity<>(responce, HttpStatus.NOT_FOUND);
    }
}
