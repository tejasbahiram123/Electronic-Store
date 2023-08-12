package com.lcwd.electronicstore.service.impl;

import com.lcwd.electronicstore.constant.AppConstants;
import com.lcwd.electronicstore.dto.AddItemToCartRequest;
import com.lcwd.electronicstore.dto.CartDto;
import com.lcwd.electronicstore.entity.Cart;
import com.lcwd.electronicstore.entity.CartItem;
import com.lcwd.electronicstore.entity.Product;
import com.lcwd.electronicstore.entity.User;
import com.lcwd.electronicstore.exception.BadApiRequestException;
import com.lcwd.electronicstore.exception.ResourceNotFoundException;
import com.lcwd.electronicstore.repository.CartItemRepository;
import com.lcwd.electronicstore.repository.CartRepository;
import com.lcwd.electronicstore.repository.ProductRepository;
import com.lcwd.electronicstore.repository.UserRepository;
import com.lcwd.electronicstore.service.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public CartDto addItemToCart(String userId, AddItemToCartRequest request) {

        Integer quantity = request.getQuantity();
        String productId = request.getProductId();
        if (quantity <= 0) {
            throw new BadApiRequestException(AppConstants.NOT_VALID);
        }
        //fetch product
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.PRODUCT_NOT_FOUND));

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER_NOT_FOUND));
        Cart cart = null;

        try {
            cartRepository.findByUser(user).get();
        } catch (NoSuchElementException e) {
            cart = new Cart();
            cart.setCartId(UUID.randomUUID().toString());
            cart.setCratedAt(new Date());
        }
        //if in cart items already present then update
        //perform cart operation
        AtomicReference<Boolean> updated = new AtomicReference<>(false);
        List<CartItem> items = cart.getItems();
        List<CartItem> updatedItems = items.stream().map(item -> {
            if (item.getProduct().getProductId().equals(productId)) {
                item.setQuantity(quantity);
                item.setTotalPrice(quantity * product.getPrice());
                updated.set(true);
            }
            return item;

        }).collect(Collectors.toList());

        cart.setItems(updatedItems);
        if (!updated.get()) {
            CartItem cartItems = CartItem.builder().quantity(quantity).
                    totalPrice(quantity * product.getPrice())
                    .cart(cart).product(product).build();

            cart.getItems().add(cartItems);
        }
        cart.setUser(user);
        Cart updatedCart = cartRepository.save(cart);

        return mapper.map(updatedCart, CartDto.class);


    }

    @Override
    public void removeItemFromCart(String userId, Integer cartItem) {

        CartItem cartItem1 = cartItemRepository.findById(cartItem).orElseThrow(() -> new ResourceNotFoundException(AppConstants.CART_ITEM_NOT_FOUND));
        cartItemRepository.delete(cartItem1);

    }

    @Override
    public void clearCart(String userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER_NOT_FOUND));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException(AppConstants.CART_NOT_FOUND));
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    @Override
    public CartDto getCartByUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER_NOT_FOUND));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException(AppConstants.CART_NOT_FOUND));
        return mapper.map(cart,CartDto.class);
    }
}
