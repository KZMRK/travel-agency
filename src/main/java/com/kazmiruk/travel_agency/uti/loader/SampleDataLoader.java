package com.kazmiruk.travel_agency.uti.loader;

import com.github.javafaker.Faker;
import com.kazmiruk.travel_agency.model.BookedTour;
import com.kazmiruk.travel_agency.model.Client;
import com.kazmiruk.travel_agency.model.Country;
import com.kazmiruk.travel_agency.model.Guide;
import com.kazmiruk.travel_agency.model.Tour;
import com.kazmiruk.travel_agency.model.key.BookedTourKey;
import com.kazmiruk.travel_agency.repository.BookedTourRepository;
import com.kazmiruk.travel_agency.repository.ClientRepository;
import com.kazmiruk.travel_agency.repository.CountryRepository;
import com.kazmiruk.travel_agency.repository.GuideRepository;
import com.kazmiruk.travel_agency.repository.TourRepository;
import com.kazmiruk.travel_agency.uti.holder.DBFillingProps;
import com.kazmiruk.travel_agency.uti.holder.TourBookingProps;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(
        prefix = "database",
        value = "loader",
        havingValue = "true"
)
public class SampleDataLoader implements CommandLineRunner {

    private final Faker faker;

    private final DBFillingProps dbFillingProps;

    private final CountryRepository countryRepository;

    private final ClientRepository clientRepository;

    private final GuideRepository guideRepository;

    private final TourRepository tourRepository;

    private final BookedTourRepository bookedTourRepository;

    private final TourBookingProps tourBookingProps;

    @Override
    public void run(String... args) {
        bookedTourRepository.deleteAll();
        countryRepository.deleteAll();
        clientRepository.deleteAll();
        guideRepository.deleteAll();
        tourRepository.deleteAll();

        loadRandomCountries();
        loadRandomClients();
        loadRandomGuides();
        loadRandomTours();
        joinClientsWithTours();
    }

    private void joinClientsWithTours() {
        Random random = new Random();

        List<BookedTour> bookedTours = Stream.generate(() -> {
            Tour tour = tourRepository.findRandomTour().get();
            Client client = clientRepository.findRandom().get();

            BookedTourKey bookedTourKey = new BookedTourKey(
                    tour.getId(), client.getId()
            );

            int discount = random.nextInt(tourBookingProps.getDiscount()) + 1;
            double sellingPrice = tour.getInitialPrice() * discount;

            return new BookedTour(
                    bookedTourKey,
                    tour,
                    client,
                    sellingPrice
            );
        })
                .limit(dbFillingProps.getBookedTour())
                .toList();

        bookedTourRepository.saveAll(bookedTours);
    }

    private void loadRandomTours() {
        Random random = new Random();
        List<Tour> tours = Stream.generate(() -> {
            Country departure = countryRepository.findRandom().get();
            Country destination;
            do {
                destination = countryRepository.findRandom().get();
            } while (!departure.equals(destination));
            Guide guide = guideRepository.findRandom().get();

            long minDay = LocalDate.now().toEpochDay();
            long randomDepartureDay = minDay + 1 + random.nextLong(1095);
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
        })
                .limit(dbFillingProps.getTour())
                .toList();

        tourRepository.saveAll(tours);
    }

    private void loadRandomCountries() {
        List<Country> countries = Stream.generate(() -> new Country(faker.address().country()))
                .distinct()
                .limit(dbFillingProps.getCountry())
                .toList();

        countryRepository.saveAll(countries);
    }

    private void loadRandomClients() {
        List<Client> clients = Stream.generate(() ->
                new Client(
                        faker.name().firstName(),
                        faker.name().lastName(),
                        faker.number().digits(9)
                ))
                .limit(dbFillingProps.getClient())
                .toList();

        clientRepository.saveAll(clients);
    }

    private void loadRandomGuides() {
        List<Guide> guides = Stream.generate(() ->
                new Guide(
                        faker.name().firstName(),
                        faker.name().lastName()
                ))
                .limit(dbFillingProps.getGuide())
                .toList();

        guideRepository.saveAll(guides);
    }
}
