package com.kazmiruk.travel_agency.mapper;

import com.kazmiruk.travel_agency.dto.TourRequest;
import com.kazmiruk.travel_agency.dto.TourResponse;
import com.kazmiruk.travel_agency.model.Tour;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { CountryMapper.class, GuideMapper.class })
public interface TourMapper {

    TourResponse toResponse(Tour tour);

    Iterable<TourResponse> toResponse(Iterable<Tour> tours);

    @Mapping(target = "bookedTours", ignore = true)
    @Mapping(target = "id", ignore = true)
    Tour toEntity(TourRequest tourRequest);

}
