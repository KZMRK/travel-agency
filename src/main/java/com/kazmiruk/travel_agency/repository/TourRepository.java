package com.kazmiruk.travel_agency.repository;

import com.kazmiruk.travel_agency.model.dto.TourAggregateDto;
import com.kazmiruk.travel_agency.model.entity.Tour;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TourRepository extends BaseRepository<Tour, Long> {

    @Query("SELECT new com.kazmiruk.travel_agency.model.dto.TourAggregateDto(SUM(ct.sellingPrice), AVG(ct.sellingPrice)) " +
            "FROM ClientTour ct WHERE ct.tour = :tour GROUP BY ct.tour")
    TourAggregateDto sumAndAvgTourSellingPrices(Tour tour);

    @Query("SELECT tour FROM " +
            "   (SELECT bt.tour tour, MIN(bt.sellingPrice) min_selling_price " +
            "   FROM ClientTour bt " +
            "   GROUP BY bt.tour " +
            "   ORDER BY COUNT(*) DESC LIMIT 3) " +
            "ORDER BY min_selling_price ASC LIMIT 1")
    Optional<Tour> findMostPopularTourWithTheLowestSellingPrice();
}
