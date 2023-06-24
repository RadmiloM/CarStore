package com.cars.RentACar.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;
@Data
@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID carId;
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
    @OneToMany(mappedBy = "car",orphanRemoval = true)
    private List<Contract> contracts;

}
