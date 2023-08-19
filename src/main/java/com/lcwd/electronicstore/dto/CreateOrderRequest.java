package com.lcwd.electronicstore.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CreateOrderRequest {

    @NotBlank(message = "userId is required")
    private String userId;
    @NotBlank(message = "cartId is required")
    private String cartId;
    private String orderStatus="PENDING";
    private String paymentStatus="NOTPAID";
    @NotBlank(message = "Billing Address is required")
    private String billingAddress;
    @NotBlank(message = "Billing Phone is required")
    private String billingPhone;
    @NotBlank(message = "Billing Name is required")
    private String billingName;

}
