package com.kazmiruk.travel_agency.service;

import com.kazmiruk.travel_agency.mapper.ClientTourMapper;
import com.kazmiruk.travel_agency.mapper.TourMapper;
import com.kazmiruk.travel_agency.model.dto.BookTourDto;
import com.kazmiruk.travel_agency.model.dto.ClientTourDto;
import com.kazmiruk.travel_agency.model.dto.TourAggregateDto;
import com.kazmiruk.travel_agency.model.dto.TourDto;
import com.kazmiruk.travel_agency.model.entity.Client;
import com.kazmiruk.travel_agency.model.entity.ClientTour;
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
import static com.kazmiruk.travel_agency.type.ErrorMessageType.BOOKING_TIME_FRAME;
import static com.kazmiruk.travel_agency.type.ErrorMessageType.MAX_DISCOUNT_EXCEEDED;

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

        tour.setDestination(countryRepository.getOneById(tour.getDestination().getId()));
        tour.setDeparture(countryRepository.getOneById(tour.getDeparture().getId()));
        tour.setGuide(guideRepository.getOneById(tour.getGuide().getId()));

        tour = tourRepository.save(tour);
        return tourMapper.toDto(tour);
    }

    @Transactional
    public Set<TourDto> getAllTours() {
        List<Tour> tours = tourRepository.findAll();
        return tours.stream().map(tourMapper::toDto).collect(Collectors.toSet());
    }

    @Transactional
    public TourDto updateTour(Long tourId, TourDto tourRequest) {
        Tour tour = tourRepository.getOneById(tourId);
        tourMapper.updateEntity(tour, tourRequest);

        tour.setDestination(countryRepository.getOneById(tour.getDestination().getId()));
        tour.setDeparture(countryRepository.getOneById(tour.getDeparture().getId()));
        tour.setGuide(guideRepository.getOneById(tour.getGuide().getId()));

        return tourMapper.toDto(tour);
    }

    @Transactional
    public void deleteTour(Long tourId) {
        Tour tour = tourRepository.getOneById(tourId);
        tourRepository.delete(tour);
    }

    @Transactional
    public ClientTourDto bookTour(Long tourId, Long clientId, BookTourDto bookTourRequest) {
        Tour tour = tourRepository.getOneById(tourId);
        Client client = clientRepository.getOneById(clientId);

        BigDecimal clientDiscount = calculateClientDiscount(
                tour.getInitialPrice(),
                bookTourRequest.getSellingPrice()
        );
        BigDecimal minSellingPrice = calculateMinTourSellingPrice(tour);

        if (clientDiscount.compareTo(BigDecimal.valueOf(tourBookingProperties.getDiscount())) > 0) {
            throw new BadRequestException(
                    MAX_DISCOUNT_EXCEEDED,
                    clientDiscount,
                    tourBookingProperties.getDiscount(),
                    minSellingPrice
            );
        }

        if (isClientHasTourAtSameTimeframe(client, tour)) {
            throw new BadRequestException(BOOKING_TIME_FRAME);
        }

        ClientTour bookedTour = new ClientTour(tour, client, bookTourRequest.getSellingPrice());
        bookedTour = clientTourRepository.save(bookedTour);

        return clientTourMapper.toDto(bookedTour);
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
                        new NotFoundException(BOOKING_NOT_FOUND, clientId, tourId)
                );
        clientTourRepository.delete(clientTour);
    }

    @Transactional(readOnly = true)
    public TourAggregateDto getTourSumAndAvgSellingPrice(Long tourId) {
        Tour tour = tourRepository.getOneById(tourId);
        return tourRepository.sumAndAvgTourSellingPrices(tour);
    }

    @Transactional(readOnly = true)
    public TourDto getMostPopularTourWithTheLowestSellingPrice() {
        Optional<Tour> tourOpt = tourRepository.findMostPopularTourWithTheLowestSellingPrice();
        return tourMapper.toDto(tourOpt.orElse(null));
    }
}
