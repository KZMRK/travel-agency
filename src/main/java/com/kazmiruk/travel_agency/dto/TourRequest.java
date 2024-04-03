package com.kazmiruk.travel_agency.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TourRequest {

    private CountryDto departure;

    private CountryDto destination;

    private LocalDate departureAt;

    private LocalDate returnAt;

    private Double initialPrice;

    private GuideDto guide;

}
