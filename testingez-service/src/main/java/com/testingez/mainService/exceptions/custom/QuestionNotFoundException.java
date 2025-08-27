package com.testingez.mainService.exceptions.custom;

public class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
