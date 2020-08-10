package com.test.agro.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Calendar;

public class DateInSecondsValidator implements ConstraintValidator<DateInSeconds, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        Calendar calendar = Calendar.getInstance();

        long matchDate = calendar.toInstant().getEpochSecond();
        long dateInRequest = Long.parseLong(s);

        return dateInRequest > matchDate;
    }
}
