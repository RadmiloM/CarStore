package com.cars.RentACar.dto;

import lombok.Data;

@Data
public class AdminRequestDTO {

    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String personalNumber;
    private String image;
    private String role;
}
