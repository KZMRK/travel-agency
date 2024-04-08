package com.kazmiruk.travel_agency.repository;

import com.kazmiruk.travel_agency.model.BookedTour;
import com.kazmiruk.travel_agency.model.key.BookedTourKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookedTourRepository extends JpaRepository<BookedTour, BookedTourKey> {
}
