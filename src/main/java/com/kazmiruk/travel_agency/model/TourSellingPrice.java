package com.kazmiruk.travel_agency.model;

import com.kazmiruk.travel_agency.model.key.TourSellingPriceKey;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tour_selling_price")
public class TourSellingPrice {

    @EmbeddedId
    private TourSellingPriceKey id;

    @ManyToOne
    @MapsId("tourId")
    @JoinColumn(name = "tour_id")
    private Tour tour;

    @ManyToOne
    @MapsId("clientId")
    @JoinColumn(name = "client_id")
    private Client client;

    private Double sellingPrice;
}
