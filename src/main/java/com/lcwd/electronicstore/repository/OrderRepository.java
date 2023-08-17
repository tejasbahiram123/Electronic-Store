package com.lcwd.electronicstore.repository;

import com.lcwd.electronicstore.entity.Order;
import com.lcwd.electronicstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order ,String> {


    List<Order> findByUser(User user);
}
