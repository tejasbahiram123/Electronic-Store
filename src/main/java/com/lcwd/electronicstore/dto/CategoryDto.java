package com.lcwd.electronicstore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private String categoryId;

    @NotBlank(message = "Title is required..")
    @Size(min = 4,message = "title is minimum 4 charcters")
    private String title;

    @NotBlank(message = "description is required..")
    private String description;

    private String coverImage;
}
