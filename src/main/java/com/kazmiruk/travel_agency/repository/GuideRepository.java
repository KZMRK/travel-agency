package com.kazmiruk.travel_agency.repository;

import com.kazmiruk.travel_agency.model.entity.Guide;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface GuideRepository extends BaseRepository<Guide, Long> {

    @Query("SELECT ct.tour.guide FROM ClientTour ct GROUP BY ct.tour.guide " +
            "ORDER BY SUM(ct.sellingPrice) DESC LIMIT 1")
    Optional<Guide> findGuideGeneratedHighestRevenue();

}
