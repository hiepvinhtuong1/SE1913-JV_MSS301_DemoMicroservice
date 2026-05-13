package com.example.orchidservice.repository;

import com.example.orchidservice.entity.Orchid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrchidRepository extends JpaRepository<Orchid, Long> {
}
