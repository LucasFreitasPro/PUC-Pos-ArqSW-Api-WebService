package com.grouptwo.soccer.championships.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grouptwo.soccer.championships.models.Match;
import com.grouptwo.soccer.championships.services.MatchService;
import com.grouptwo.soccer.championships.services.SeasonService;
import com.grouptwo.soccer.transfers.lib.requests.MatchRegisteringRequest;
import com.grouptwo.soccer.transfers.lib.responses.SeasonResponse;

@RestController
@RequestMapping(path = "/api/v1/championships/{championshipId}/seasons/{seasonId}/matches")
public class MatchController {

	private final MatchService matchService;

	private final SeasonService seasonService;

	public MatchController(MatchService matchService, SeasonService seasonService) {
		this.matchService = matchService;
		this.seasonService = seasonService;
	}

	@GetMapping
	public ResponseEntity<List<Match>> getAll(@PathVariable("championshipId") UUID championshipId, @PathVariable("seasonId") UUID seasonId) {
		return ResponseEntity.ok().body(this.matchService.findAll(championshipId, seasonId));
	}

	@GetMapping(path = "/{matchId}")
	public ResponseEntity<Match> getOne(@PathVariable("championshipId") UUID championshipId, @PathVariable("seasonId") UUID seasonId, @PathVariable("matchId") UUID matchId) {
		return ResponseEntity.ok().body(this.matchService.findById(championshipId, seasonId, matchId));
	}

	@PostMapping
	public void register(@PathVariable("championshipId") UUID championshipId, @PathVariable("seasonId") UUID seasonId, @RequestBody @Valid MatchRegisteringRequest matchRegisteringRequest) {
		SeasonResponse response = this.seasonService.findById(championshipId, seasonId);
		if (response != null) {
			Match entity = this.matchService.getMatchConverter().fromRequestToEntity(matchRegisteringRequest);
			entity.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
			entity.setSeason(this.seasonService.getSeasonConverter().fromResponseToEntity(response));
		}
	}
}
