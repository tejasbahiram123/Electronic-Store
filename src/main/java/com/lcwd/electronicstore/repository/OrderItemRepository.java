package com.lcwd.electronicstore.repository;

import com.lcwd.electronicstore.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,String> {
}
