package com.snippet.docketbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.snippet.docketbackend.models.Availability;

@Repository
public interface AvailabilityRepo extends JpaRepository<Availability, Long> {
    
}
