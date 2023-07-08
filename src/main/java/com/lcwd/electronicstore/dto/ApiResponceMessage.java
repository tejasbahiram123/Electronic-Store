package com.lcwd.electronicstore.dto;

import lombok.*;
import org.springframework.http.HttpStatus;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponceMessage {

    private String message;
    private  boolean success;
    private HttpStatus status;
}
