package com.example.school.annotation;

import com.example.school.validation.PassWordStrengthValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented()
@Constraint(validatedBy = PassWordStrengthValidator.class)
@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PassWordValidator {
    String message() default "Password is too weak!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
