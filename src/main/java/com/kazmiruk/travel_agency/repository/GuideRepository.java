package com.kazmiruk.travel_agency.repository;

import com.kazmiruk.travel_agency.model.Guide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface GuideRepository extends JpaRepository<Guide, Long> {

    @Query("SELECT g FROM Guide g ORDER BY RANDOM() LIMIT 1")
    Optional<Guide> findRandom();

}
