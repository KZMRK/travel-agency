package com.kazmiruk.travel_agency.repository;

import com.kazmiruk.travel_agency.model.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TourRepository extends JpaRepository<Tour, Long> {

    @Query("SELECT t FROM Tour t ORDER BY RANDOM() LIMIT 1")
    Optional<Tour> findRandomTour();

}
