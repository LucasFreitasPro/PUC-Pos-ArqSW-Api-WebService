package com.grouptwo.soccer.championships.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.grouptwo.soccer.championships.models.Season;

public interface SeasonRepository extends JpaRepository<Season, UUID> {

	public Season findByEndedAtIsNull();

	public Season findByYear(Short year);

	@Query("SELECT s FROM Season s INNER JOIN FETCH s.championship c WHERE c.id = :championshipId")
	public List<Season> findAll(@Param("championshipId") UUID championshipId);

	@Query("SELECT s FROM Season s INNER JOIN FETCH s.championship c WHERE c.id = :championshipId AND s.id = :seasonId")
	public Season findById(@Param("championshipId") UUID championshipId, @Param("seasonId") UUID seasonId);
}
