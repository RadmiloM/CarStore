package com.cars.RentACar.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class ContractDTO {

    private UUID userId;
    private UUID carId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "YYYY-MM-DD")
    private LocalDate startDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "YYYY-MM-DD")
    private LocalDate endDate;
    @NotNull
    private double totalPrice;
    @NotNull
    private boolean signed;
    private boolean approved;

}
