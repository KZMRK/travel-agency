package com.kazmiruk.travel_agency.web;

import com.kazmiruk.travel_agency.dto.ClientRequest;
import com.kazmiruk.travel_agency.dto.ClientResponse;
import com.kazmiruk.travel_agency.dto.TourResponse;
import com.kazmiruk.travel_agency.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping
    public ResponseEntity<Iterable<ClientResponse>> getClients() {
        Iterable<ClientResponse> clients = clientService.getClients();
        return ResponseEntity.ok(clients);
    }

    @PostMapping
    public ResponseEntity<ClientResponse> addClient(@RequestBody ClientRequest clientRequest) {
        ClientResponse clientResponse = clientService.addClient(clientRequest);
        return new ResponseEntity<>(clientResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> updateClient(
            @PathVariable("id") Long clientId,
            @RequestBody ClientRequest clientRequest
    ) {
        ClientResponse clientResponse = clientService.updateClient(clientId, clientRequest);
        return ResponseEntity.ok(clientResponse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClient(@PathVariable("id") Long clientId) {
        clientService.deleteClient(clientId);
    }

    @GetMapping("/{id}/tours")
    public ResponseEntity<Iterable<TourResponse>> getClientTours(
            @PathVariable("id") Long clientId
    ) {
        Iterable<TourResponse> tourResponses = clientService.getClientTours(clientId);
        return ResponseEntity.ok(tourResponses);
    }
}
