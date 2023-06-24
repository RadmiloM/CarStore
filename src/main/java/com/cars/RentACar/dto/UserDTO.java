package com.cars.RentACar.dto;

import com.cars.RentACar.validation.ValidPassword;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class UserDTO {
    @NotEmpty
    @Size(min = 3, message = "username must be at least 3 characters long")
    private String username;

    @Email
    private String email;

    @ValidPassword
    private String password;
}
