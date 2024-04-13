package com.kazmiruk.travel_agency.dto;

import com.kazmiruk.travel_agency.uti.validation.RequestBodyNestedEntity;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@RequestBodyNestedEntity(idFieldName = "id")
public class GuideDto {

    private Long id;

    @Size(min = 2, max = 50, message = "{firstname.size}")
    @Pattern(
            regexp = "^[A-Za-z-]+$",
            message = "{firstname.pattern}"
    )
    private String firstName;

    @Size(min = 2, max = 50, message = "{lastname.size}")
    @Pattern(
            regexp = "^[A-Za-z-]+$",
            message = "{lastname.pattern}"
    )
    private String lastName;

}
