package com.kazmiruk.travel_agency.mapper;

import com.kazmiruk.travel_agency.dto.ClientRequest;
import com.kazmiruk.travel_agency.dto.ClientResponse;
import com.kazmiruk.travel_agency.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientResponse toResponse(Client client);

    Iterable<ClientResponse> toResponse(Iterable<Client> clients);

    @Mapping(target = "tours", ignore = true)
    @Mapping(target = "id", ignore = true)
    Client toEntity(ClientRequest clientRequest);
}
