package com.lcwd.electronicstore.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDto {
    private Integer cartItemId;
    private ProductDto productDto;
    private Integer quantity;
    private Integer totalPrice;


}
