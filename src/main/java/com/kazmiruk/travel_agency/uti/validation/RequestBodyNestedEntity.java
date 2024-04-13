package com.kazmiruk.travel_agency.uti.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = RequestBodyNestedEntityValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestBodyNestedEntity {

    String message() default "Must contain 'id' if exists or class fields without 'id' if to be created";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String idFieldName();

}
