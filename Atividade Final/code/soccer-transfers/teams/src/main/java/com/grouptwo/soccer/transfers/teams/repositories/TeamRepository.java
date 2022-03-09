package com.grouptwo.soccer.transfers.teams.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.grouptwo.soccer.transfers.teams.models.Team;

public interface TeamRepository extends JpaRepository<Team, UUID> {

	public Optional<Team> findByName(String teamName);

	@Query(value = "SELECT * FROM team WHERE name = :teamName", nativeQuery = true)
	public Optional<Team> getByName(@Param("teamName") String teamName);
}
