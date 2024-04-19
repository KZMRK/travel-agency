package com.kazmiruk.travel_agency.controller;

import com.kazmiruk.travel_agency.model.dto.ErrorDto;
import com.kazmiruk.travel_agency.model.dto.GuideDto;
import com.kazmiruk.travel_agency.service.GuideService;
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
@RequestMapping("/api/guides")
@RequiredArgsConstructor
@Tag(name = "Guide", description = "Guide who manages the tour")
public class GuideController {

    private final GuideService guideService;

    @Operation(
            summary = "Add guide",
            responses = {
                    @ApiResponse(
                            description = "Guide created",
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
    public ResponseEntity<GuideDto> createGuide(@RequestBody @Valid GuideDto guideRequest) {
        GuideDto guideResponse = guideService.createGuide(guideRequest);
        return new ResponseEntity<>(guideResponse, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get all guides",
            responses = @ApiResponse(
                    description = "OK",
                    responseCode = "200",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GuideDto.class))
                    )
            )
    )
    @GetMapping
    public ResponseEntity<Set<GuideDto>> getAllGuides() {
        Set<GuideDto> guideResponses = guideService.getAllGuides();
        return ResponseEntity.ok(guideResponses);
    }

    @Operation(
            summary = "Update guide data",
            description = "Update guide data if id exists otherwise create a new guide with the specified data",
            responses = {
                    @ApiResponse(
                            description = "Guide updated",
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
                            description = "Guide not found",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDto.class)
                            )
                    )
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<GuideDto> updateGuide(
            @Parameter(name = "id", description = "Guide id whose data you want to change")
            @PathVariable("id") Long guideId,
            @RequestBody @Valid GuideDto guideRequest
    ) {
        GuideDto guideResponse = guideService.updateGuide(guideId, guideRequest);
        return ResponseEntity.ok(guideResponse);
    }

    @Operation(
            summary = "Delete the guide",
            responses = {
                    @ApiResponse(
                            description = "Guide deleted",
                            responseCode = "204"
                    ),
                    @ApiResponse(
                            description = "Guide not found",
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
    public void deleteGuide(
            @Parameter(name = "id", description = "id of the client you want to delete")
            @PathVariable("id") Long guideId
    ) {
        guideService.deleteGuide(guideId);
    }

    @Operation(
            summary = "Get tour guide who generated the highest revenue"
    )
    @GetMapping("/highest-revenue")
    public ResponseEntity<GuideDto> getGuideGeneratedHighestRevenue() {
        GuideDto guideResponse = guideService.getGuideGeneratedHighestRevenue();
        return ResponseEntity.ok(guideResponse);
    }

}
