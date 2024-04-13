package com.kazmiruk.travel_agency.uti.exception;

import com.kazmiruk.travel_agency.dto.ErrorDto;
import com.kazmiruk.travel_agency.uti.error.ClientNotFoundException;
import com.kazmiruk.travel_agency.uti.error.CountryNotFoundException;
import com.kazmiruk.travel_agency.uti.error.GuideNotFoundException;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(
            MethodArgumentNotValidException e
    ) {
        log.error("Error: {}", e.getMessage(), e);
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

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({CountryNotFoundException.class, GuideNotFoundException.class, ClientNotFoundException.class})
    public ResponseEntity<ErrorDto> handleNotFoundException(RuntimeException e) {
        log.error("Error: {}", e.getMessage(), e);
        ErrorDto errorDto = new ErrorDto(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                e.getMessage()
        );
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleException(Exception e) {
        log.error("Error: {}", e.getMessage(), e);
        ErrorDto errorDto = new ErrorDto(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.getMessage()
        );
        return ResponseEntity.internalServerError().body(errorDto);
    }

}
