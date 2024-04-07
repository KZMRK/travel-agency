package com.kazmiruk.travel_agency.repository;

import com.kazmiruk.travel_agency.model.TourSellingPrice;
import com.kazmiruk.travel_agency.model.key.TourSellingPriceKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourSellingPriceRepository extends JpaRepository<TourSellingPrice, TourSellingPriceKey> {
}
