package com.kazmiruk.travel_agency.controller;

import com.kazmiruk.travel_agency.model.dto.CountryDto;
import com.kazmiruk.travel_agency.model.dto.ErrorDto;
import com.kazmiruk.travel_agency.service.CountryService;
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
@RequestMapping("/api/countries")
@RequiredArgsConstructor
@Tag(name = "Country", description = "Interaction with the countries to which the travel company organizes tours")
public class CountryController {

    private final CountryService countryService;

    @Operation(
            summary = "Add country",
            responses = {
                    @ApiResponse(
                            description = "Country created",
                            responseCode = "201"
                    ),
                    @ApiResponse(
                            description = "Validation error in request body data or country with passed" +
                                    " name already exist",
                            responseCode = "400",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDto.class)
                            )
                    )
            }
    )
    @PostMapping
    public ResponseEntity<CountryDto> createCountry(@RequestBody @Valid CountryDto countryRequest) {
        CountryDto countryResponse = countryService.createCountry(countryRequest);
        return new ResponseEntity<>(countryResponse, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get all the countries",
            responses = @ApiResponse(
                    description = "OK",
                    responseCode = "200",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CountryDto.class))
                    )
            )
    )
    @GetMapping
    public ResponseEntity<Set<CountryDto>> getAllCountries() {
        Set<CountryDto> countryResponses = countryService.getAllCountries();
        return ResponseEntity.ok(countryResponses);
    }

    @Operation(
            summary = "Update the country name",
            description = "Update the country name",
            responses = {
                    @ApiResponse(
                            description = "Country updated",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Validation error in request body data or country with passed" +
                                    " name already exist",
                            responseCode = "400",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDto.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Country not found",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDto.class)
                            )
                    )
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<CountryDto> updateCountry(
            @Parameter(name = "id", description = "Country id whose name you want to change")
            @PathVariable("id") Integer countryId,
            @RequestBody @Valid CountryDto countryRequest
    ) {
        CountryDto countryResponse = countryService.updateCountry(countryId, countryRequest);
        return ResponseEntity.ok(countryResponse);
    }

    @Operation(
            summary = "Delete the country",
            responses = {
                    @ApiResponse(
                            description = "Country deleted",
                            responseCode = "204"
                    ),
                    @ApiResponse(
                            description = "Country cannot be deleted",
                            responseCode = "400",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDto.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Country not found",
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
    public void deleteCountry(
            @Parameter(name = "id", description = "id of the country you want to delete")
            @PathVariable("id") Integer countryId
    ) {
        countryService.deleteCountry(countryId);
    }

    @Operation(
            summary = "Get most popular destination in a given year",
            responses = {
                    @ApiResponse(
                            description = "OK",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "There are no tours in given year",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDto.class)
                            )
                    )
            }
    )
    @GetMapping("/top")
    public ResponseEntity<CountryDto> getMostPopularDestinationInYear(
            @RequestParam Integer year
    ) {
        CountryDto countryResponse = countryService.getMostPopularDestinationInYear(year);
        return ResponseEntity.ok(countryResponse);
    }
}
