package com.cars.RentACar.dto;

import lombok.Data;

@Data
public class CarDTO {

    private String licencePlate;
    private String make;
    private String model;
    private int year;
    private int engineCapacity;
    private String color;
    private double price;
    private int doors;
    private char size;
    private int power;
    private boolean automatic;
    private String fuel;
    private String image;
}
