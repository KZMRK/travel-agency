package com.kazmiruk.travel_agency.service;

import com.kazmiruk.travel_agency.mapper.ClientMapper;
import com.kazmiruk.travel_agency.mapper.TourMapper;
import com.kazmiruk.travel_agency.model.dto.ClientDto;
import com.kazmiruk.travel_agency.model.dto.TourDto;
import com.kazmiruk.travel_agency.model.entity.Client;
import com.kazmiruk.travel_agency.model.entity.Tour;
import com.kazmiruk.travel_agency.model.exception.AlreadyExistException;
import com.kazmiruk.travel_agency.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.kazmiruk.travel_agency.type.ErrorMessageType.CLIENT_WITH_PASSPORT_ALREADY_EXIST;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    private final ClientMapper clientMapper;

    private final TourMapper tourMapper;

    @Transactional
    public ClientDto createClient(ClientDto clientRequest) {
        checkIfClientAlreadyExistsByPassportNumber(clientRequest.getPassportNumber());
        Client client = clientMapper.toEntity(clientRequest);
        client = clientRepository.save(client);
        return clientMapper.toDto(client);
    }

    private void checkIfClientAlreadyExistsByPassportNumber(String passportNumber) {
        if (clientRepository.existsByPassportNumber(passportNumber)) {
            throw new AlreadyExistException(
                    CLIENT_WITH_PASSPORT_ALREADY_EXIST,
                    passportNumber
            );
        }
    }

    @Transactional(readOnly = true)
    public Set<ClientDto> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        return clients.stream()
                .map(clientMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Transactional
    public ClientDto updateClient(Long clientId, ClientDto clientRequest) {
        Client client = clientRepository.getOneById(clientId);
        if (!client.getPassportNumber().equals(clientRequest.getPassportNumber())) {
            checkIfClientAlreadyExistsByPassportNumber(clientRequest.getPassportNumber());
        }
        clientMapper.updateEntity(client, clientRequest);
        return clientMapper.toDto(client);
    }

    @Transactional
    public void deleteClient(Long clientId) {
        Client client = clientRepository.getOneById(clientId);
        clientRepository.delete(client);
    }

    @Transactional(readOnly = true)
    public Set<TourDto> getClientTours(Long clientId) {
        Set<Tour> clientTours = clientRepository.findClientToursByClientId(clientId);
        return clientTours.stream().map(tourMapper::toDto).collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    public Set<ClientDto> getClientsWithoutToursInYear(int year) {
        Set<Client> clients = clientRepository.findClientsWithoutTourInYear(year);
        return clients.stream().map(clientMapper::toDto).collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    public ClientDto getClientWithHighestDiscount() {
        Optional<Client> clientOpt = clientRepository.findClientWithHighestDiscount();
        return clientMapper.toDto(clientOpt.orElse(null));
    }

    @Transactional(readOnly = true)
    public ClientDto getClientGeneratedHighestRevenue() {
        Optional<Client> client = clientRepository.findClientGeneratedHighestRevenue();
        return clientMapper.toDto(client.orElse(null));
    }
}
