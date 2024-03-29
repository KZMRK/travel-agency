package com.kazmiruk.travel_agency.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Tour {

    @Id
    @GeneratedValue
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

    @ManyToMany(mappedBy = "tours", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Client> clients;
}
