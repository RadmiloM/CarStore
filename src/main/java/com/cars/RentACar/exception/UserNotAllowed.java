package com.cars.RentACar.exception;

public class UserNotAllowed extends RuntimeException {

    public UserNotAllowed(String message){
        super(message);
    }
}
