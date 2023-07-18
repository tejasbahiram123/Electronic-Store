package com.lcwd.electronicstore.dto;

import com.lcwd.electronicstore.entity.Category;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDto {

    private String productId;
    @NonNull
    @Size(min = 3,max = 25)
    private String title;
    @NotBlank(message = "Give the Description")
    private String description;
    private double price;
    private double discountedPrice;
    private Integer quantity;
    private Date addedDate;
    private boolean live;
    private boolean stock;
    private String productImageName;
    private CategoryDto category;
}
