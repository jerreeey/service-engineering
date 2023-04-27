package com.example.demo.exception;

import lombok.Getter;

@Getter
public class BookingNotFoundException extends RuntimeException {
    public BookingNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
