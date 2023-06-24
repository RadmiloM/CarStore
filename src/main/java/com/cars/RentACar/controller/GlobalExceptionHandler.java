package com.cars.RentACar.controller;

import com.cars.RentACar.entity.User;
import com.cars.RentACar.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exceptions,
                                                               HttpHeaders headers,
                                                               HttpStatus status,
                                                               WebRequest request) {
        HashMap<String, String> errors = new HashMap<>();
        exceptions.getBindingResult().getAllErrors().forEach((error) -> {
            String rentACarFieldError = ((FieldError) error).getField();
            String rentACarMessage = error.getDefaultMessage();
            errors.put(rentACarFieldError, rentACarMessage);

        });
        return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(HandlePSQLException.class)
    public ResponseEntity<String> handlePSQLException(HandlePSQLException handlePSQLException) {
        return new ResponseEntity<>(handlePSQLException.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmailAndPasswordNotFoundException.class)
    public ResponseEntity<String> handleEmailAndPasswordNotFoundException(EmailAndPasswordNotFoundException emailAndPasswordNotFoundException) {
        return new ResponseEntity<>(emailAndPasswordNotFoundException.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(PasswordNotFoundException.class)
    public ResponseEntity<String> handlePasswordNotFoundException(PasswordNotFoundException passwordNotFoundException) {
        return new ResponseEntity<>(passwordNotFoundException.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException userNotFoundException) {
        return new ResponseEntity<>(userNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CarNotFoundException.class)
    public ResponseEntity<String> handleCarNotFoundException(CarNotFoundException carNotFoundException) {
        return new ResponseEntity<>(carNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ContractNotFoundException.class)
    public ResponseEntity<String> handleContractNotFoundException(ContractNotFoundException contractNotFoundException) {
        return new ResponseEntity<>(contractNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CarNotAvailableException.class)
    public ResponseEntity<String> handleCarNotAvailableException(CarNotAvailableException carNotAvailableException) {
        return new ResponseEntity<>(carNotAvailableException.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotAllowed.class)
    public ResponseEntity<String> handleUserNotAllowedException(UserNotAllowed userNotAllowed) {
        return new ResponseEntity<>(userNotAllowed.getMessage(), HttpStatus.FORBIDDEN);
    }
}
