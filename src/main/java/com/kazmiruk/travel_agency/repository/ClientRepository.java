package com.kazmiruk.travel_agency.repository;

import com.kazmiruk.travel_agency.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("SELECT c FROM Client c ORDER BY RANDOM() LIMIT 1")
    Optional<Client> findRandom();

    @Query("SELECT c FROM Client c WHERE c NOT IN " +
            "(SELECT DISTINCT bt.client FROM BookedTour bt " +
            "WHERE YEAR(bt.tour.departureAt) = :year) ORDER BY c.passportNumber DESC")
    List<Client> findClientsWithoutTourInYear(
            @Param("year") Integer year
    );

    @Query("SELECT bt.client FROM BookedTour bt ORDER BY bt.tour.initialPrice - bt.sellingPrice DESC LIMIT 1")
    Optional<Client> findClientWithHighestDiscount();

}
