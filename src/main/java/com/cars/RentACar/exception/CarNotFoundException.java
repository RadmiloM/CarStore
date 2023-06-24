package com.cars.RentACar.exception;

public class CarNotFoundException extends RuntimeException {

    public CarNotFoundException(String message){
        super(message);
    }
}
