package com.kazmiruk.travel_agency.mapper;

import com.kazmiruk.travel_agency.model.dto.ClientTourDto;
import com.kazmiruk.travel_agency.model.entity.ClientTour;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { TourMapper.class, ClientMapper.class })
public interface ClientTourMapper {

    ClientTourDto toDto(ClientTour bookedTour);

}
