package com.kazmiruk.travel_agency.repository;

import com.kazmiruk.travel_agency.dto.TourAggregateResponse;
import com.kazmiruk.travel_agency.model.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TourRepository extends JpaRepository<Tour, Long> {

    @Query("SELECT t FROM Tour t ORDER BY RANDOM() LIMIT 1")
    Optional<Tour> findRandomTour();


    @Query("SELECT new com.kazmiruk.travel_agency.dto.TourAggregateResponse(SUM(bt.sellingPrice), AVG(bt.sellingPrice)) " +
            "FROM BookedTour bt WHERE bt.tour.id = :id GROUP BY bt.tour")
    TourAggregateResponse sumAndAvgTourSellingPrices(@Param("id") Long tourId);

}
