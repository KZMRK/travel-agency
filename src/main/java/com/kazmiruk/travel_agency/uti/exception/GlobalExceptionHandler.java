package com.kazmiruk.travel_agency.uti.exception;

import com.kazmiruk.travel_agency.model.dto.ErrorDto;
import com.kazmiruk.travel_agency.model.exception.AlreadyExistException;
import com.kazmiruk.travel_agency.model.exception.BadRequestException;
import com.kazmiruk.travel_agency.model.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto<Map<String, List<String>>>> handleValidationErrors(
            MethodArgumentNotValidException e
    ) {
        Map<String, List<String>> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            List<String> fieldErrors;
            if (errors.containsKey(fieldName)) {
                fieldErrors = errors.get(fieldName);
            } else {
                fieldErrors = new ArrayList<>();
            }
            fieldErrors.add(errorMessage);
            errors.put(fieldName, fieldErrors);
        });

        ErrorDto<Map<String, List<String>>> errorDto = new ErrorDto<>(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                errors
        );


        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDto<String>> handleNotFoundException(RuntimeException e) {
        ErrorDto<String> errorDto = new ErrorDto<>(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                e.getMessage()
        );
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({BadRequestException.class, AlreadyExistException.class})
    public ResponseEntity<ErrorDto<String>> handleBadRequestException(RuntimeException e) {
        ErrorDto<String> errorDto = new ErrorDto<>(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage()
        );
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto<String>> handleException(Exception e) {
        ErrorDto<String> errorDto = new ErrorDto<>(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.getMessage()
        );
        return ResponseEntity.internalServerError().body(errorDto);
    }

}
