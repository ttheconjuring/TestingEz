package com.testingez.testingez.utils;

import com.testingez.testingez.repositories.UserRepository;
import com.testingez.testingez.models.annotations.NotTaken;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotTakenValidator implements ConstraintValidator<NotTaken, String> {

    private final UserRepository userRepository;
    private String fieldName;

    public NotTakenValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void initialize(NotTaken constraintAnnotation) {
        fieldName = constraintAnnotation.fieldName();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return switch (this.fieldName) {
            case "username" -> this.userRepository.findByUsername(value).isEmpty();
            case "email" -> this.userRepository.findByEmail(value).isEmpty();
            case "phone" -> this.userRepository.findByPhone(value).isEmpty();
            default -> throw new IllegalArgumentException("Invalid filed name is passed");
        };
    }
}
