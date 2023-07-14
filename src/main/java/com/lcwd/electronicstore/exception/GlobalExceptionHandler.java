package com.lcwd.electronicstore.exception;

import com.lcwd.electronicstore.dto.ApiResponceMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
      log.info("Method_Argument_Not_Valid_Exception handler invoked");
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        Map<String, Object> responce = new HashMap<>();
        allErrors.stream().forEach(ObjectError -> {
            String message = ObjectError.getDefaultMessage();
            String field = ((FieldError) ObjectError).getField();
            responce.put(field, message);
        });

        return new ResponseEntity<>(responce, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadApiRequestException.class)
    public ResponseEntity<ApiResponceMessage> handleBadApiRequest(BadApiRequestException ex) {

        log.info("Bad Api request Handler invoked");
        ApiResponceMessage responce = ApiResponceMessage.builder().message(ex.getMessage()).success(false).status(HttpStatus.BAD_REQUEST).build();
        return new ResponseEntity<>(responce, HttpStatus.BAD_REQUEST);
    }
}
