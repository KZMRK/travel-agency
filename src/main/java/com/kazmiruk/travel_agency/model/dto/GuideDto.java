package com.kazmiruk.travel_agency.model.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
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
