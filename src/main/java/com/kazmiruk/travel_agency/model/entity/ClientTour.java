package com.kazmiruk.travel_agency.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "client_tours")
@NoArgsConstructor
public class ClientTour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tour_id")
    private Tour tour;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    private BigDecimal sellingPrice;

    public ClientTour(Tour tour, Client client, BigDecimal sellingPrice) {
        this.tour = tour;
        this.client = client;
        this.sellingPrice = sellingPrice;
    }
}
