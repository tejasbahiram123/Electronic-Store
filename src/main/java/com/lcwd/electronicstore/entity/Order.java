package com.lcwd.electronicstore.entity;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "orders")
public class Order {

    @Id
    private String orderId;

    private String orderStatus;

    private String paymentStatus;

    private double orderAmount;

    private String billingAddress;

    private String billingPhone;

    private String billingName;

    private Date orderedDate;

    private Date deliveredDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order",fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    private List<OrderItem> orderItems = new ArrayList<>();
}
