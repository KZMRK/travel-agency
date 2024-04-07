package com.kazmiruk.travel_agency.uti.error;

public class SameTimeFrameException extends RuntimeException {

    public SameTimeFrameException(String message) {
        super(message);
    }
}
