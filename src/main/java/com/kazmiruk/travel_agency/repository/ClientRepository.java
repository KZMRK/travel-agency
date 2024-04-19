package com.kazmiruk.travel_agency.repository;

import com.kazmiruk.travel_agency.model.entity.Client;
import com.kazmiruk.travel_agency.model.entity.Tour;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface ClientRepository extends BaseRepository<Client, Long> {

    boolean existsByPassportNumber(String passportNumber);

    @Query("SELECT c.bookedTours FROM Client c WHERE c.id = :clientId")
    Set<Tour> findClientToursByClientId(Long clientId);

    @Query("SELECT c FROM Client c WHERE c NOT IN " +
            "(SELECT DISTINCT bt.client FROM ClientTour bt " +
            "WHERE YEAR(bt.tour.departureAt) = :year) ORDER BY c.passportNumber DESC")
    Set<Client> findClientsWithoutTourInYear(Integer year);

    @Query("SELECT ct.client FROM ClientTour ct ORDER BY ct.tour.initialPrice - ct.sellingPrice DESC LIMIT 1")
    Optional<Client> findClientWithHighestDiscount();

    @Query("SELECT ct.client, SUM(ct.sellingPrice) FROM ClientTour ct " +
            "GROUP BY ct.client " +
            "ORDER BY SUM(ct.sellingPrice) " +
            "DESC LIMIT 1")
    Optional<Client> findClientGeneratedHighestRevenue();
}
