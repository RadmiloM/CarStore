package com.cars.RentACar.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UserSuccessfulLoginDTO {

    private UUID userId;
    private boolean successful;
}
