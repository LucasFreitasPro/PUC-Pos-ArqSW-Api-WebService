package com.grouptwo.soccer.championships.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grouptwo.soccer.championships.models.Championship;

public interface ChampionshipRepository extends JpaRepository<Championship, UUID> {

	Championship findByNameAndDivisionName(String name, String divisionName);
}
