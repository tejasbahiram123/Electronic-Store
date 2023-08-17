package com.lcwd.electronicstore.service.impl;

import com.lcwd.electronicstore.dto.OrderDto;
import com.lcwd.electronicstore.dto.PageableResponce;
import com.lcwd.electronicstore.entity.User;
import com.lcwd.electronicstore.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public OrderDto createOrder(OrderDto orderDto, User userId) {
        return null;
    }

    @Override
    public void removeOrder(String orderId) {

    }

    @Override
    public List<OrderDto> getOrdersOfUser(String userId) {
        return null;
    }

    @Override
    public PageableResponce<OrderDto> getOrders(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        return null;
    }
}
