package com.kazmiruk.travel_agency.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @NotNull
    private Country departure;

    @ManyToOne
    @NotNull
    private Country destination;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate departureAt;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate returnAt;

    @NotNull
    private Double initialPrice;

    @ManyToOne
    @NotNull
    private Guide guide;

    @OneToMany(mappedBy = "tour")
    private Set<TourSellingPrice> tourSellingPrices;
}
