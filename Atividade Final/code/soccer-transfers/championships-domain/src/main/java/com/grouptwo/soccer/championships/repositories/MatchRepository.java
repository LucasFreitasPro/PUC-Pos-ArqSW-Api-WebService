package com.grouptwo.soccer.championships.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.grouptwo.soccer.championships.models.Match;

public interface MatchRepository extends JpaRepository<Match, UUID> {

	@Query("SELECT m FROM Match m INNER JOIN FETCH m.season s INNER JOIN FETCH s.championship c WHERE c.id = :championshipId AND s.id = :seasonId")
	List<Match> findAll(@Param("championshipId") UUID championshipId, @Param("seasonId") UUID seasonId);

	@Query("SELECT m FROM Match m INNER JOIN FETCH m.season s INNER JOIN FETCH s.championship c WHERE c.id = :championshipId AND s.id = :seasonId AND m.id = :matchId")
	Match findById(@Param("championshipId") UUID championshipId, @Param("seasonId") UUID seasonId, @Param("matchId") UUID matchId);

	Match findByTeamIdAAndTeamIdBAndArenaAndDate(UUID teamIdA, UUID teamIdB, String arena, LocalDateTime date);
}
