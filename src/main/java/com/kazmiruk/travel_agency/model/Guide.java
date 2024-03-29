package com.kazmiruk.travel_agency.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Guide {

    @Id
    @GeneratedValue
    private Long id;

    private String firstName;

    private String lastName;
}
