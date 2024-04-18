package com.kazmiruk.travel_agency.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientTourDto {

    private TourDto tour;

    private Double sellingPrice;
}
