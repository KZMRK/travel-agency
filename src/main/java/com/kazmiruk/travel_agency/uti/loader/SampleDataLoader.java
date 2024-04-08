package com.kazmiruk.travel_agency.uti.loader;

import com.github.javafaker.Faker;
import com.kazmiruk.travel_agency.model.Client;
import com.kazmiruk.travel_agency.model.Country;
import com.kazmiruk.travel_agency.model.Guide;
import com.kazmiruk.travel_agency.model.Tour;
import com.kazmiruk.travel_agency.model.TourSellingPrice;
import com.kazmiruk.travel_agency.model.key.TourSellingPriceKey;
import com.kazmiruk.travel_agency.repository.ClientRepository;
import com.kazmiruk.travel_agency.repository.CountryRepository;
import com.kazmiruk.travel_agency.repository.GuideRepository;
import com.kazmiruk.travel_agency.repository.TourRepository;
import com.kazmiruk.travel_agency.repository.TourSellingPriceRepository;
import com.kazmiruk.travel_agency.uti.holder.TourBookingProps;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class SampleDataLoader /*implements CommandLineRunner*/ {

    private final CountryRepository countryRepository;

    private final ClientRepository clientRepository;

    private final GuideRepository guideRepository;

    private final TourRepository tourRepository;

    private final Faker faker;
    
    private final TourSellingPriceRepository tourSellingPriceRepository;

    private final TourBookingProps tourBookingProps;

    /*@Override*/
    public void run(String... args) {
        /*loadRandomCountries(50);
        loadRandomClients(50);
        loadRandomGuides(50);
        loadRandomTours(100);*/
        /*joinClientsWithTours(200);*/
    }

    private void joinClientsWithTours(int numOfRows) {
        Random random = new Random();
        long numOfClients = clientRepository.count();
        long numOfTours = clientRepository.count();

        List<TourSellingPrice> tourSellingPrices = IntStream.rangeClosed(1, numOfRows)
                .mapToObj(i -> {
                    long tourId = random.nextLong(numOfTours) + 1;
                    long clientId = random.nextLong(numOfClients) + 1;
                    TourSellingPriceKey tourSellingPriceKey = new TourSellingPriceKey(
                            tourId, clientId
                    );

                    Client client = clientRepository.findById(clientId).get();
                    Tour tour = tourRepository.findById(tourId).get();

                    int discount = random.nextInt(tourBookingProps.getDiscount()) + 1;
                    double sellingPrice = tour.getInitialPrice() * discount;

                    return new TourSellingPrice(
                            tourSellingPriceKey,
                            tour,
                            client,
                            sellingPrice
                    );
                }).toList();

        tourSellingPriceRepository.saveAll(tourSellingPrices);
    }

    private void loadRandomTours(int numOfRows) {
        Random random = new Random();
        long numOfGuides = guideRepository.count();
        int numOfCountries = (int) countryRepository.count();
        List<Tour> tours = IntStream.rangeClosed(1, numOfRows)
                .mapToObj(i -> {
                    int departureId = random.nextInt(numOfCountries) + 1;
                    int destinationId;
                    do {
                        destinationId = random.nextInt(numOfCountries) + 1;
                    } while (departureId == destinationId);
                    long guideId = random.nextLong(numOfGuides) + 1;

                    Country departure = countryRepository.findById(departureId).get();
                    Country destination = countryRepository.findById(destinationId).get();
                    Guide guide = guideRepository.findById(guideId).get();

                    long minDay = LocalDate.now().toEpochDay();
                    long randomDepartureDay = minDay + 1 + random.nextLong(30);
                    long randomReturnAtDay = randomDepartureDay + 1 + random.nextLong(13);

                    final double minPrice = 1000;
                    final double maxPrice = 5000;
                    double initialPrice = minPrice + (maxPrice - minPrice) * random.nextDouble();

                    return Tour.builder()
                            .departure(departure)
                            .destination(destination)
                            .departureAt(LocalDate.ofEpochDay(randomDepartureDay))
                            .returnAt(LocalDate.ofEpochDay(randomReturnAtDay))
                            .initialPrice(initialPrice)
                            .guide(guide)
                            .build();
                }).toList();

        tourRepository.saveAll(tours);
    }

    private void loadRandomCountries(int numOfRows) {
        List<Country> countries = Stream.generate(() -> new Country(faker.address().country()))
                .distinct()
                .limit(numOfRows)
                .toList();

        countryRepository.saveAll(countries);
    }

    private void loadRandomClients(int numOfRows) {
        List<Client> clients = IntStream.rangeClosed(1, numOfRows)
                .mapToObj(i ->
                        new Client(
                                faker.name().firstName(),
                                faker.name().lastName(),
                                faker.number().digits(9)
                        )
                )
                .toList();

        clientRepository.saveAll(clients);
    }

    private void loadRandomGuides(int numOfRows) {
        List<Guide> guides = IntStream.rangeClosed(1, numOfRows)
                .mapToObj(i ->
                        new Guide(
                                faker.name().firstName(),
                                faker.name().lastName()
                        )
                )
                .toList();

        guideRepository.saveAll(guides);
    }
}
