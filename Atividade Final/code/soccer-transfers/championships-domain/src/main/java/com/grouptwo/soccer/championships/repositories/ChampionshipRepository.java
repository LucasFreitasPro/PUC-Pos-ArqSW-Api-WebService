package com.grouptwo.soccer.championships.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.grouptwo.soccer.championships.models.Championship;

public interface ChampionshipRepository extends JpaRepository<Championship, UUID> {

	@Query("SELECT c FROM Championship c INNER JOIN FETCH c.division d WHERE c.name = :name AND d.id = :divisionId")
	public Championship findByNameAndDivisionId(@Param("name") String name, @Param("divisionId") UUID divisionId);

	public Championship findByName(String name);
}
