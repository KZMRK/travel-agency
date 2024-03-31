package com.kazmiruk.travel_agency.web;

import com.kazmiruk.travel_agency.dto.GuideRequest;
import com.kazmiruk.travel_agency.dto.GuideResponse;
import com.kazmiruk.travel_agency.service.GuideService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/guides")
@RequiredArgsConstructor
public class GuideController {

    private final GuideService guideService;

    @GetMapping
    public ResponseEntity<Iterable<GuideResponse>> getGuides() {
        Iterable<GuideResponse> guideResponses = guideService.getGuides();
        return ResponseEntity.ok(guideResponses);
    }

    @PostMapping
    public ResponseEntity<GuideResponse> addGuide(@RequestBody GuideRequest guideRequest) {
        GuideResponse guideResponse = guideService.addGuide(guideRequest);
        return new ResponseEntity<>(guideResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GuideResponse> editGuide(
            @PathVariable("id") Long guideId,
            @RequestBody GuideRequest guideRequest
    ) {
        GuideResponse guideResponse = guideService.editGuide(guideId, guideRequest);
        return ResponseEntity.ok(guideResponse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGuide(@PathVariable("id") Long guideId) {
        guideService.deleteGuide(guideId);
    }
}
