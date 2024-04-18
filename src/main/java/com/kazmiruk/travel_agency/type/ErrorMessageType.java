package com.kazmiruk.travel_agency.type;

import lombok.Getter;

@Getter
public enum ErrorMessageType {

    COUNTRY_NAME_ALREADY_EXIST("Country with name '%s' already exists"),
    CLIENT_WITH_PASSPORT_ALREADY_EXIST("Client with passport number '%s' already exist"),

    COUNTRY_NOT_FOUND("Country with id %d not found"),
    CLIENT_NOT_FOUND("Client with id %d not found"),
    TOUR_NOT_FOUND("Tour with id %d not found"),
    GUIDE_NOT_FOUND("Guide with id %d not found"),
    BOOKING_NOT_FOUND("Client with id %d didn't book a tour with id %d"),

    NO_TOURS_IN_YEAR("There are no tours in %d"),
    MAX_DISCOUNT_EXCEEDED("Discount %.1f%% exceeds the maximum discount %.1f%% (min selling price - %.1f)"),
    BOOKING_TIME_FRAME("You can't book 2 tours at the same time");

    private final String message;

    ErrorMessageType(String message) {
        this.message = message;
    }

}
