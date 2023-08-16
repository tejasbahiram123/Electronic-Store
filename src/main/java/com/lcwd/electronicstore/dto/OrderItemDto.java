package com.lcwd.electronicstore.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class OrderItemDto {
    private String orderItemId;

    private Integer quantity;

    private double totalPrice;

    private ProductDto product;

}
