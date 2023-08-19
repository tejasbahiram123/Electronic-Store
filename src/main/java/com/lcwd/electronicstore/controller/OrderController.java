package com.lcwd.electronicstore.controller;

import com.lcwd.electronicstore.constant.AppConstants;
import com.lcwd.electronicstore.dto.ApiResponceMessage;
import com.lcwd.electronicstore.dto.CreateOrderRequest;
import com.lcwd.electronicstore.dto.OrderDto;
import com.lcwd.electronicstore.dto.PageableResponce;
import com.lcwd.electronicstore.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    private Logger logger= LoggerFactory.getLogger(OrderController.class);

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        logger.info("Initiating logic for create Order");
        OrderDto order = orderService.createOrder(request);
        logger.info("Complete logic for create Order");
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponceMessage> removeOrder(@PathVariable String orderId) {
        logger.info("Initiating logic for remove Order{}",orderId);
        orderService.removeOrder(orderId);
        ApiResponceMessage responceMessage = ApiResponceMessage.builder().message(AppConstants.ORDER_REMOVED)
                .success(true)
                .status(HttpStatus.OK)
                .build();
        logger.info("Complete logic for remove Order{}",orderId);
        return new ResponseEntity<>(responceMessage, HttpStatus.OK);
    }
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<OrderDto>> getOrdersOfUser(@PathVariable String userId){
        logger.info("Initiating logic for getOrder Of User{}",userId);
        List<OrderDto> ordersOfUser = orderService.getOrdersOfUser(userId);
        logger.info("Complete logic for getOrder Of User{}",userId);
        return new ResponseEntity<>(ordersOfUser,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageableResponce<OrderDto>> getOrders(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir)
    {
        logger.info("Initiating logic for getOrders");
        PageableResponce<OrderDto> orders = orderService.getOrders(pageNumber,pageSize,sortBy,sortDir);
        logger.info("Complete logic for getOrders");
        return new ResponseEntity<>(orders,HttpStatus.OK);
    }
}
