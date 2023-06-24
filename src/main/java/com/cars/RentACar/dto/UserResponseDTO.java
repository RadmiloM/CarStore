package com.cars.RentACar.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UserResponseDTO {

    private boolean successful;
    private UUID id;

}
