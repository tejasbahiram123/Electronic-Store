package com.lcwd.electronicstore.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @Column(name = "id")
    private String productId;

    @Column(name = "product_title",nullable = true)
    private String title;

    @Column(name = "description",length = 500)
    private String description;

    @Column(name = "price")
    private double price;

    @Column(name = "discounted_price")
    private double discountedPrice;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "added_date")
    private Date addedDate;

    @Column(name = "product_live")
    private boolean live;

    @Column(name = "product_stock")
    private boolean stock;

    private String productImageName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;
}
