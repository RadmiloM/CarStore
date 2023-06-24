package com.cars.RentACar.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class ContractSignResponseDTO {

    private UUID userId;
    private UUID carId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "YYYY-MM-DD")
    private LocalDate startDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "YYYY-MM-DD")
    private LocalDate endDate;
    private double totalPrice;
    private boolean signed;
}
