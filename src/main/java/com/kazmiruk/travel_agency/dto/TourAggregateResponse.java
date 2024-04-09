package com.kazmiruk.travel_agency.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TourAggregateResponse {

    private Double tourSumSellingPrice;

    private Double tourAvgSellingPrice;
}
