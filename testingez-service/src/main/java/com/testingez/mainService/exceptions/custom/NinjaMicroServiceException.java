package com.testingez.mainService.exceptions.custom;

public class NinjaMicroServiceException extends Exception {
    public NinjaMicroServiceException(String errorMessage, Throwable error) {
        super(errorMessage, error);
    }
}
