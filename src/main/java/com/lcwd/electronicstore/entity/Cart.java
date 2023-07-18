package com.lcwd.electronicstore.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cart")
public class Cart {


    @Id
    private String cartId;
    private Date cratedAt;
    @OneToOne
    private User user;

    @OneToMany(mappedBy = "cart",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<CartItem> items= new ArrayList<>();

}
