package com.kazmiruk.travel_agency.uti.error;

public class CountryWithNameAlreadyExistException extends RuntimeException {

    public CountryWithNameAlreadyExistException(String message) {
        super(message);
    }

}
