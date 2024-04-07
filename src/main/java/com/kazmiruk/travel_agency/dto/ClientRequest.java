package com.kazmiruk.travel_agency.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClientRequest {

    @Size(min = 2, max = 50)
    private String firstName;

    @Size(min = 2, max = 50)
    private String lastName;

    @Pattern(regexp="\\d{9}", message = "The passport number must consist of 9 digits")
    private String passportNumber;

}
