package com.example.school.annotation;

import com.example.school.validation.FieldsValueMatchValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FieldsValueMatchValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface  FieldsValueMatch {

    String message() default "Fields value don't match!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};

    String field();
    String fieldMatch();

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface  List{
        FieldsValueMatch[] value();
    }
}