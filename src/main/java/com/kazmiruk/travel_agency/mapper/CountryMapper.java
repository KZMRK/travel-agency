package com.kazmiruk.travel_agency.mapper;

import com.kazmiruk.travel_agency.dto.CountryRequest;
import com.kazmiruk.travel_agency.dto.CountryResponse;
import com.kazmiruk.travel_agency.model.Country;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CountryMapper {

    @Mapping(target = "id", ignore = true)
    Country toEntity(CountryRequest countryRequest);

    CountryResponse toResponse(Country country);

    Iterable<CountryResponse> toResponse(Iterable<Country> countries);
}
