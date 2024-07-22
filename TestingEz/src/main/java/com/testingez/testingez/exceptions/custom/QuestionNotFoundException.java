package com.testingez.testingez.exceptions.custom;

public class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException(String errorMessage, Throwable error) {
        super(errorMessage, error);
    }
}
