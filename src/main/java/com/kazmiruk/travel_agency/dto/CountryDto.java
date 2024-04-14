package com.kazmiruk.travel_agency.dto;

import com.kazmiruk.travel_agency.uti.validation.RequestBodyNestedEntity;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@RequestBodyNestedEntity(idFieldName = "id")
public class CountryDto {

    private Integer id;

    @Size(min = 2, max = 80, message = "{country.name.size}")
    private String name;

}
