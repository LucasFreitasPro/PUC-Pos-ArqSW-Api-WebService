package com.grouptwo.soccer.championships.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.grouptwo.soccer.championships.models.Event;
import com.grouptwo.soccer.championships.models.enums.EventType;

public interface EventRepository extends JpaRepository<Event, UUID> {

	public final String SELECT_ALL = "SELECT e FROM Event e INNER JOIN FETCH e.match m INNER JOIN FETCH m.season s INNER JOIN FETCH s.championship c INNER JOIN FETCH e.substitutionEvent se WHERE c.id = :championshipId AND s.id = :seasonId AND m.id = :matchId";
	public final String SELECT_ALL_BY_NAME = SELECT_ALL + " AND e.eventType = :eventType";
	public final String SELECT_ALL_BY_NAME_AND_PLAYER_ID = SELECT_ALL_BY_NAME + " AND e.playerId = :playerId";

	@Query(SELECT_ALL)
	List<Event> findAll(@Param("championshipId") UUID championshipId, @Param("seasonId") UUID seasonId, @Param("matchId") UUID matchId);

	@Query(SELECT_ALL_BY_NAME)
	List<Event> findAllByEventType(@Param("championshipId") UUID championshipId, @Param("seasonId") UUID seasonId, @Param("matchId") UUID matchId, @Param("eventType") EventType eventType);

	@Query(SELECT_ALL_BY_NAME_AND_PLAYER_ID)
	List<Event> findAllByEventTypeAndPlayerId(@Param("championshipId") UUID championshipId, @Param("seasonId") UUID seasonId, @Param("matchId") UUID matchId, @Param("eventType") EventType eventType,
			@Param("playerId") UUID playerId);
}
