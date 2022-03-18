package com.grouptwo.soccer.championships.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grouptwo.soccer.championships.converters.MatchConverter;
import com.grouptwo.soccer.championships.models.Match;
import com.grouptwo.soccer.championships.repositories.MatchRepository;
import com.grouptwo.soccer.transfers.lib.responses.MatchResponse;

@Service
public class MatchService {

	private final MatchRepository matchRepository;

	private final MatchConverter matchConverter;

	public MatchService(MatchRepository matchRepository, MatchConverter matchConverter) {
		this.matchRepository = matchRepository;
		this.matchConverter = matchConverter;
	}

	public MatchConverter getMatchConverter() {
		return matchConverter;
	}

	public List<MatchResponse> findAll(UUID championshipId, UUID seasonId) {
		List<Match> all = this.matchRepository.findAll(championshipId, seasonId);
		return all != null && !all.isEmpty() ? all.stream().map(this.matchConverter::fromEntityToResponse).collect(Collectors.toList()) : null;
	}

	public MatchResponse findById(UUID championshipId, UUID seasonId, UUID matchId) {
		Match match = this.matchRepository.findById(championshipId, seasonId, matchId);
		return match != null ? this.matchConverter.fromEntityToResponse(match) : null ;
	}

	@Transactional
	public Match save(Match entity) {
		return this.matchRepository.save(entity);
	}

	public MatchResponse findByTeamIdAAndTeamIdBAndArenaAndDate(UUID aTeamId, UUID bTeamId, String arena, LocalDateTime date) {
		Match match = this.matchRepository.findByTeamIdAAndTeamIdBAndArenaAndDate(aTeamId, bTeamId, arena, date);
		return match == null ? null : this.matchConverter.fromEntityToResponse(match);
	}
}
