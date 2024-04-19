package com.kazmiruk.travel_agency.controller;

import com.kazmiruk.travel_agency.model.dto.BookTourDto;
import com.kazmiruk.travel_agency.model.dto.ErrorDto;
import com.kazmiruk.travel_agency.model.dto.TourAggregateDto;
import com.kazmiruk.travel_agency.model.dto.TourDto;
import com.kazmiruk.travel_agency.model.dto.ClientTourDto;
import com.kazmiruk.travel_agency.service.TourService;
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
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/tours")
@RequiredArgsConstructor
@Tag(name = "Tour", description = "A guided tour that can be booked by the client")
public class TourController {

    private final TourService tourService;

    @Operation(
            summary = "Add tour",
            responses = {
                    @ApiResponse(
                            description = "Tour created",
                            responseCode = "201"
                    ),
                    @ApiResponse(
                            description = "Validation error in request body data",
                            responseCode = "400",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDto.class)
                            )
                    )
            }
    )
    @PostMapping
    public ResponseEntity<TourDto> createTour(
            @RequestBody @Valid TourDto tourRequest
    ) {
        TourDto tourResponse = tourService.createTour(tourRequest);
        return ResponseEntity.ok(tourResponse);
    }

    @Operation(
            summary = "Get all tours",
            responses = @ApiResponse(
                    description = "OK",
                    responseCode = "200",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TourDto.class))
                    )
            )
    )
    @GetMapping
    public ResponseEntity<Set<TourDto>> getAllTours() {
        Set<TourDto> tourResponses = tourService.getAllTours();
        return ResponseEntity.ok(tourResponses);
    }

    @Operation(
            summary = "Update tour data",
            description = "Update tour data if id exists otherwise create a new tour with the specified data",
            responses = {
                    @ApiResponse(
                            description = "Tour updated",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Validation error in request body data",
                            responseCode = "400",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDto.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Tour not found",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDto.class)
                            )
                    )
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<TourDto> updateTour(
            @Parameter(name = "id", description = "Tour id whose data you want to change")
            @PathVariable("id") Long tourId,
            @RequestBody @Valid TourDto tourRequest
    ) {
        TourDto tourResponse = tourService.updateTour(tourId, tourRequest);
        return ResponseEntity.ok(tourResponse);
    }

    @Operation(
            summary = "Delete the tour",
            responses = {
                    @ApiResponse(
                            description = "Tour deleted",
                            responseCode = "204"
                    ),
                    @ApiResponse(
                            description = "Tour not found",
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
    public void deleteTour(
            @Parameter(name = "id", description = "id of the tour you want to delete")
            @PathVariable("id") Long tourId) {
        tourService.deleteTour(tourId);
    }

    @Operation(
            summary =  "Booking tours for clients",
            description = "Booking tours for clients. Customers can negotiate the price of a tour, so the selling " +
                    "price may be different from the initial price, but the highest discount cannot be " +
                    "greater than 20% (by default) of the initial price",
            responses = {
                    @ApiResponse(
                            description = "Tour booked",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Tour or client not found",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDto.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Too much discount or invalid price",
                            responseCode = "400",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDto.class)
                            )
                    )
            }
    )
    @PostMapping("/{tourId}/clients/{clientId}")
    public ResponseEntity<ClientTourDto> bookTour(
            @Parameter(name = "tourId", description = "id of tour that the client wants to book")
            @PathVariable Long tourId,
            @Parameter(name = "clientId", description = "id of client who wants to book a tour")
            @PathVariable Long clientId,
            @RequestBody @Valid BookTourDto bookTourRequest
    ) {
        ClientTourDto clientTourResponse = tourService.bookTour(tourId, clientId, bookTourRequest);
        return ResponseEntity.ok(clientTourResponse);
    }

    @Operation(
            summary = "Cancel the client's booking",
            responses = {
                    @ApiResponse(
                            description = "204",
                            responseCode = "Booking canceled"
                    ),
                    @ApiResponse(
                            description = "404",
                            responseCode = "Booking not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDto.class)
                            )
                    )
            }
    )
    @DeleteMapping("/{tourId}/clients/{clientId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelBooking(
            @Parameter(name = "tourId", description = "id of booked tour")
            @PathVariable("tourId") Long tourId,
            @Parameter(name = "clientId", description = "id of client")
            @PathVariable("clientId") Long clientId
    ) {
        tourService.cancelBooking(tourId, clientId);
    }

    @Operation(
            summary = "The average price and total amount for which a tour was sold",
            responses = {
                    @ApiResponse(
                            description = "OK",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Tour not found",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDto.class)
                            )
                    )
            }
    )
    @GetMapping("/{id}/aggregate")
    public ResponseEntity<TourAggregateDto> getTourSumAndAvgSellingPrice(
            @Parameter(name = "id", description = "id of the tour")
            @PathVariable("id") Long tourId
    ) {
        TourAggregateDto tourAggregateDto = tourService.getTourSumAndAvgSellingPrice(tourId);
        return ResponseEntity.ok(tourAggregateDto);
    }

    @Operation(
            summary = "The most popular trip with the lowest selling price"
    )
    @GetMapping("/most-popular-with-lowest-selling-price")
    public ResponseEntity<TourDto> getMostPopularTourWithTheLowestSellingPrice() {
        TourDto tourResponse = tourService.getMostPopularTourWithTheLowestSellingPrice();
        return ResponseEntity.ok(tourResponse);
    }

}
