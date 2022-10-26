package com.snippet.docketbackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.snippet.docketbackend.models.EventTemplate;
import com.snippet.docketbackend.models.User;

@Repository
public interface EventRepo extends JpaRepository<EventTemplate, Long> {
    @Query("select s from EventTemplate s where s.name = ?1 and s.useremail = ?2")
    Optional<EventTemplate> findByName(String name, String useremail);

    @Query("select s from EventTemplate s where s.name = ?1 and s.useremail = ?2 and s.linkName = ?3")
    Optional<EventTemplate> findExistence(String name, String useremail, String linkName);

    @Query("select s from EventTemplate s where s.type = ?1 and s.useremail = ?2")
    List<EventTemplate> findByType(String type, String useremail);

    @Query("select s from EventTemplate s where s.linkName = ?1 and s.useremail = ?2")
    Optional<EventTemplate> findByLinkName(String linkName, String useremail);

    @Query("select s from EventTemplate s where s.id = ?1 and s.useremail = ?2")
    Optional<EventTemplate> findEventById(Long id, String useremail);
}
