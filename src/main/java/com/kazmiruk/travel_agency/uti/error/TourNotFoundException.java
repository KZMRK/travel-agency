package com.kazmiruk.travel_agency.uti.error;

public class TourNotFoundException extends RuntimeException {

    public TourNotFoundException(String message) {
        super(message);
    }
}
