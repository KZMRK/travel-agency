package com.kazmiruk.travel_agency.web;

import com.kazmiruk.travel_agency.dto.BookTourRequest;
import com.kazmiruk.travel_agency.dto.ErrorDto;
import com.kazmiruk.travel_agency.dto.TourAggregateResponse;
import com.kazmiruk.travel_agency.dto.TourRequest;
import com.kazmiruk.travel_agency.dto.TourResponse;
import com.kazmiruk.travel_agency.dto.TourSellingPriceResponse;
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

@RestController
@RequestMapping("/api/tours")
@RequiredArgsConstructor
@Tag(name = "Tour", description = "A guided tour that can be booked by the client")
public class TourController {

    private final TourService tourService;

    @Operation(
            summary = "Get all tours",
            responses = @ApiResponse(
                    description = "OK",
                    responseCode = "200",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TourResponse.class))
                    )
            )
    )
    @GetMapping
    public ResponseEntity<Iterable<TourResponse>> getTours() {
        Iterable<TourResponse> tourResponses = tourService.getTours();
        return ResponseEntity.ok(tourResponses);
    }

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
    public ResponseEntity<TourResponse> addTour(
            @RequestBody @Valid TourRequest tourRequest
    ) {
        TourResponse tourResponse = tourService.addTour(tourRequest);
        return ResponseEntity.ok(tourResponse);
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
    public ResponseEntity<TourResponse> updateTour(
            @Parameter(name = "id", description = "Tour id whose data you want to change")
            @PathVariable("id") Long tourId,
            @RequestBody @Valid TourRequest tourRequest
    ) {
        TourResponse tourResponse = tourService.updateTour(tourId, tourRequest);
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
                    ),
                    @ApiResponse(
                            description = "Tour cannot be deleted",
                            responseCode = "400",
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
    public ResponseEntity<TourSellingPriceResponse> bookTour(
            @Parameter(name = "tourId", description = "id of tour that the client wants to book")
            @PathVariable Long tourId,
            @Parameter(name = "clientId", description = "id of client who wants to book a tour")
            @PathVariable Long clientId,
            @RequestBody @Valid BookTourRequest bookTourRequest
    ) {
        TourSellingPriceResponse tourResponse = tourService.bookTour(tourId, clientId, bookTourRequest);
        return ResponseEntity.ok(tourResponse);
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
    public ResponseEntity<TourAggregateResponse> getTourSumAndAvgSellingPrice(
            @Parameter(name = "id", description = "id of the tour")
            @PathVariable("id") Long tourId
    ) {
        TourAggregateResponse tourAggregateResponse = tourService.getTourSumAndAvgSellingPrice(tourId);
        return ResponseEntity.ok(tourAggregateResponse);
    }

    @Operation(
            summary = "The most popular trip with the lowest selling price",
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
    @GetMapping("/most-popular-with-lowest-selling-price")
    public ResponseEntity<TourResponse> getMostPopularTourWithTheLowestSellingPrice() {
        TourResponse tourResponse = tourService.getMostPopularTourWithTheLowestSellingPrice();
        return ResponseEntity.ok(tourResponse);
    }

}
