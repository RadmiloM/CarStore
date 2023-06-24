package com.cars.RentACar.exception;

public class ContractNotFoundException extends RuntimeException {

    public ContractNotFoundException(String message){
        super(message);
    }
}
