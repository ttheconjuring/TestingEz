package com.testingez.testingez.exceptions.custom;

public class ResultNotFoundException extends RuntimeException {
    public ResultNotFoundException(String errorMessage, Throwable error) {
        super(errorMessage, error);
    }
}
