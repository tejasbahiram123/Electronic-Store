package com.lcwd.electronicstore.entity;

import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;

@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartItemId;

    @OneToOne
    private Product product;
    private Integer quantity;
    private Integer totalPrice;

}
