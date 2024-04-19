package com.kazmiruk.travel_agency.repository;

import com.kazmiruk.travel_agency.model.entity.Country;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CountryRepository extends BaseRepository<Country, Integer> {

    boolean existsByName(String name);

    @Query("SELECT ct.tour.destination, COUNT(*) FROM ClientTour ct " +
            "WHERE YEAR(ct.tour.departureAt) = :year GROUP BY ct.tour.destination " +
            "ORDER BY COUNT(*) DESC LIMIT 1")
    Optional<Country> findMostPopularDestinationInYear(Integer year);

}
