package com.lcwd.electronicstore.controller;

import com.lcwd.electronicstore.constant.AppConstants;
import com.lcwd.electronicstore.dto.AddItemToCartRequest;
import com.lcwd.electronicstore.dto.ApiResponceMessage;
import com.lcwd.electronicstore.dto.CartDto;
import com.lcwd.electronicstore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    //add item to cart
    @PostMapping("/{userId}")
    public ResponseEntity<CartDto> addItemToCart(@PathVariable String userId, @RequestBody AddItemToCartRequest request) {
        CartDto cartDto = cartService.addItemToCart(userId, request);
        return new ResponseEntity(cartDto, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/items/{itemId}")
    public ResponseEntity<ApiResponceMessage> removeItemFromCart(@PathVariable String userId, @PathVariable Integer itemId) {
        cartService.removeItemFromCart(userId, itemId);
        ApiResponceMessage responce = ApiResponceMessage.builder().message(AppConstants.ITEM_REMOVED)
                .status(HttpStatus.OK).success(true).build();
        return  new ResponseEntity<>(responce,HttpStatus.OK);
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponceMessage> clearCart(@PathVariable String userId) {
        cartService.clearCart(userId);
        ApiResponceMessage responce = ApiResponceMessage.builder().message(AppConstants.CLEAR_CART)
                .status(HttpStatus.OK).success(true).build();
        return  new ResponseEntity<>(responce,HttpStatus.OK);
    }

}
