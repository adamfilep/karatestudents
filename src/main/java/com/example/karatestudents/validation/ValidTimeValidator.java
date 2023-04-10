package com.example.karatestudents.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalTime;

public class ValidTimeValidator implements ConstraintValidator<ValidTime, LocalTime> {


    @Override
    public void initialize(ValidTime constraintAnnotation) {
    }

    @Override
    public boolean isValid(LocalTime value, ConstraintValidatorContext context) {
        return value.toString().matches("^(?:[01]\\d|2[0-3]):[0-5]\\d(?::[0-5]\\d)?$");
    }
}
