package com.kazmiruk.travel_agency.service;

import com.kazmiruk.travel_agency.mapper.GuideMapper;
import com.kazmiruk.travel_agency.model.dto.GuideDto;
import com.kazmiruk.travel_agency.model.entity.Guide;
import com.kazmiruk.travel_agency.repository.GuideRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GuideService {

    private final GuideRepository guideRepository;

    private final GuideMapper guideMapper;

    @Transactional
    public GuideDto createGuide(GuideDto guideRequest) {
        Guide guide = guideMapper.toEntity(guideRequest);
        guide = guideRepository.save(guide);
        return guideMapper.toDto(guide);
    }

    @Transactional(readOnly = true)
    public Set<GuideDto> getAllGuides() {
        List<Guide> guides = guideRepository.findAll();
        return guides.stream().map(guideMapper::toDto).collect(Collectors.toSet());
    }

    @Transactional
    public GuideDto updateGuide(Long guideId, GuideDto guideRequest) {
        Guide guide = guideRepository.getOneById(guideId);
        guideMapper.updateEntity(guide, guideRequest);
        return guideMapper.toDto(guide);
    }

    @Transactional
    public void deleteGuide(Long guideId) {
        Guide guide = guideRepository.getOneById(guideId);
        guideRepository.delete(guide);
    }

    @Transactional(readOnly = true)
    public GuideDto getGuideGeneratedHighestRevenue() {
        Optional<Guide> guideOpt = guideRepository.findGuideGeneratedHighestRevenue();
        return guideMapper.toDto(guideOpt.orElse(null));
    }
}
