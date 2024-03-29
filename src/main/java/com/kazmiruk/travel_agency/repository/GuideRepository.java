package com.kazmiruk.travel_agency.repository;

import com.kazmiruk.travel_agency.model.Guide;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuideRepository extends JpaRepository<Guide, Long> {
}
