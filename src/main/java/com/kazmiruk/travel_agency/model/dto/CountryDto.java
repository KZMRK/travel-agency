package com.kazmiruk.travel_agency.model.dto;

import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class CountryDto {

    private Integer id;

    @Size(min = 2, max = 80, message = "{country.name.size}")
    private String name;

}
