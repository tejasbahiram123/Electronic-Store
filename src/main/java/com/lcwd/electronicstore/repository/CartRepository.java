package com.lcwd.electronicstore.repository;

import com.lcwd.electronicstore.entity.Cart;
import com.lcwd.electronicstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, String> {

    Optional<Cart> findByUser(User user);
}
