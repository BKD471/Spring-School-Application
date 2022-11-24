package com.example.school.validation;

import com.example.school.annotation.PassWordValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class PassWordStrengthValidator implements ConstraintValidator<PassWordValidator,String> {
    /**
     * @param constraintAnnotation 
     */
     List<String> weakPasswords;

    @Override
    public void initialize(PassWordValidator constraintAnnotation) {
       weakPasswords= Arrays.asList("12345","password","qwerty");
    }

    /**
     * @param  passwordField
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(String passwordField, ConstraintValidatorContext constraintValidatorContext) {
        return  passwordField!=null && (!weakPasswords.contains(passwordField));
    }
}
