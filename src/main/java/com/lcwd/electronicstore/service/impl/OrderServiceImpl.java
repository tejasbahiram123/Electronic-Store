package com.lcwd.electronicstore.service.impl;

import com.lcwd.electronicstore.constant.AppConstants;
import com.lcwd.electronicstore.dto.CreateOrderRequest;
import com.lcwd.electronicstore.dto.OrderDto;
import com.lcwd.electronicstore.dto.PageableResponce;
import com.lcwd.electronicstore.entity.*;
import com.lcwd.electronicstore.exception.BadApiRequestException;
import com.lcwd.electronicstore.exception.ResourceNotFoundException;
import com.lcwd.electronicstore.helper.Helper;
import com.lcwd.electronicstore.repository.CartRepository;
import com.lcwd.electronicstore.repository.OrderRepository;
import com.lcwd.electronicstore.repository.UserRepository;
import com.lcwd.electronicstore.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ModelMapper mapper;


    @Override
    public OrderDto createOrder(CreateOrderRequest orderDto) {
        String userId = orderDto.getUserId();
        String cartId = orderDto.getCartId();

        //fetch user
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER_NOT_FOUND));
        //fetch cart
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.CATEGORY_NOT_FOUND));

        List<CartItem> cartItems = cart.getItems();

        if (cartItems.size() <= 0) {
            throw new BadApiRequestException("Invalid ,No Items is found in cart");
        }

        Order order = Order.builder().billingName(orderDto.getBillingName())
                .billingAddress(orderDto.getBillingAddress())
                .billingPhone(orderDto.getBillingPhone())
                .orderedDate(new Date())
                .deliveredDate(null)
                .orderStatus(orderDto.getOrderStatus())
                .paymentStatus(orderDto.getPaymentStatus())
                .orderId(UUID.randomUUID().toString())
                .user(user)
                .build();

        AtomicReference<Double> orderAmount = new AtomicReference<>(0.0);
        List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {
            OrderItem orderItem = OrderItem.builder().quantity(cartItem.getQuantity())
                    .product(cartItem.getProduct())
                    .totalPrice(cartItem.getQuantity() * cartItem.getProduct().getDiscountedPrice())
                    .order(order)
                    .build();

            orderAmount.set(orderAmount.get() + orderItem.getTotalPrice());
            return orderItem;
        }).collect(Collectors.toList());

        order.setOrderItems(orderItems);
        order.setOrderAmount(orderAmount.get());

        order.setOrderItems(orderItems);

        //after clear cart
        cart.getItems().clear();
        cartRepository.save(cart);
        Order saveOrder = orderRepository.save(order);
        return mapper.map(saveOrder, OrderDto.class);
    }

    @Override
    public void removeOrder(String orderId) {

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.ORDER_NOT_FOUND));
        orderRepository.delete(order);
    }

    @Override
    public List<OrderDto> getOrdersOfUser(String userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER_NOT_FOUND));
        List<Order> orders = orderRepository.findByUser(user);
        List<OrderDto> orderDtos = orders.stream().map(order -> mapper.map(order, OrderDto.class)).collect(Collectors.toList());
        return orderDtos;
    }

    @Override
    public PageableResponce<OrderDto> getOrders(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        PageRequest pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Order> page = orderRepository.findAll(pageable);

        return Helper.getPageableResponce(page,OrderDto.class);
    }
}
