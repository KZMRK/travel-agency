package com.kazmiruk.travel_agency.mapper;

import com.kazmiruk.travel_agency.model.dto.CountryDto;
import com.kazmiruk.travel_agency.model.entity.Country;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CountryMapper {

    Country toEntity(CountryDto countryDto);

    CountryDto toDto(Country country);

    @Mapping(target = "id", ignore = true)
    void updateEntity(@MappingTarget Country country, CountryDto countryDto);
}
