package com.kazmiruk.travel_agency.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BookTourDto {

    @NotNull
    @Min(value = 0, message = "{price.min}")
    private BigDecimal sellingPrice;

}
