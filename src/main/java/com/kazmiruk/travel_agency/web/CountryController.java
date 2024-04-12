package com.kazmiruk.travel_agency.web;

import com.kazmiruk.travel_agency.dto.CountryRequest;
import com.kazmiruk.travel_agency.dto.CountryResponse;
import com.kazmiruk.travel_agency.service.CountryService;
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

@RestController
@RequestMapping("/api/countries")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

    @GetMapping
    public ResponseEntity<Iterable<CountryResponse>> getCountries() {
        Iterable<CountryResponse> countryResponses = countryService.getCountries();
        return ResponseEntity.ok(countryResponses);
    }

    @PostMapping
    public ResponseEntity<CountryResponse> addCountry(@RequestBody @Valid CountryRequest countryRequest) {
        CountryResponse countryResponse = countryService.addCountry(countryRequest);
        return new ResponseEntity<>(countryResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CountryResponse> updateCountry(
            @PathVariable("id") Integer countryId,
            @RequestBody @Valid CountryRequest countryRequest
    ) {
        CountryResponse countryResponse = countryService.updateCountry(countryId, countryRequest);
        return ResponseEntity.ok(countryResponse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCountry(@PathVariable("id") Integer countryId) {
        countryService.deleteCountry(countryId);
    }

    @GetMapping("/top")
    public ResponseEntity<CountryResponse> getMostPopularCountry(
            @RequestParam Integer year
    ) {
        CountryResponse countryResponse = countryService.getMostPopularDestination(year);
        return ResponseEntity.ok(countryResponse);
    }
}
