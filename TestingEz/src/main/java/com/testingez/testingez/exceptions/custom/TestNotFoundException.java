package com.testingez.testingez.exceptions.custom;

public class TestNotFoundException extends RuntimeException {
    public TestNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
