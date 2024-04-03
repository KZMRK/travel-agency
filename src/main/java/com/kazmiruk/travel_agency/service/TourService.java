package com.kazmiruk.travel_agency.service;

import com.kazmiruk.travel_agency.dto.GuideDto;
import com.kazmiruk.travel_agency.dto.TourRequest;
import com.kazmiruk.travel_agency.dto.TourResponse;
import com.kazmiruk.travel_agency.mapper.TourMapper;
import com.kazmiruk.travel_agency.model.Country;
import com.kazmiruk.travel_agency.model.Guide;
import com.kazmiruk.travel_agency.model.Tour;
import com.kazmiruk.travel_agency.repository.CountryRepository;
import com.kazmiruk.travel_agency.repository.GuideRepository;
import com.kazmiruk.travel_agency.repository.TourRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TourService {

    private final TourRepository tourRepository;

    private final TourMapper tourMapper;

    private final CountryRepository countryRepository;

    private final GuideRepository guideRepository;

    public Iterable<TourResponse> getTours() {
        Iterable<Tour> tours = tourRepository.findAll();
        return tourMapper.toResponse(tours);
    }

    public TourResponse addTour(TourRequest tourRequest) {
        Tour tour = tourMapper.toEntity(tourRequest);
        tour.setDeparture(getCountryIfExistOrSaveAndGet(tour.getDeparture()));
        tour.setDestination(getCountryIfExistOrSaveAndGet(tour.getDestination()));
        tour.setGuide(getGuideIfExistOrSaveAndGet(tour.getGuide()));

        return tourMapper.toResponse(tourRepository.save(tour));
    }

    private Country getCountryIfExistOrSaveAndGet(Country country) {
        if (Objects.isNull(country.getId())) {
            return countryRepository.save(country);
        } else {
            return countryRepository.findById(country.getId()).get();
        }
    }

    private Guide getGuideIfExistOrSaveAndGet(Guide guide) {
        if (Objects.isNull(guide.getId())) {
            return guideRepository.save(guide);
        } else {
            return guideRepository.findById(guide.getId()).get();
        }
    }


    public TourResponse editTour(Long tourId, TourRequest tourRequest) {
        Tour tour = tourMapper.toEntity(tourRequest);
        tour.setId(tourId);
        return tourMapper.toResponse(tourRepository.save(tour));
    }

    public void deleteTour(Long tourId) {
        tourRepository.deleteById(tourId);
    }
}
