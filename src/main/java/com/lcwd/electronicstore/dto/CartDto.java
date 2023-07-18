package com.lcwd.electronicstore.dto;


import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDto {

    private String cartId;
    private Date cratedAt;
    private UserDto user;
    private List<CartItemDto> items= new ArrayList<>();

}
