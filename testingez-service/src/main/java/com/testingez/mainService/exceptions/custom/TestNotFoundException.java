package com.testingez.mainService.exceptions.custom;

public class TestNotFoundException extends RuntimeException {
    public TestNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
