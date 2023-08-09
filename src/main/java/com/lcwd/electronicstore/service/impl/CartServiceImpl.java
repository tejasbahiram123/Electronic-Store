package com.lcwd.electronicstore.service.impl;

import com.lcwd.electronicstore.dto.AddItemToCartRequest;
import com.lcwd.electronicstore.dto.CartDto;
import com.lcwd.electronicstore.service.CartService;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {
    @Override
    public CartDto addItemToCart(String userId, AddItemToCartRequest request) {
        return null;
    }

    @Override
    public void removeItemFromCart(String userId, Integer cartItem) {

    }

    @Override
    public void clearCart(String userId) {

    }
}
