package com.grouptwo.soccer.transfers.teams.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grouptwo.soccer.transfers.lib.responses.TeamResponse;
import com.grouptwo.soccer.transfers.teams.converters.TeamConverter;
import com.grouptwo.soccer.transfers.teams.models.Team;
import com.grouptwo.soccer.transfers.teams.repositories.TeamRepository;

@Service
public class TeamService {

	private TeamRepository repository;

	private TeamConverter converter;

	public TeamService(TeamRepository repository, TeamConverter converter) {
		this.repository = repository;
		this.converter = converter;
	}

	public TeamConverter getConverter() {
		return converter;
	}

	@Transactional
	public Team save(Team newTeam) {
		return this.repository.save(newTeam);
	}

	public Team save(TeamResponse teamResponse) {
		return save(this.converter.fromResponseToEntity(teamResponse));
	}

	public List<TeamResponse> findAll() {
		List<Team> all = this.repository.findAll();
		if (all != null && !all.isEmpty()) {
			return all.stream().map(t -> this.converter.fromEntityToResponse(t)).collect(Collectors.toList());
		}
		return null;
	}

	public TeamResponse findByName(String teamName) {
		Optional<Team> optional = this.repository.findByName(teamName);
		return optional.isPresent() ? this.converter.fromEntityToResponse(optional.get()) : null;
	}

	public Optional<Team> getByName(String teamName) {
		return this.repository.getByName(teamName);
	}
}
