package com.kazmiruk.travel_agency.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookTourRequest {

    @NotNull
    @Min(value = 0, message = "{price.min}")
    private Double sellingPrice;

}
