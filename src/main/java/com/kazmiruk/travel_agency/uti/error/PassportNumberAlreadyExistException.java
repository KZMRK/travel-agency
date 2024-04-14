package com.kazmiruk.travel_agency.uti.error;

public class PassportNumberAlreadyExistException extends RuntimeException {

    public PassportNumberAlreadyExistException(String message) {
        super(message);
    }
}
