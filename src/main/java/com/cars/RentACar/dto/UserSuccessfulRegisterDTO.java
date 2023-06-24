package com.cars.RentACar.dto;

import lombok.Data;

@Data
public class UserSuccessfulRegisterDTO {
    private boolean successful;
    private String message;
}
