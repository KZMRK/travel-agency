package com.kazmiruk.travel_agency.service;

import com.kazmiruk.travel_agency.dto.ClientRequest;
import com.kazmiruk.travel_agency.dto.ClientResponse;
import com.kazmiruk.travel_agency.dto.TourResponse;
import com.kazmiruk.travel_agency.mapper.ClientMapper;
import com.kazmiruk.travel_agency.mapper.TourMapper;
import com.kazmiruk.travel_agency.model.Client;
import com.kazmiruk.travel_agency.model.BookedTour;
import com.kazmiruk.travel_agency.repository.ClientRepository;
import com.kazmiruk.travel_agency.uti.error.ClientNotFoundException;
import com.kazmiruk.travel_agency.uti.error.PassportNumberAlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    private final ClientMapper clientMapper;

    private final TourMapper tourMapper;

    public Iterable<ClientResponse> getClients() {
        Iterable<Client> clients = clientRepository.findAll();
        return clientMapper.toResponse(clients);
    }

    public ClientResponse addClient(ClientRequest clientRequest) {
        Client client = clientMapper.toEntity(clientRequest);
        try {
            Client savedClient = clientRepository.save(client);
            return clientMapper.toResponse(savedClient);
        } catch (DataIntegrityViolationException e) {
            throw new PassportNumberAlreadyExistException(
                    "Client with passport number '" + clientRequest.getPassportNumber() + "' already exist"
            );
        }
    }

    public void deleteClient(Long clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow(() ->
                new ClientNotFoundException("Client with id " + clientId + " not found")
        );
        clientRepository.delete(client);
    }

    public ClientResponse updateClient(Long clientId, ClientRequest clientRequest) {
        Client updatedClient = clientRepository.findById(clientId)
                .map(client -> {
                    client.setFirstName(clientRequest.getFirstName());
                    client.setLastName(clientRequest.getLastName());
                    client.setPassportNumber(clientRequest.getPassportNumber());
                    return clientRepository.save(client);
                }).orElseThrow(() -> new ClientNotFoundException("Client with id " + clientId + " not found"));

        return clientMapper.toResponse(updatedClient);
    }

    public Iterable<TourResponse> getClientTours(Long clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow(() ->
                new ClientNotFoundException("Client with id " + clientId + " not found")
        );
        return tourMapper.toResponse(client.getBookedTours().stream().map(BookedTour::getTour).toList());
    }

    public Iterable<ClientResponse> getClientsWithoutToursInYear(int year) {
        Iterable<Client> clients = clientRepository.findClientsWithoutTourInYear(year);
        return clientMapper.toResponse(clients);
    }

    public ClientResponse getClientWithHighestDiscount() {
        Client client = clientRepository.findClientWithHighestDiscount().orElseThrow(() ->
                new ClientNotFoundException("Client not found")
        );
        return clientMapper.toResponse(client);
    }

    public ClientResponse getClientGeneratedHighestRevenue() {
        Client client = clientRepository.findClientGeneratedHighestRevenue().orElseThrow(() ->
                new ClientNotFoundException("Client not found")
        );
        return clientMapper.toResponse(client);
    }
}
