package com.testingez.testingez.exceptions.custom;

public class NinjaMicroServiceException extends Exception {
    public NinjaMicroServiceException(String errorMessage, Throwable error) {
        super(errorMessage, error);
    }
}
