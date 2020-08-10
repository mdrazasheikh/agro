package com.test.agro.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateInSecondsValidator.class)
public @interface DateInSeconds {
    String message() default "date is invalid. Date should be in epoch seconds format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
