package com.kazmiruk.travel_agency.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CountryRequest {

    @Pattern(
            regexp = "^[A-Za-z\\s-]+$",
            message = "{country.name.pattern}"
    )
    @Size(min = 2, max = 80, message = "{country.name.size}")
    private String name;

}
