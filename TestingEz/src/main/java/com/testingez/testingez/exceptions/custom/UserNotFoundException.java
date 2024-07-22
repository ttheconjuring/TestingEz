package com.testingez.testingez.exceptions.custom;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String errorMessage, Throwable error) {
        super(errorMessage, error);
    }
}
