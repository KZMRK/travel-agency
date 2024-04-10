package com.kazmiruk.travel_agency.uti.loader;

import com.github.javafaker.Faker;
import com.kazmiruk.travel_agency.enums.Role;
import com.kazmiruk.travel_agency.model.BookedTour;
import com.kazmiruk.travel_agency.model.Client;
import com.kazmiruk.travel_agency.model.Country;
import com.kazmiruk.travel_agency.model.Guide;
import com.kazmiruk.travel_agency.model.Tour;
import com.kazmiruk.travel_agency.model.User;
import com.kazmiruk.travel_agency.model.key.BookedTourKey;
import com.kazmiruk.travel_agency.repository.BookedTourRepository;
import com.kazmiruk.travel_agency.repository.ClientRepository;
import com.kazmiruk.travel_agency.repository.CountryRepository;
import com.kazmiruk.travel_agency.repository.GuideRepository;
import com.kazmiruk.travel_agency.repository.TourRepository;
import com.kazmiruk.travel_agency.repository.UserRepository;
import com.kazmiruk.travel_agency.uti.holder.DBFillingProps;
import com.kazmiruk.travel_agency.uti.holder.TourBookingProps;
import com.kazmiruk.travel_agency.uti.holder.UserProps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
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
@Slf4j
public class SampleDataLoader implements CommandLineRunner {

    private final Faker faker;

    private final DBFillingProps dbFillingProps;

    private final TourBookingProps tourBookingProps;

    private final UserProps userProps;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final CountryRepository countryRepository;

    private final ClientRepository clientRepository;

    private final GuideRepository guideRepository;

    private final TourRepository tourRepository;

    private final BookedTourRepository bookedTourRepository;


    @Override
    public void run(String... args) {
        userRepository.deleteAll();
        bookedTourRepository.deleteAll();
        tourRepository.deleteAll();
        countryRepository.deleteAll();
        clientRepository.deleteAll();
        guideRepository.deleteAll();

        registerUser();
        loadRandomCountries();
        loadRandomClients();
        loadRandomGuides();
        loadRandomTours();
        joinClientsWithTours();
    }

    private void registerUser() {
        User user = new User(
                userProps.getEmail(),
                Role.ROLE_ADMIN,
                passwordEncoder.encode(userProps.getPassword())
        );
        log.info("User: {}",  user);
        userRepository.save(user);
    }

    private void loadRandomTours() {
        Random random = new Random();
        List<Tour> tours = Stream.generate(() -> {
            Country departure = countryRepository.findRandom().get();
            Country destination;
            do {
                destination = countryRepository.findRandom().get();
            } while (departure.equals(destination));
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

    private void joinClientsWithTours() {
        Random random = new Random();

        List<BookedTour> bookedTours = Stream.generate(() -> {
                    Tour tour = tourRepository.findRandomTour().get();
                    Client client = clientRepository.findRandom().get();

                    BookedTourKey bookedTourKey = new BookedTourKey(
                            tour.getId(), client.getId()
                    );

                    int discount = random.nextInt(tourBookingProps.getDiscount()) + 1;
                    double sellingPrice = tour.getInitialPrice() * (1.0 - discount / 100.0);

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
}
