package com.testingez.testingez.models.annotations;

import com.testingez.testingez.utils.validators.NotTakenValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = {NotTakenValidator.class})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotTaken {

    String message() default "This value is already taken.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
