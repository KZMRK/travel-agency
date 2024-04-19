package com.kazmiruk.travel_agency.model.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
public class TourDto {

    private Long id;

    @NotNull
    private CountryDto departure;

    @NotNull
    private CountryDto destination;

    @Future(message = "{date.future}")
    private LocalDate departureAt;

    @Future(message = "{date.future}")
    private LocalDate returnAt;

    @Min(value = 1, message = "{price.min}")
    private Double initialPrice;

    @NotNull
    private GuideDto guide;

}
