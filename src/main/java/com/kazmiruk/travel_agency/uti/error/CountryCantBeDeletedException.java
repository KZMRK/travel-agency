package com.kazmiruk.travel_agency.uti.error;

public class CountryCantBeDeletedException extends RuntimeException {

    public CountryCantBeDeletedException(String message) {
        super(message);
    }
}
