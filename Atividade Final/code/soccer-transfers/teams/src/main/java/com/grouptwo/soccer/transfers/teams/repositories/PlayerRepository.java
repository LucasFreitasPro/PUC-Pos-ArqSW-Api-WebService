package com.grouptwo.soccer.transfers.teams.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.grouptwo.soccer.transfers.teams.models.Player;

public interface PlayerRepository extends JpaRepository<Player, UUID> {

	public final String ALL_PLAYERS = "SELECT p.* FROM player p INNER JOIN team t ON p.team_id = t.team_id WHERE NOT t.deleted AND t.team_id = :teamId";
	public final String ONE_PLAYER = ALL_PLAYERS + " AND p.player_id = :playerId";

	@Query(value = ALL_PLAYERS, nativeQuery = true)
	public List<Player> findByTeamId(@Param("teamId") UUID teamId);

	@Query(value = ONE_PLAYER, nativeQuery = true)
	public Player getByTeamIdAndPlayerId(@Param("teamId") UUID teamId, @Param("playerId") UUID playerId);

	public Optional<Player> findByName(String name);
}
