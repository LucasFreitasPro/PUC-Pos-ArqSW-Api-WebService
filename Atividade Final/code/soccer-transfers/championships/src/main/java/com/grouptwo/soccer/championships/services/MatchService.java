package com.grouptwo.soccer.championships.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.grouptwo.soccer.championships.converters.MatchConverter;
import com.grouptwo.soccer.championships.models.Match;
import com.grouptwo.soccer.championships.repositories.MatchRepository;

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

	public List<Match> findAll(UUID championshipId, UUID seasonId) {
		return this.matchRepository.findAll(championshipId, seasonId);
	}

	public Match findById(UUID championshipId, UUID seasonId, UUID matchId) {
		return this.matchRepository.findById(championshipId, seasonId, matchId);
	}
}
