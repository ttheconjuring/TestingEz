package com.testingez.testingez.utils;

import com.testingez.testingez.repositories.UserRepository;
import com.testingez.testingez.models.annotations.NotTaken;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NotTakenValidator implements ConstraintValidator<NotTaken, String> {

    private final UserRepository userRepository;

    @Override
    public void initialize(NotTaken constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return this.userRepository.findByUsername(value).isEmpty();
    }
}
