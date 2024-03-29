package com.kazmiruk.travel_agency.repository;

import com.kazmiruk.travel_agency.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Integer> {
}
