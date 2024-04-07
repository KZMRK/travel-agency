package com.kazmiruk.travel_agency.dto;

import lombok.Data;

@Data
public class TourSellingPriceResponse {

    private TourResponse tour;

    private ClientResponse client;

    private Double sellingPrice;
}
