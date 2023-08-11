package com.lcwd.electronicstore.repository;

import com.lcwd.electronicstore.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Integer> {


}
