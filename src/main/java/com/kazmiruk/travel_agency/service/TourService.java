package com.kazmiruk.travel_agency.service;

import com.kazmiruk.travel_agency.mapper.TourMapper;
import com.kazmiruk.travel_agency.mapper.ClientTourMapper;
import com.kazmiruk.travel_agency.model.dto.BookTourDto;
import com.kazmiruk.travel_agency.model.dto.ClientTourDto;
import com.kazmiruk.travel_agency.model.dto.TourAggregateDto;
import com.kazmiruk.travel_agency.model.dto.TourDto;
import com.kazmiruk.travel_agency.model.entity.Client;
import com.kazmiruk.travel_agency.model.entity.ClientTour;
import com.kazmiruk.travel_agency.model.entity.Country;
import com.kazmiruk.travel_agency.model.entity.Guide;
import com.kazmiruk.travel_agency.model.entity.Tour;
import com.kazmiruk.travel_agency.model.exception.BadRequestException;
import com.kazmiruk.travel_agency.model.exception.NotFoundException;
import com.kazmiruk.travel_agency.model.properties.TourBookingProperties;
import com.kazmiruk.travel_agency.repository.ClientRepository;
import com.kazmiruk.travel_agency.repository.ClientTourRepository;
import com.kazmiruk.travel_agency.repository.CountryRepository;
import com.kazmiruk.travel_agency.repository.GuideRepository;
import com.kazmiruk.travel_agency.repository.TourRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.kazmiruk.travel_agency.type.ErrorMessageType.BOOKING_NOT_FOUND;
import static com.kazmiruk.travel_agency.type.ErrorMessageType.CLIENT_NOT_FOUND;
import static com.kazmiruk.travel_agency.type.ErrorMessageType.COUNTRY_NOT_FOUND;
import static com.kazmiruk.travel_agency.type.ErrorMessageType.GUIDE_NOT_FOUND;
import static com.kazmiruk.travel_agency.type.ErrorMessageType.MAX_DISCOUNT_EXCEEDED;
import static com.kazmiruk.travel_agency.type.ErrorMessageType.TOUR_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class TourService {

    private final TourRepository tourRepository;

    private final TourMapper tourMapper;

    private final CountryRepository countryRepository;

    private final GuideRepository guideRepository;

    private final ClientRepository clientRepository;

    private final TourBookingProperties tourBookingProperties;

    private final ClientTourRepository clientTourRepository;

    private final ClientTourMapper clientTourMapper;

    @Transactional
    public TourDto createTour(TourDto tourDto) {
        Tour tour = tourMapper.toEntity(tourDto);

        tour.setDestination(getCountryById(tour.getDestination().getId()));
        tour.setDeparture(getCountryById(tour.getDeparture().getId()));
        tour.setGuide(getGuideById(tour.getGuide().getId()));

        tour = tourRepository.save(tour);
        return tourMapper.toDto(tour);
    }

    private Country getCountryById(Integer countryId) {
        return countryRepository.findById(countryId).orElseThrow(() ->
                new NotFoundException(
                        COUNTRY_NOT_FOUND.getMessage().formatted(countryId)
                )
        );
    }

    private Guide getGuideById(Long guideId) {
        return guideRepository.findById(guideId).orElseThrow(() ->
                new NotFoundException(
                        GUIDE_NOT_FOUND.getMessage().formatted(guideId)
                )
        );
    }

    @Transactional
    public Set<TourDto> getAllTours() {
        List<Tour> tours = tourRepository.findAll();
        return tours.stream().map(tourMapper::toDto).collect(Collectors.toSet());
    }

    @Transactional
    public TourDto updateTour(Long tourId, TourDto tourRequest) {
        Tour tour = getTourById(tourId);
        tourMapper.updateEntity(tour, tourRequest);

        tour.setDestination(getCountryById(tourRequest.getDestination().getId()));
        tour.setDeparture(getCountryById(tourRequest.getDeparture().getId()));
        tour.setGuide(getGuideById(tourRequest.getGuide().getId()));

        return tourMapper.toDto(tour);
    }

    private Tour getTourById(Long tourId) {
        return tourRepository.findById(tourId).orElseThrow(() ->
                new NotFoundException(
                        TOUR_NOT_FOUND.getMessage().formatted(tourId)
                )
        );
    }

    @Transactional
    public void deleteTour(Long tourId) {
        Tour tour = getTourById(tourId);
        tourRepository.delete(tour);
    }

    @Transactional
    public ClientTourDto bookTour(Long tourId, Long clientId, BookTourDto bookTourRequest) {
        Tour tour = getTourById(tourId);
        Client client = getClientById(clientId);

        BigDecimal clientDiscount = calculateClientDiscount(
                tour.getInitialPrice(),
                bookTourRequest.getSellingPrice()
        );
        BigDecimal minSellingPrice = calculateMinTourSellingPrice(tour);

        if (clientDiscount.compareTo(BigDecimal.valueOf(tourBookingProperties.getDiscount())) > 0) {
            throw new BadRequestException(
                    MAX_DISCOUNT_EXCEEDED.getMessage().formatted(
                            clientDiscount,
                            tourBookingProperties.getDiscount(),
                            minSellingPrice
                    )
            );
        }

        if (isClientHasTourAtSameTimeframe(client, tour)) {
            throw new BadRequestException(BOOKING_NOT_FOUND.getMessage());
        }

        ClientTour bookedTour = new ClientTour(tour, client, bookTourRequest.getSellingPrice());
        bookedTour = clientTourRepository.save(bookedTour);

        return clientTourMapper.toDto(bookedTour);
    }

    private Client getClientById(Long clientId) {
        return clientRepository.findById(clientId).orElseThrow(() ->
                new NotFoundException(
                        CLIENT_NOT_FOUND.getMessage().formatted(clientId)
                )
        );
    }

    private BigDecimal calculateClientDiscount(BigDecimal initialPrice, BigDecimal sellingPrice) {
        BigDecimal discount = initialPrice.subtract(sellingPrice);
        return discount.divide(initialPrice, 5, RoundingMode.FLOOR)
                .multiply(BigDecimal.valueOf(100.0));
    }

    private BigDecimal calculateMinTourSellingPrice(Tour tour) {
        return tour.getInitialPrice()
                .multiply(
                        BigDecimal.valueOf(1.0).subtract(BigDecimal.valueOf(
                            tourBookingProperties.getDiscount() / 100.0
                        )
                ));
    }

    private boolean isClientHasTourAtSameTimeframe(Client client, Tour tour) {
        return client.getBookedTours().stream()
                .map(ClientTour::getTour)
                .anyMatch(bookedTour ->
                        tour.getDepartureAt().isBefore(bookedTour.getReturnAt()) &&
                                bookedTour.getDepartureAt().isBefore(tour.getReturnAt())
                );
    }

    @Transactional
    public void cancelBooking(Long tourId, Long clientId) {
        ClientTour clientTour = clientTourRepository.findByTourIdAndClientId(tourId, clientId)
                .orElseThrow(() ->
                        new NotFoundException(
                                BOOKING_NOT_FOUND.getMessage().formatted(clientId, tourId)
                        )
                );
        clientTourRepository.delete(clientTour);
    }

    @Transactional(readOnly = true)
    public TourAggregateDto getTourSumAndAvgSellingPrice(Long tourId) {
        Tour tour = getTourById(tourId);
        return tourRepository.sumAndAvgTourSellingPrices(tour);
    }

    @Transactional(readOnly = true)
    public TourDto getMostPopularTourWithTheLowestSellingPrice() {
        Optional<Tour> tourOpt = tourRepository.findMostPopularTourWithTheLowestSellingPrice();
        return tourMapper.toDto(tourOpt.orElse(null));
    }
}
