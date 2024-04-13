package com.kazmiruk.travel_agency.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Guide {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public Guide(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
