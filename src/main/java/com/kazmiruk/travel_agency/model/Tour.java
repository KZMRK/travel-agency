package com.kazmiruk.travel_agency.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

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

    @OneToMany(mappedBy = "tour")
    private Set<TourSellingPrice> tourSellingPrices;
}
