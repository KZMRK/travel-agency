package com.kazmiruk.travel_agency.repository;

import com.kazmiruk.travel_agency.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
