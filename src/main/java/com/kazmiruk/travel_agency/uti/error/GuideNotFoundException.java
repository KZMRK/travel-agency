package com.kazmiruk.travel_agency.uti.error;

public class GuideNotFoundException extends RuntimeException {

    public GuideNotFoundException(String message) {
        super(message);
    }
}
