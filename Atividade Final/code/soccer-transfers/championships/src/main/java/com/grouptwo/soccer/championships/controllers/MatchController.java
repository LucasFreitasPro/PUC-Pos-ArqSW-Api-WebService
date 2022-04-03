package com.grouptwo.soccer.championships.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grouptwo.soccer.championships.hateoas.assemblers.MatchResponseModelAssembler;
import com.grouptwo.soccer.championships.models.Match;
import com.grouptwo.soccer.championships.services.ChampionshipService;
import com.grouptwo.soccer.championships.services.MatchService;
import com.grouptwo.soccer.championships.services.SeasonService;
import com.grouptwo.soccer.transfers.lib.requests.MatchRegisteringRequest;
import com.grouptwo.soccer.transfers.lib.requests.MatchUpdatingRequest;
import com.grouptwo.soccer.transfers.lib.responses.BadRequestResponse;
import com.grouptwo.soccer.transfers.lib.responses.ChampionshipResponse;
import com.grouptwo.soccer.transfers.lib.responses.ConflictResponse;
import com.grouptwo.soccer.transfers.lib.responses.MatchResponse;
import com.grouptwo.soccer.transfers.lib.responses.SeasonResponse;
import com.grouptwo.soccer.transfers.lib.utils.CommonUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(path = "/api/v1/championships/{championshipId}/seasons/{seasonId}/matches")
public class MatchController {

	private final MatchService matchService;

	private final SeasonService seasonService;

	private final ChampionshipService championshipService;

	private final MatchResponseModelAssembler matchResponseModelAssembler;

	public MatchController(MatchService matchService, SeasonService seasonService, ChampionshipService championshipService, MatchResponseModelAssembler matchResponseModelAssembler) {
		this.matchService = matchService;
		this.seasonService = seasonService;
		this.championshipService = championshipService;
		this.matchResponseModelAssembler = matchResponseModelAssembler;
	}

	@Operation(summary = "Get all registered matches")
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_OK, description = "List of all registered matches", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MatchResponse.class)))
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_NOT_FOUND, description = "No matches found", content = @Content)
	@GetMapping
	public ResponseEntity<List<MatchResponse>> getAll(@PathVariable("championshipId") UUID championshipId, @PathVariable("seasonId") UUID seasonId) {
		return ResponseEntity.ok().body(this.matchService.findAll(championshipId, seasonId));
	}

	@Operation(summary = "Get a match by its id")
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_OK, description = "Found the match", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MatchResponse.class)))
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_NOT_FOUND, description = "Match not found", content = @Content)
	@GetMapping(path = "/{matchId}")
	public ResponseEntity<MatchResponse> getOne(@PathVariable("championshipId") UUID championshipId, @PathVariable("seasonId") UUID seasonId, @PathVariable("matchId") UUID matchId) {
		return ResponseEntity.ok().body(this.matchService.findById(championshipId, seasonId, matchId));
	}

	@Operation(summary = "Register a new match")
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_CREATED, description = "Match registered successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MatchResponse.class)))
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_CONFLICT, description = "Match is already registered \t\n The provided championship does not exist \t\n The provided season does not exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConflictResponse.class)))
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_BAD_REQUEST, description = "Invalid payload", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestResponse.class)))
	@PostMapping
	public ResponseEntity<Object> register(@PathVariable("championshipId") UUID championshipId, @PathVariable("seasonId") UUID seasonId,
			@RequestBody @Valid MatchRegisteringRequest matchRegisteringRequest, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			final BadRequestResponse badRequestResponse = new BadRequestResponse();
			bindingResult.getFieldErrors().stream().forEach(e -> badRequestResponse.addError(e.getField(), e.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestResponse);
		}

		ChampionshipResponse championshipResponse = this.championshipService.findById(championshipId);
		if (championshipResponse != null) {
			SeasonResponse seasonResponse = this.seasonService.findById(championshipId, seasonId);
			if (seasonResponse != null) {
				MatchResponse matchResponse = this.matchService.findByTeamIdAAndTeamIdBAndArenaAndDate(matchRegisteringRequest.getTeamIdA(), matchRegisteringRequest.getTeamIdB(),
						matchRegisteringRequest.getArena(), matchRegisteringRequest.getDate());
				if (matchResponse == null) {
					Match entity = this.matchService.getMatchConverter().fromRequestToEntity(matchRegisteringRequest);
					entity.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
					entity.setSeason(this.seasonService.getSeasonConverter().fromResponseToEntity(seasonResponse));
					entity = this.matchService.save(entity);

					this.matchResponseModelAssembler.setChampionshipId(championshipId);
					this.matchResponseModelAssembler.setSeasonId(seasonId);
					return ResponseEntity.status(HttpStatus.CREATED).body(this.matchResponseModelAssembler.toModel(this.matchService.getMatchConverter().fromEntityToResponse(entity)));
				} else {
					this.matchResponseModelAssembler.setChampionshipId(championshipId);
					this.matchResponseModelAssembler.setSeasonId(seasonId);
					return ResponseEntity.status(HttpStatus.CONFLICT)
							.body(new ConflictResponse<EntityModel<MatchResponse>>("Match is already registered", this.matchResponseModelAssembler.toModel(matchResponse)));
				}
			} else {
				return ResponseEntity.status(HttpStatus.CONFLICT).body(new ConflictResponse<MatchResponse>("The provided season does not exist"));
			}
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ConflictResponse<MatchResponse>("The provided championship does not exist"));
		}
	}

	@Operation(summary = "Update a match by its id")
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_OK, description = "Match updated successfully", content = @Content)
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_CONFLICT, description = "The provided match id does not exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConflictResponse.class)))
	@PatchMapping(path = "/{matchId}")
	public ResponseEntity<Object> updateOne(@PathVariable("championshipId") UUID championshipId, @PathVariable("seasonId") UUID seasonId, @PathVariable("matchId") UUID matchId,
			@RequestBody @Valid MatchUpdatingRequest matchUpdatingRequest, BindingResult bindingResult) {
		MatchResponse matchResponse = this.matchService.findById(championshipId, seasonId, matchId);
		if (matchResponse != null) {

		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ConflictResponse<MatchResponse>("The provided match id does not exist"));
		}

		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Delete a match by its id")
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_NO_CONTENT, description = "Match deleted successfully", content = @Content)
	@DeleteMapping(path = "/{matchId}")
	public ResponseEntity<Object> deleteOne(@PathVariable("championshipId") UUID championshipId, @PathVariable("seasonId") UUID seasonId, @PathVariable("matchId") UUID matchId) {
		MatchResponse matchResponse = this.matchService.findById(championshipId, seasonId, matchId);
		if (matchResponse != null) {
			this.matchService.deleteById(matchId);
		}
		return ResponseEntity.noContent().build();
	}
}
