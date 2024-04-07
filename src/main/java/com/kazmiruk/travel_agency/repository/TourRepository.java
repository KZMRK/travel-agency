package com.kazmiruk.travel_agency.repository;

import com.kazmiruk.travel_agency.model.Tour;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourRepository extends JpaRepository<Tour, Long> {
}
