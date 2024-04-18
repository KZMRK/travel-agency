package com.kazmiruk.travel_agency.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "tours")
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

    @Min(value = 1, message = "{price.min}")
    private BigDecimal initialPrice;

    @ManyToOne
    private Guide guide;

    @OneToMany(mappedBy = "tour")
    private Set<ClientTour> bookedTours;
}
