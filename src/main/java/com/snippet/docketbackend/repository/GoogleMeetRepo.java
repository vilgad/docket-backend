package com.snippet.docketbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.snippet.docketbackend.models.GoogleMeet;

@Repository
public interface GoogleMeetRepo extends JpaRepository<GoogleMeet, Long> {
    
}
