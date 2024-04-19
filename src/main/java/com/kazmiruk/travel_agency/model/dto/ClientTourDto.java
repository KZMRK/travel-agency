package com.kazmiruk.travel_agency.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class ClientTourDto {

    private TourDto tour;

    private Double sellingPrice;
}
