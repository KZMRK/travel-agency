package com.kazmiruk.travel_agency.controller;

import com.kazmiruk.travel_agency.model.dto.ClientDto;
import com.kazmiruk.travel_agency.model.dto.ErrorDto;
import com.kazmiruk.travel_agency.model.dto.TourDto;
import com.kazmiruk.travel_agency.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
@Tag(name = "Client", description = "A client who wants to book a tour")
public class ClientController {

    private final ClientService clientService;

    @Operation(
            summary = "Add client",
            responses = {
                    @ApiResponse(
                            description = "Client created",
                            responseCode = "201"
                    ),
                    @ApiResponse(
                            description = "Validation error in request body data or client with passed" +
                                    " passport number already exist",
                            responseCode = "400",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDto.class)
                            )
                    )
            }
    )
    @PostMapping
    public ResponseEntity<ClientDto> createClient(@RequestBody @Valid ClientDto clientRequest) {
        ClientDto clientResponse = clientService.createClient(clientRequest);
        return new ResponseEntity<>(clientResponse, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get all clients",
            responses = @ApiResponse(
                    description = "OK",
                    responseCode = "200",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ClientDto.class))
                    )
            )
    )
    @GetMapping
    public ResponseEntity<Set<ClientDto>> getAllClients() {
        Set<ClientDto> clientResponses = clientService.getAllClients();
        return ResponseEntity.ok(clientResponses);
    }

    @Operation(
            summary = "Update client data",
            description = "Update client data",
            responses = {
                    @ApiResponse(
                            description = "Client updated",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Validation error in request body data or client with passed" +
                                    " passport number already exist",
                            responseCode = "400",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDto.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Client not found",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDto.class)
                            )
                    )
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<ClientDto> updateClient(
            @Parameter(name = "id", description = "Client id whose data you want to change" , example = "34")
            @PathVariable("id") Long clientId,
            @RequestBody @Valid ClientDto clientRequest
    ) {
        ClientDto clientResponse = clientService.updateClient(clientId, clientRequest);
        return ResponseEntity.ok(clientResponse);
    }

    @Operation(
            summary = "Delete the client",
            responses = {
                    @ApiResponse(
                            description = "Client deleted",
                            responseCode = "204"
                    ),
                    @ApiResponse(
                            description = "Client not found",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDto.class)
                            )
                    )
            }
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClient(
            @Parameter(name = "id", description = "id of the client you want to delete")
            @PathVariable("id") Long clientId
    ) {
        clientService.deleteClient(clientId);
    }

    @Operation(
            summary = "Get tours booked by the client",
            responses = {
                    @ApiResponse(
                            description = "OK",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = TourDto.class))
                            )
                    ),
                    @ApiResponse(
                            description = "Client not found",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDto.class)
                            )
                    )
            }
    )
    @GetMapping("/{id}/tours")
    public ResponseEntity<Set<TourDto>> getClientTours(
            @Parameter(name = "id", description = "id of the client whose tours you want to receive")
            @PathVariable("id") Long clientId
    ) {
        Set<TourDto> tourResponses = clientService.getClientTours(clientId);
        return ResponseEntity.ok(tourResponses);
    }

    @Operation(
            summary = "Customers which did not order a tour in a given year",
            responses = @ApiResponse(
                    description = "OK",
                    responseCode = "200",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ClientDto.class))
                    )
            )
    )
    @GetMapping("/without-tours")
    public ResponseEntity<Set<ClientDto>> getClientsWithoutToursInYear(
            @RequestParam("year") Integer year
    ) {
        Set<ClientDto> clientResponses = clientService.getClientsWithoutToursInYear(year);
        return ResponseEntity.ok(clientResponses);
    }

    @Operation(
            summary = "Customer who got the highest discount",
             responses = {
                     @ApiResponse(
                             description = "OK",
                             responseCode = "200"
                     )
                     ,
                     @ApiResponse(
                             description = "Client not found",
                             responseCode = "404",
                             content = @Content(
                                     mediaType = "application/json",
                                     schema = @Schema(implementation = ErrorDto.class)
                         )
                    )
             }
    )
    @GetMapping("/with-highest-discount")
    public ResponseEntity<ClientDto> getClientWithHighestDiscount() {
        ClientDto clientResponse = clientService.getClientWithHighestDiscount();
        return ResponseEntity.ok(clientResponse);
    }

    @Operation(
            summary = "Customer generated the highest revenue overall for the travel agency",
            responses = {
                    @ApiResponse(
                            description = "OK",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Client not found",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDto.class)
                        )
                    )
            }
    )
    @GetMapping("/highest-revenue")
    public ResponseEntity<ClientDto> getClientGeneratedHighestRevenue() {
        ClientDto clientResponse = clientService.getClientGeneratedHighestRevenue();
        return ResponseEntity.ok(clientResponse);
    }
}
