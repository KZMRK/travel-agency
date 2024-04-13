package com.kazmiruk.travel_agency.dto;

import com.kazmiruk.travel_agency.uti.validation.RequestBodyNestedEntity;
import lombok.Data;

@Data
@RequestBodyNestedEntity(idFieldName = "id")
public class GuideDto {

    private Long id;

    private String firstName;

    private String lastName;

}
