package com.cars.RentACar.dto;

import com.cars.RentACar.validation.ValidPassword;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserRequestInfoDTO {
    @NotEmpty
    @Size(min = 4, message = "username must have at least 4 characters")
    private String username;
    @ValidPassword
    private String password;
    @ValidPassword
    private String newPassword;
    @NotEmpty
    @Size(min=4,message = "firstName must be at least 4 characters long")
    private String firstName;
    @NotEmpty
    @Size(min=4,message = "lastName must be at least 4 characters long")
    private String lastName;
    @NotEmpty
    private String phoneNumber;
    @NotEmpty
    private String image;
    @Pattern(regexp = "ADMIN|USER")
    private String role;
}
