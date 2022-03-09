package com.grouptwo.soccer.transfers.teams.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.grouptwo.soccer.transfers.teams.models.Player;

public interface PlayerRepository extends JpaRepository<Player, UUID> {

	public final String ALL_PLAYERS = "SELECT p.* FROM player p INNER JOIN team t ON p.team_id = t.team_id WHERE NOT t.deleted AND t.name = :teamName";
	public final String ONE_PLAYER = ALL_PLAYERS + " AND p.name = :playerName";

	@Query(value = ALL_PLAYERS, nativeQuery = true)
	public List<Player> findByTeamName(@Param("teamName") String teamName);

	@Query(value = ONE_PLAYER, nativeQuery = true)
	public Player getByTeamNameAndPlayerName(@Param("teamName") String teamName, @Param("playerName") String playerName);

	@Query("SELECT p FROM Player p WHERE p.name = :name")
	public Player findByName(@Param("name") String name);
}
