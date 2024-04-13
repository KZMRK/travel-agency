package com.kazmiruk.travel_agency.dto;

import com.kazmiruk.travel_agency.uti.validation.RequestBodyNestedEntity;
import lombok.Data;

@Data
@RequestBodyNestedEntity(idFieldName = "id")
public class CountryDto {


    private Integer id;

    private String name;

}
