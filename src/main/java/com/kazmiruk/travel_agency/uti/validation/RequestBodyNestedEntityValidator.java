package com.kazmiruk.travel_agency.uti.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.Objects;

public class RequestBodyNestedEntityValidator implements ConstraintValidator<RequestBodyNestedEntity, Object> {

    private String idFieldName;

    @Override
    public void initialize(RequestBodyNestedEntity constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        idFieldName = constraintAnnotation.idFieldName();
    }

    @SneakyThrows
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Object idField = findIdField(value);

        boolean isIdFieldNull = Objects.isNull(idField);

        Field[] fields = value.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (field.getName().equals(idFieldName)) {
                continue;
            }
            field.setAccessible(true);
            Object fieldValue = field.get(value);
            if (isIdFieldNull && Objects.isNull(fieldValue) || !isIdFieldNull && Objects.nonNull(fieldValue)) {
                return false;
            }
        }
        return true;
    }

    @SneakyThrows
    private Object findIdField(Object value) {
        Field[] fields = value.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals(idFieldName)) {
                field.setAccessible(true);
                return field.get(value);
            }
        }
        throw new IllegalArgumentException("Class " + value.getClass() + " doesn't contain a field named " + idFieldName);
    }
}
