package com.kazmiruk.travel_agency.dto;

import lombok.Data;

@Data
public class ClientResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String passportNumber;
}
