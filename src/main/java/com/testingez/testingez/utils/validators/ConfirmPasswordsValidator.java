package com.testingez.testingez.utils.validators;

import com.testingez.testingez.models.annotations.ConfirmPasswords;
import com.testingez.testingez.models.dtos.imp.UserSignUpDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class ConfirmPasswordsValidator implements ConstraintValidator<ConfirmPasswords, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        UserSignUpDTO user = (UserSignUpDTO) value;
        return user.getPassword().equals(user.getConfirmPassword());
    }

}
