package com.testingez.mainService.exception.custom;

public class TestNotFoundException extends RuntimeException {
    public TestNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
