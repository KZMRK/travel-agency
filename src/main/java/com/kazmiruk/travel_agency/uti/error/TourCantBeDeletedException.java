package com.kazmiruk.travel_agency.uti.error;

public class TourCantBeDeletedException extends RuntimeException {

    public TourCantBeDeletedException(String message) {
        super(message);
    }
}
