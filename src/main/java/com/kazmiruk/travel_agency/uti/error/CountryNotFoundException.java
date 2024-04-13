package com.kazmiruk.travel_agency.uti.error;

public class CountryNotFoundException extends RuntimeException {

    public CountryNotFoundException(String message) {
        super(message);
    }
}
