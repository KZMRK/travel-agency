package com.kazmiruk.travel_agency.repository;

import com.kazmiruk.travel_agency.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("SELECT c FROM Client c ORDER BY RANDOM() LIMIT 1")
    Optional<Client> findRandom();

}
