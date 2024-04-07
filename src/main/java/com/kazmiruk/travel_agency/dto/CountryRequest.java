package com.kazmiruk.travel_agency.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CountryRequest {

    @Size(min = 2, max = 80)
    private String name;

}
