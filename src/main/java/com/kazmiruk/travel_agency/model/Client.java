package com.kazmiruk.travel_agency.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Client {

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

    @Pattern(regexp="\\d{9}", message = "{passport-number.pattern}")
    private String passportNumber;

    @OneToMany(mappedBy = "client")
    private List<BookedTour> bookedTours;

    public Client(String firstName, String lastName, String passportNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.passportNumber = passportNumber;
    }

}
