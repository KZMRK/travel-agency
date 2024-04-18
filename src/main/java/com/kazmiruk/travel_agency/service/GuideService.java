package com.kazmiruk.travel_agency.service;

import com.kazmiruk.travel_agency.model.dto.GuideDto;
import com.kazmiruk.travel_agency.mapper.GuideMapper;
import com.kazmiruk.travel_agency.model.entity.Guide;
import com.kazmiruk.travel_agency.repository.GuideRepository;
import com.kazmiruk.travel_agency.model.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.kazmiruk.travel_agency.type.ErrorMessageType.GUIDE_NOT_FOUND;

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
        Guide guide = getGuideById(guideId);
        guideMapper.updateEntity(guide, guideRequest);
        return guideMapper.toDto(guide);
    }

    private Guide getGuideById(Long guideId) {
        return guideRepository.findById(guideId).orElseThrow(() ->
                new NotFoundException(
                        GUIDE_NOT_FOUND.getMessage().formatted(guideId)
                )
        );
    }

    @Transactional
    public void deleteGuide(Long guideId) {
        Guide guide = getGuideById(guideId);
        guideRepository.delete(guide);
    }

    @Transactional(readOnly = true)
    public GuideDto getGuideGeneratedHighestRevenue() {
        Optional<Guide> guideOpt = guideRepository.findGuideGeneratedHighestRevenue();
        return guideMapper.toDto(guideOpt.orElse(null));
    }
}
