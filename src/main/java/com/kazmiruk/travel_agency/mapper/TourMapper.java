package com.kazmiruk.travel_agency.mapper;

import com.kazmiruk.travel_agency.model.dto.TourDto;
import com.kazmiruk.travel_agency.model.entity.Tour;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = { CountryMapper.class, GuideMapper.class})
public interface TourMapper {

    TourDto toDto(Tour tour);

    @Mapping(target = "bookedTours", ignore = true)
    Tour toEntity(TourDto tourDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bookedTours", ignore = true)
    @Mapping(target = "departure", expression = "java(countryMapper.toEntity(tourDto.getDeparture()))")
    @Mapping(target = "destination", expression = "java(countryMapper.toEntity(tourDto.getDestination()))")
    @Mapping(target = "guide", expression = "java(guideMapper.toEntity(tourDto.getGuide()))")
    void updateEntity(@MappingTarget Tour tour, TourDto tourDto);
}
