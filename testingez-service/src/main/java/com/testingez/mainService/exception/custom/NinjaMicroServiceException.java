package com.testingez.mainService.exception.custom;

public class NinjaMicroServiceException extends Exception {
    public NinjaMicroServiceException(String errorMessage, Throwable error) {
        super(errorMessage, error);
    }
}
