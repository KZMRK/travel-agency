package com.kazmiruk.travel_agency.service;

import com.kazmiruk.travel_agency.dto.ClientRequest;
import com.kazmiruk.travel_agency.dto.ClientResponse;
import com.kazmiruk.travel_agency.mapper.ClientMapper;
import com.kazmiruk.travel_agency.model.Client;
import com.kazmiruk.travel_agency.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    private final ClientMapper clientMapper;

    public Iterable<ClientResponse> getClients() {
        Iterable<Client> clients = clientRepository.findAll();
        return clientMapper.toResponse(clients);
    }

    public ClientResponse addClient(ClientRequest clientRequest) {
        Client client = clientMapper.toEntity(clientRequest);
        Client savedClient = clientRepository.save(client);
        return clientMapper.toResponse(savedClient);
    }

    public void deleteClient(Long clientId) {
        clientRepository.deleteById(clientId);
    }

    public ClientResponse updateClient(Long clientId, ClientRequest clientRequest) {
        Client updatedClient = clientRepository.findById(clientId)
                .map(client -> {
                    client.setFirstName(clientRequest.getFirstName());
                    client.setLastName(clientRequest.getLastName());
                    client.setPassportNumber(clientRequest.getPassportNumber());
                    return clientRepository.save(client);
                }).orElseGet(() -> {
                    Client newClient = clientMapper.toEntity(clientRequest);
                    newClient.setId(clientId);
                    return clientRepository.save(newClient);
                });
        return clientMapper.toResponse(updatedClient);
    }
}
