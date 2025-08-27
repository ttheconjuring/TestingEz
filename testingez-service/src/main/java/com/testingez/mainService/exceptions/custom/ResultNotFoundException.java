package com.testingez.mainService.exceptions.custom;

public class ResultNotFoundException extends RuntimeException {
    public ResultNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
