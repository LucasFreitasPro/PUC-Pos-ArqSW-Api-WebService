package com.grouptwo.soccer.championships.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grouptwo.soccer.championships.models.Division;

public interface DivisionRepository extends JpaRepository<Division, UUID> {

	public Division findByName(String name);

}
