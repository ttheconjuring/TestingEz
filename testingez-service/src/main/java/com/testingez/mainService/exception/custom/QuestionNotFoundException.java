package com.testingez.mainService.exception.custom;

public class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
