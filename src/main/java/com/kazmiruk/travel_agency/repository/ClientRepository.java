package com.kazmiruk.travel_agency.repository;

import com.kazmiruk.travel_agency.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
