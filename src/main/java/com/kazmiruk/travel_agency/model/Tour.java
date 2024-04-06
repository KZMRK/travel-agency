package com.kazmiruk.travel_agency.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Tour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Country departure;

    @ManyToOne
    private Country destination;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate departureAt;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate returnAt;

    private Double initialPrice;

    @ManyToOne
    private Guide guide;
}
