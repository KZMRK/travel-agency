package com.kazmiruk.travel_agency.mapper;

import com.kazmiruk.travel_agency.dto.TourSellingPriceResponse;
import com.kazmiruk.travel_agency.model.BookedTour;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { TourMapper.class, ClientMapper.class })
public interface TourSellingPriceMapper {

    TourSellingPriceResponse toResponse(BookedTour bookedTour);

}
