package com.kazmiruk.travel_agency.web;

import com.kazmiruk.travel_agency.dto.BookTourRequest;
import com.kazmiruk.travel_agency.dto.TourAggregateResponse;
import com.kazmiruk.travel_agency.dto.TourRequest;
import com.kazmiruk.travel_agency.dto.TourResponse;
import com.kazmiruk.travel_agency.dto.TourSellingPriceResponse;
import com.kazmiruk.travel_agency.service.TourService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tours")
@RequiredArgsConstructor
public class TourController {

    private final TourService tourService;

    @GetMapping
    public ResponseEntity<Iterable<TourResponse>> getTours() {
        Iterable<TourResponse> tourResponses = tourService.getTours();
        return ResponseEntity.ok(tourResponses);
    }

    @PostMapping
    public ResponseEntity<TourResponse> addTour(
            @RequestBody @Valid TourRequest tourRequest
    ) {
        TourResponse tourResponse = tourService.addTour(tourRequest);
        return ResponseEntity.ok(tourResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TourResponse> editTour(
            @PathVariable("id") Long tourId,
            @RequestBody @Valid TourRequest tourRequest
    ) {
        TourResponse tourResponse = tourService.editTour(tourId, tourRequest);
        return ResponseEntity.ok(tourResponse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTour(@PathVariable("id") Long tourId) {
        tourService.deleteTour(tourId);
    }

    @PostMapping("/{tourId}/clients/{clientId}")
    public ResponseEntity<TourSellingPriceResponse> bookTour(
            @PathVariable Long tourId,
            @PathVariable Long clientId,
            @RequestBody @Valid BookTourRequest bookTourRequest
    ) {
        TourSellingPriceResponse tourResponse = tourService.bookTour(tourId, clientId, bookTourRequest);
        return ResponseEntity.ok(tourResponse);
    }

    @GetMapping("/{id}/aggregate")
    public ResponseEntity<TourAggregateResponse> getTourSumAndAvgSellingPrice(
            @PathVariable("id") Long tourId
    ) {
        TourAggregateResponse tourAggregateResponse = tourService.getTourSumAndAvgSellingPrice(tourId);
        return ResponseEntity.ok(tourAggregateResponse);
    }

    @GetMapping("/most-popular-with-lowest-selling-price")
    public ResponseEntity<TourResponse> getMostPopularTourWithTheLowestSellingPrice() {
        TourResponse tourResponse = tourService.getMostPopularTourWithTheLowestSellingPrice();
        return ResponseEntity.ok(tourResponse);
    }

}
