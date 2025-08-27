package com.testingez.mainService.exception.custom;

public class ResultNotFoundException extends RuntimeException {
    public ResultNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
