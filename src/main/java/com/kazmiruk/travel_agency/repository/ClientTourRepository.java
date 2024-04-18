package com.kazmiruk.travel_agency.repository;

import com.kazmiruk.travel_agency.model.entity.ClientTour;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientTourRepository extends JpaRepository<ClientTour, Long> {

    Optional<ClientTour> findByTourIdAndClientId(Long tourId, Long clientId);

}
