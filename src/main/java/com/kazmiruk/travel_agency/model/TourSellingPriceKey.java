package com.kazmiruk.travel_agency.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourSellingPriceKey implements Serializable {

    @Column(name = "tour_id")
    private Long tourId;

    @Column(name = "client_id")
    private Long clientId;

}
