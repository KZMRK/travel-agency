package com.kazmiruk.travel_agency.service;

import com.kazmiruk.travel_agency.dto.*;
import com.kazmiruk.travel_agency.mapper.TourMapper;
import com.kazmiruk.travel_agency.mapper.TourSellingPriceMapper;
import com.kazmiruk.travel_agency.model.*;
import com.kazmiruk.travel_agency.model.key.BookedTourKey;
import com.kazmiruk.travel_agency.repository.*;
import com.kazmiruk.travel_agency.uti.error.SameTimeFrameException;
import com.kazmiruk.travel_agency.uti.error.TooMuchDiscountException;
import com.kazmiruk.travel_agency.uti.holder.TourBookingProps;
import lombok.RequiredArgsConstructor;
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

    public TourSellingPriceResponse bookTour(Long tourId, Long clientId, BookTourRequest bookTourRequest) {
        Tour tour = tourRepository.findById(tourId).get();
        double discount = ((tour.getInitialPrice() - bookTourRequest.getSellingPrice()) * 100) / tour.getInitialPrice();
        if (discount > tourBookingProps.getDiscount()) {
            throw new TooMuchDiscountException(
                    String.format("Discount %.1f exceeds the maximum discount %d",
                            discount,
                            tourBookingProps.getDiscount())
            );
        }
        Client client = clientRepository.findById(clientId).get();
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
}
