package com.lcwd.electronicstore.service;

import com.lcwd.electronicstore.dto.OrderDto;
import com.lcwd.electronicstore.dto.PageableResponce;
import com.lcwd.electronicstore.entity.User;

import java.util.List;

public interface OrderService{

    OrderDto createOrder(OrderDto orderDto, String userId,String cartId);

    void removeOrder(String orderId);

    List<OrderDto> getOrdersOfUser(String userId);

    PageableResponce<OrderDto> getOrders(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

}
