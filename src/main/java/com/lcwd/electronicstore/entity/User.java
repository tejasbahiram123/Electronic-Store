package com.lcwd.electronicstore.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
  //  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userId;

   @Column(name = "user_name")
   private String name;

   @Column(name = "user_email")
    private String email;

   @Column(name = "user_password")
    private String password;

   @Column(name = "gender")
    private String gender;

    @Column(name="about")
    private String about;

    @Column(name = "user_image_name")
    private String imageName;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List<Order> orders=new ArrayList<>();

}
