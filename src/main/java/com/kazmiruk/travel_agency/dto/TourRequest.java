package com.kazmiruk.travel_agency.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TourRequest {

    @Valid
    @NotNull
    private CountryDto departure;

    @Valid
    @NotNull
    private CountryDto destination;

    @Future(message = "{date.future}")
    private LocalDate departureAt;

    @Future(message = "{date.future}")
    private LocalDate returnAt;

    @Min(value = 1, message = "{price.min}")
    private Double initialPrice;

    @Valid
    @NotNull
    private GuideDto guide;

}
