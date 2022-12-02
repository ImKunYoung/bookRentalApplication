package com.my.rental.web.rest.errors;

public class RentalUnavailableException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public RentalUnavailableException(String message) {
        super(message);
    }
}
