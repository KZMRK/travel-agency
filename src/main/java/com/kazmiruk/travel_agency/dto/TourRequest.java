package com.kazmiruk.travel_agency.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TourRequest {

    @NotNull
    private CountryDto departure;

    @NotNull
    private CountryDto destination;

    @Future(message = "The date must be in the future")
    private LocalDate departureAt;

    @Future(message = "The date must be in the future")
    private LocalDate returnAt;

    @NotNull
    private Double initialPrice;

    @NotNull
    private GuideDto guide;

}
