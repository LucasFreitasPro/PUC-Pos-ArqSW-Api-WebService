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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grouptwo.soccer.championships.hateoas.assemblers.SeasonResponseModelAssembler;
import com.grouptwo.soccer.championships.models.Championship;
import com.grouptwo.soccer.championships.models.Season;
import com.grouptwo.soccer.championships.services.ChampionshipService;
import com.grouptwo.soccer.championships.services.SeasonService;
import com.grouptwo.soccer.transfers.lib.requests.SeasonRegisteringRequest;
import com.grouptwo.soccer.transfers.lib.responses.BadRequestResponse;
import com.grouptwo.soccer.transfers.lib.responses.ChampionshipResponse;
import com.grouptwo.soccer.transfers.lib.responses.ConflictResponse;
import com.grouptwo.soccer.transfers.lib.responses.SeasonResponse;
import com.grouptwo.soccer.transfers.lib.utils.CommonUtil;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(path = "/api/v1/championships/{championshipId}/seasons")
public class SeasonController {

	private final SeasonService seasonService;

	private final ChampionshipService championshipService;

	private final SeasonResponseModelAssembler seasonResponseModelAssembler;

	public SeasonController(SeasonService seasonService, ChampionshipService championshipService, SeasonResponseModelAssembler seasonResponseModelAssembler) {
		this.seasonService = seasonService;
		this.championshipService = championshipService;
		this.seasonResponseModelAssembler = seasonResponseModelAssembler;
	}

	@GetMapping
	public ResponseEntity<List<SeasonResponse>> getAll(@PathVariable("championshipId") UUID championshipId) {
		return ResponseEntity.ok().body(this.seasonService.findAll(championshipId));
	}

	@GetMapping(path = "/{seasonId}")
	public ResponseEntity<SeasonResponse> getOne(@PathVariable("championshipId") UUID championshipId, @PathVariable("seasonId") UUID seasonId) {
		return ResponseEntity.ok().body(this.seasonService.findById(championshipId, seasonId));
	}

	@PostMapping(path = "/start")
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_CREATED, description = "Season started successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SeasonResponse.class)))
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_CONFLICT, description = "Season has already started \t\n Season is ended \t\n Can't start a season until all seasons are closed \t\n The provided championship does not exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConflictResponse.class)))
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_BAD_REQUEST, description = "Invalid payload", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestResponse.class)))
	public ResponseEntity<Object> register(@PathVariable("championshipId") UUID championshipId, @RequestBody @Valid SeasonRegisteringRequest seasonRegisteringRequest, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			final BadRequestResponse badRequestResponse = new BadRequestResponse();
			bindingResult.getFieldErrors().stream().forEach(e -> badRequestResponse.addError(e.getField(), e.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestResponse);
		}

		ChampionshipResponse championshipResponse = championshipService.findById(championshipId);
		if (championshipResponse != null) {
			Season season = this.seasonService.findByYear(seasonRegisteringRequest.getYear());
			if (season != null) {
				if (season.getEndedAt() == null) {
					return ResponseEntity.status(HttpStatus.CONFLICT).body(new ConflictResponse<EntityModel<SeasonResponse>>("Season has already started",
							this.seasonResponseModelAssembler.toModel(this.seasonService.getSeasonConverter().fromEntityToResponse(season))));
				} else {
					return ResponseEntity.status(HttpStatus.CONFLICT).body(new ConflictResponse<EntityModel<SeasonResponse>>("Season is ended",
							this.seasonResponseModelAssembler.toModel(this.seasonService.getSeasonConverter().fromEntityToResponse(season))));
				}
			}
			season = this.seasonService.findByEndedAtIsNull();
			if (season != null) {
				season.setAddLinkToEndPath(Boolean.TRUE);
				return ResponseEntity.status(HttpStatus.CONFLICT).body(new ConflictResponse<EntityModel<SeasonResponse>>("Can't start a season until all seasons are closed",
						this.seasonResponseModelAssembler.toModel(this.seasonService.getSeasonConverter().fromEntityToResponse(season))));
			}
			Season entity = this.seasonService.getSeasonConverter().fromRequestToEntity(seasonRegisteringRequest);
			entity.setChampionship(new Championship(championshipResponse.getId()));
			entity = this.seasonService.save(entity);
			return ResponseEntity.status(HttpStatus.CREATED).body(this.seasonResponseModelAssembler.toModel(this.seasonService.getSeasonConverter().fromEntityToResponse(entity)));
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("The provided championship does not exist");
		}
	}

	@PatchMapping(path = "/{seasonId}/end")
	public ResponseEntity<Object> end(@PathVariable("championshipId") UUID championshipId, @PathVariable("seasonId") UUID seasonId) {
		SeasonResponse response = this.seasonService.findById(championshipId, seasonId);
		if (response != null) {
			response.setEndedAt(LocalDateTime.now(ZoneId.of("UTC")));
			this.seasonService.save(response);
		}
		return ResponseEntity.status(HttpStatus.OK).body("");
	}
}
