package com.kazmiruk.travel_agency.uti.error;

public class BookedTourNotFoundException extends RuntimeException {

    public BookedTourNotFoundException(String message) {
        super(message);
    }

}
