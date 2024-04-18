package com.kazmiruk.travel_agency.mapper;

import com.kazmiruk.travel_agency.model.dto.ClientDto;
import com.kazmiruk.travel_agency.model.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientDto toDto(Client client);

    @Mapping(target = "bookedTours", ignore = true)
    Client toEntity(ClientDto clientDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bookedTours", ignore = true)
    void updateEntity(@MappingTarget Client client, ClientDto clientDto);
}
