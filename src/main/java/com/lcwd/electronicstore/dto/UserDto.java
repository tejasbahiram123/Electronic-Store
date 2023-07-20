package com.lcwd.electronicstore.dto;
import com.lcwd.electronicstore.validate.ImageNameValid;
import lombok.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {


    private String userId;

    @Size(min = 3,max = 20 ,message = "Invalid name..")
    private String name;

    @Email(message = "Invalid email Id..")
    @NotBlank(message = "Email Id Is Required...")
    @Pattern(regexp = "[a-z0-9]+@[a-z]+\\.[a-z]{2,3}",message = "Invalid Email Id..")
    private String email;

    @NotBlank
    @Size(min = 8,message = "In password is minimum Eight Characters required  ")
    private String password;

    @Size(min = 4,max = 6 ,message = "Invalid gender")
    private String gender;

    @NotBlank(message = "Write Something in about..")
    private String about;

    @ImageNameValid
    private String imageName;
}
