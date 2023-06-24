package com.cars.RentACar.exception;

public class EmailAndPasswordNotFoundException extends RuntimeException{

    public EmailAndPasswordNotFoundException(String message){
        super(message);
    }
}
