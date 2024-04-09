package com.kazmiruk.travel_agency.service;

import com.kazmiruk.travel_agency.dto.GuideRequest;
import com.kazmiruk.travel_agency.dto.GuideResponse;
import com.kazmiruk.travel_agency.mapper.GuideMapper;
import com.kazmiruk.travel_agency.model.Guide;
import com.kazmiruk.travel_agency.repository.GuideRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuideService {

    private final GuideRepository guideRepository;

    private final GuideMapper guideMapper;

    public Iterable<GuideResponse> getGuides() {
        Iterable<Guide> guides = guideRepository.findAll();
        return guideMapper.toResponse(guides);
    }

    public GuideResponse addGuide(GuideRequest guideRequest) {
        Guide guide = guideMapper.toEntity(guideRequest);
        Guide savedGuide = guideRepository.save(guide);
        return guideMapper.toResponse(savedGuide);
    }

    public GuideResponse editGuide(Long guideId, GuideRequest guideRequest) {
        Guide updatedGuide = guideRepository.findById(guideId)
                .map(guide -> {
                    guide.setFirstName(guideRequest.getFirstName());
                    guide.setLastName(guideRequest.getLastName());
                    return guideRepository.save(guide);
                }).orElseGet(() -> {
                    Guide newGuide = guideMapper.toEntity(guideRequest);
                    newGuide.setId(guideId);
                    return guideRepository.save(newGuide);
                });
        return guideMapper.toResponse(updatedGuide);
    }

    public void deleteGuide(Long guideId) {
        guideRepository.deleteById(guideId);
    }

    public GuideResponse getGuideGeneratedHighestRevenue() {
        Guide guide = guideRepository.findGuideGeneratedHighestRevenue().get();
        return guideMapper.toResponse(guide);
    }
}
