package com.kazmiruk.travel_agency.service;

import com.kazmiruk.travel_agency.dto.*;
import com.kazmiruk.travel_agency.mapper.CountryMapper;
import com.kazmiruk.travel_agency.mapper.GuideMapper;
import com.kazmiruk.travel_agency.mapper.TourMapper;
import com.kazmiruk.travel_agency.mapper.TourSellingPriceMapper;
import com.kazmiruk.travel_agency.model.*;
import com.kazmiruk.travel_agency.model.key.BookedTourKey;
import com.kazmiruk.travel_agency.repository.*;
import com.kazmiruk.travel_agency.uti.error.ClientNotFoundException;
import com.kazmiruk.travel_agency.uti.error.CountryNotFoundException;
import com.kazmiruk.travel_agency.uti.error.CountryWithNameAlreadyExistException;
import com.kazmiruk.travel_agency.uti.error.GuideNotFoundException;
import com.kazmiruk.travel_agency.uti.error.SameTimeFrameException;
import com.kazmiruk.travel_agency.uti.error.TooMuchDiscountException;
import com.kazmiruk.travel_agency.uti.error.TourCantBeDeletedException;
import com.kazmiruk.travel_agency.uti.error.TourNotFoundException;
import com.kazmiruk.travel_agency.uti.holder.TourBookingProps;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TourService {

    private final TourRepository tourRepository;

    private final TourMapper tourMapper;

    private final CountryRepository countryRepository;

    private final GuideRepository guideRepository;

    private final ClientRepository clientRepository;

    private final TourBookingProps tourBookingProps;

    private final BookedTourRepository bookedTourRepository;

    private final TourSellingPriceMapper tourSellingPriceMapper;

    private final CountryMapper countryMapper;

    private final GuideMapper guideMapper;

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
            try {
                return countryRepository.save(country);
            } catch (DataIntegrityViolationException e) {
                throw new CountryWithNameAlreadyExistException("Country with name '" + country.getName() + "' already exist");
            }
        } else {
            return countryRepository.findById(country.getId()).orElseThrow(() ->
                    new CountryNotFoundException("Country with id " + country.getId() + " not found")
            );
        }
    }

    private Guide getGuideIfExistOrSaveAndGet(Guide guide) {
        if (Objects.isNull(guide.getId())) {
            return guideRepository.save(guide);
        } else {
            return guideRepository.findById(guide.getId()).orElseThrow(() ->
                    new GuideNotFoundException("Guide with id " + guide.getId() + " not found")
            );
        }
    }


    public TourResponse updateTour(Long tourId, TourRequest tourRequest) {
        Tour updatedTour = tourRepository.findById(tourId)
                .map(tour -> {
                    tour.setDestination(
                            getCountryIfExistOrSaveAndGet(
                                    countryMapper.toEntity(tourRequest.getDestination())
                            )
                    );
                    tour.setDeparture(
                            getCountryIfExistOrSaveAndGet(
                                    countryMapper.toEntity(tourRequest.getDeparture())
                            )
                    );
                    tour.setDepartureAt(tourRequest.getDepartureAt());
                    tour.setReturnAt(tourRequest.getReturnAt());
                    tour.setGuide(
                            getGuideIfExistOrSaveAndGet(
                                    guideMapper.toEntity(tourRequest.getGuide())
                            )
                    );
                    tour.setInitialPrice(tourRequest.getInitialPrice());
                    return tourRepository.save(tour);
                })
                .orElseThrow(() -> new GuideNotFoundException("Tour with id " + tourId + " not found"));
        return tourMapper.toResponse(updatedTour);
    }

    public void deleteTour(Long tourId) {
        Tour tour = tourRepository.findById(tourId).orElseThrow(() -> new TourNotFoundException("Tour with id " + tourId + " not found"));
        try {
            tourRepository.delete(tour);
        } catch (DataIntegrityViolationException e) {
            throw new TourCantBeDeletedException("Tour with id " + tourId + " is booked by client/clients");
        }
    }

    public TourSellingPriceResponse bookTour(Long tourId, Long clientId, BookTourRequest bookTourRequest) {
        Tour tour = tourRepository.findById(tourId).orElseThrow(() ->
                new TourNotFoundException("Tour with id " + tourId + " not found")
        );
        Client client = clientRepository.findById(clientId).orElseThrow(() ->
                new ClientNotFoundException("Client with id " + clientId + " not found")
        );

        double discount = ((tour.getInitialPrice() - bookTourRequest.getSellingPrice()) * 100) / tour.getInitialPrice();
        if (discount > tourBookingProps.getDiscount()) {
            throw new TooMuchDiscountException(
                    String.format("Discount %.1f%% exceeds the maximum discount %d%% (min selling price - %.1f)",
                            discount,
                            tourBookingProps.getDiscount(),
                            tour.getInitialPrice() * (1.0 - tourBookingProps.getDiscount() / 100.0)
                    )
            );
        }

        boolean isClientHasTourAtSameTimeframe = client.getBookedTours().stream()
                .map(BookedTour::getTour)
                .anyMatch(bookedTour ->
                        tour.getDepartureAt().isBefore(bookedTour.getReturnAt()) &&
                                bookedTour.getDepartureAt().isBefore(tour.getReturnAt())
                );

        if (isClientHasTourAtSameTimeframe) {
            throw new SameTimeFrameException("You can't book 2 tours at the same time");
        }

        BookedTour bookedTour = BookedTour.builder()
                .id(new BookedTourKey(tourId, clientId))
                .tour(tour)
                .client(client)
                .sellingPrice(bookTourRequest.getSellingPrice())
                .build();

        BookedTour saved = bookedTourRepository.save(bookedTour);

        return tourSellingPriceMapper.toResponse(saved);
    }

    public TourAggregateResponse getTourSumAndAvgSellingPrice(Long tourId) {
        return tourRepository.sumAndAvgTourSellingPrices(tourId).orElseThrow(() -> new TourNotFoundException("Tour with id " + tourId + " not found"));
    }

    public TourResponse getMostPopularTourWithTheLowestSellingPrice() {
        Tour tour = tourRepository.findMostPopularTourWithTheLowestSellingPrice().orElseThrow(() ->
                new TourNotFoundException("Tour not found")
        );
        return tourMapper.toResponse(tour);
    }
}
