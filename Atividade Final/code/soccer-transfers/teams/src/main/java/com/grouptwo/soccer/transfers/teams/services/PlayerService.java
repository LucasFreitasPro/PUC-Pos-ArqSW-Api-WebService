package com.grouptwo.soccer.transfers.teams.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grouptwo.soccer.transfers.lib.responses.PlayerResponse;
import com.grouptwo.soccer.transfers.teams.converters.PlayerConverter;
import com.grouptwo.soccer.transfers.teams.models.Player;
import com.grouptwo.soccer.transfers.teams.repositories.PlayerRepository;

@Service
public class PlayerService {

	private PlayerRepository repository;

	private PlayerConverter converter;

	public PlayerService(PlayerRepository repository, PlayerConverter converter) {
		this.repository = repository;
		this.converter = converter;
	}

	public PlayerConverter getConverter() {
		return converter;
	}

	@Transactional
	public Player save(Player newPlayer) {
		return this.repository.save(newPlayer);
	}

	public void deleteById(UUID playerId) {
		this.repository.deleteById(playerId);
	}

	public List<PlayerResponse> findByTeamName(String teamName) {
		return this.repository.findByTeamName(teamName).stream().map(p -> this.converter.fromEntityToResponse(p)).collect(Collectors.toList());
	}

	public Player getByTeamNameAndPlayerName(String teamName, String playerName) {
		return this.repository.getByTeamNameAndPlayerName(teamName, playerName);
	}

	public PlayerResponse findByName(String name) {
		Player player = this.repository.findByName(name);
		return player == null ? null : this.converter.fromEntityToResponse(player);
	}
}
