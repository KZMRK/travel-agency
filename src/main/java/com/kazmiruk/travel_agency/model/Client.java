package com.kazmiruk.travel_agency.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, max = 50)
    private String firstName;

    @Size(min = 2, max = 50)
    private String lastName;

    @Pattern(regexp="\\d{9}", message = "The passport number must consist of 9 digits")
    private String passportNumber;

    @OneToMany(mappedBy = "client")
    private List<TourSellingPrice> tourSellingPrices;

}
