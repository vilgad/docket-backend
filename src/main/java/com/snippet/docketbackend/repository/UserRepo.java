package com.snippet.docketbackend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.snippet.docketbackend.models.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);

	@Query("select s from User s where s.linkName = ?1")
	Optional<User> findByLinkName(String linkName);
}
