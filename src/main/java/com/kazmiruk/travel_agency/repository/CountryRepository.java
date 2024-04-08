package com.kazmiruk.travel_agency.repository;

import com.kazmiruk.travel_agency.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Integer> {

    @Query("SELECT bt.tour.destination, COUNT(*) FROM BookedTour bt " +
            "WHERE YEAR(bt.tour.departureAt) = :year GROUP BY bt.tour.destination " +
            "ORDER BY COUNT(*) DESC LIMIT 1")
    Country findMostPopularDestinationInYear(
            @Param("year") Integer year
    );

    @Query("SELECT c FROM Country c ORDER BY RANDOM() LIMIT 1")
    Optional<Country> findRandom();
}
