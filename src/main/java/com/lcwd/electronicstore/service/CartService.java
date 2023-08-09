package com.lcwd.electronicstore.service;

import com.lcwd.electronicstore.dto.AddItemToCartRequest;
import com.lcwd.electronicstore.dto.CartDto;

public interface CartService {


    //case1: cart for user is not available :we will create the cart
    //case2: cart is available add the items to cart.

    CartDto addItemToCart(String userId, AddItemToCartRequest request);

    //remove item from cart
    void removeItemFromCart(String userId, Integer cartItem);

    //remove all items from cart
    void clearCart(String userId);
}
