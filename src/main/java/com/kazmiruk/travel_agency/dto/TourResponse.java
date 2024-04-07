package com.kazmiruk.travel_agency.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TourResponse {

    private Long id;

    private CountryResponse departure;

    private CountryResponse destination;

    private LocalDate departureAt;

    private LocalDate returnAt;

    private Double initialPrice;

    private GuideResponse guide;

}
