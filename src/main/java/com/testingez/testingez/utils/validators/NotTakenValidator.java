package com.testingez.testingez.utils.validators;

import com.testingez.testingez.repositories.TestRepository;
import com.testingez.testingez.repositories.UserRepository;
import com.testingez.testingez.models.annotations.NotTaken;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class NotTakenValidator implements ConstraintValidator<NotTaken, String> {

    private final UserRepository userRepository;
    private final TestRepository testRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return this.userRepository.findByUsername(value).isEmpty() &&
                this.userRepository.findByEmail(value).isEmpty() &&
                this.userRepository.findByPhone(value).isEmpty() &&
                this.testRepository.findByCode(value).isEmpty();

    }
}
