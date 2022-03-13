package com.grouptwo.soccer.transfers.teams.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.CollectionModel;
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

import com.grouptwo.soccer.transfers.lib.requests.TeamRegistrationRequest;
import com.grouptwo.soccer.transfers.lib.requests.TeamUpdateRequest;
import com.grouptwo.soccer.transfers.lib.responses.BadRequestResponse;
import com.grouptwo.soccer.transfers.lib.responses.ConflictResponse;
import com.grouptwo.soccer.transfers.lib.responses.PlayerResponse;
import com.grouptwo.soccer.transfers.lib.responses.TeamRegistrationResponse;
import com.grouptwo.soccer.transfers.lib.responses.TeamResponse;
import com.grouptwo.soccer.transfers.lib.utils.CommonUtil;
import com.grouptwo.soccer.transfers.teams.hateoas.assemblers.PlayerResponseModelAssembler;
import com.grouptwo.soccer.transfers.teams.hateoas.assemblers.TeamRegistrationResponseModelAssembler;
import com.grouptwo.soccer.transfers.teams.hateoas.assemblers.TeamResponseModelAssembler;
import com.grouptwo.soccer.transfers.teams.hateoas.assemblers.TeamUpdateRequestModelAssembler;
import com.grouptwo.soccer.transfers.teams.models.Team;
import com.grouptwo.soccer.transfers.teams.services.TeamService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/v1/teams")
public class TeamController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final TeamService service;

	private final TeamResponseModelAssembler teamResponseModelAssembler;

	private final TeamRegistrationResponseModelAssembler teamRegistrationResponseModelAssembler;

	private final PlayerResponseModelAssembler playerResponseModelAssembler;
	
	private final TeamUpdateRequestModelAssembler teamUpdateRequestModelAssembler;

	public TeamController(TeamService service, TeamResponseModelAssembler teamResponseModelAssembler, PlayerResponseModelAssembler playerResponseModelAssembler,
			TeamRegistrationResponseModelAssembler teamRegistrationResponseModelAssembler, TeamUpdateRequestModelAssembler teamUpdateRequestModelAssembler) {
		this.service = service;
		this.teamResponseModelAssembler = teamResponseModelAssembler;
		this.teamRegistrationResponseModelAssembler = teamRegistrationResponseModelAssembler;
		this.playerResponseModelAssembler = playerResponseModelAssembler;
		this.teamUpdateRequestModelAssembler = teamUpdateRequestModelAssembler;
	}

	@Operation(summary = "Register a new team")
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_CREATED, description = "Team registered successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TeamRegistrationResponse.class)))
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_CONFLICT, description = "Team is already registered", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConflictResponse.class)))
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_BAD_REQUEST, description = "Invalid payload", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestResponse.class)))
	@PostMapping
	public ResponseEntity<Object> register(@RequestBody @Valid TeamRegistrationRequest teamRegistrationRequest, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			final BadRequestResponse badRequestResponse = new BadRequestResponse();
			bindingResult.getFieldErrors().stream().forEach(e -> badRequestResponse.addError(e.getField(), e.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestResponse);
		}

		teamRegistrationRequest.setName(teamRegistrationRequest.getName().toUpperCase());
		teamRegistrationRequest.setState(teamRegistrationRequest.getState().toUpperCase());
		teamRegistrationRequest.setCountry(teamRegistrationRequest.getCountry().toUpperCase());

		logger.info("new team registration {}", teamRegistrationRequest);

		Optional<Team> team = this.service.getByName(teamRegistrationRequest.getName());
		if (team.isPresent()) {
			if (team.get().getDeleted()) {
				team.get().setDeleted(Boolean.FALSE);
				this.service.save(team.get());
				logger.info("Team updated to not deleted {}", team);
				TeamRegistrationResponse registrationResponse = new TeamRegistrationResponse(team.get().getTeamId(), team.get().getName(), team.get().getState(), team.get().getCountry());
				return ResponseEntity.status(HttpStatus.CREATED).body(teamRegistrationResponseModelAssembler.toModel(registrationResponse));
			} else {
				logger.warn("Team is already registered {}", teamRegistrationRequest);
				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body(new ConflictResponse<EntityModel<TeamResponse>>("Team is already registered", this.teamResponseModelAssembler.toModel(this.service.getConverter().fromEntityToResponse(team.get()))));
			}
		} else {
			Team newTeam = new Team(Boolean.FALSE);
			BeanUtils.copyProperties(teamRegistrationRequest, newTeam);
			newTeam = this.service.save(newTeam);

			TeamRegistrationResponse registrationResponse = new TeamRegistrationResponse(newTeam.getTeamId(), newTeam.getName(), newTeam.getState(), newTeam.getCountry());
			logger.info("Team registered successfully {}", registrationResponse);
			return ResponseEntity.status(HttpStatus.CREATED).body(teamRegistrationResponseModelAssembler.toModel(registrationResponse));
		}
	}

	@Operation(summary = "Get all registered teams")
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_OK, description = "List of all registered teams", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TeamResponse.class)))
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_NOT_FOUND, description = "Team not found", content = @Content)
	@GetMapping
	public ResponseEntity<CollectionModel<EntityModel<TeamResponse>>> getAll() {
		logger.info("get all teams");

		List<TeamResponse> all = this.service.findAll();
		if (all != null && !all.isEmpty()) {
			logger.info("list of retrivered teams {}", all.size());

			all.stream().forEach(t -> {
				if (!t.getPlayers().isEmpty()) {
					List<EntityModel<PlayerResponse>> players = t.getPlayers().stream().map(playerResponseModelAssembler::toModel).collect(Collectors.toList());
					t.setPlayersCollectionModel(CollectionModel.of(players, linkTo(methodOn(PlayerController.class).getAll(t.getTeamId())).withSelfRel()));
				}
			});

			List<EntityModel<TeamResponse>> teams = all.stream().map(teamResponseModelAssembler::toModel).collect(Collectors.toList());

			return ResponseEntity.status(HttpStatus.OK).body(CollectionModel.of(teams, linkTo(methodOn(TeamController.class).getAll()).withSelfRel()));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Operation(summary = "Get a team by its id")
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_OK, description = "Found the team", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TeamResponse.class)))
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_NOT_FOUND, description = "Team not found", content = @Content)
	@GetMapping(path = "/{teamId}")
	public ResponseEntity<EntityModel<TeamResponse>> getOne(@PathVariable("teamId") UUID teamId) {
		logger.info("get one team {}", teamId);

		TeamResponse teamResponse = this.service.findById(teamId);
		if (teamResponse != null) {
			logger.info("retrivered team {}", teamResponse);
			if (teamResponse.getPlayers() != null && !teamResponse.getPlayers().isEmpty()) {
				List<EntityModel<PlayerResponse>> players = teamResponse.getPlayers().stream().map(playerResponseModelAssembler::toModel).collect(Collectors.toList());
				teamResponse.setPlayersCollectionModel(CollectionModel.of(players, linkTo(methodOn(PlayerController.class).getAll(teamId)).withSelfRel()));
			}
			return ResponseEntity.status(HttpStatus.OK).body(teamResponseModelAssembler.toModel(teamResponse));
		} else {
			logger.warn("team not found {}", teamId);
			return ResponseEntity.notFound().build();
		}
	}

	@Operation(summary = "Update a team by its id")
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_OK, description = "Team updated successfully", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = TeamResponse.class)) })
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_BAD_REQUEST, description = "Invalid payload", content = @Content)
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_CONFLICT, description = "The provided name is already in use on another team \t\n The provided team does not exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConflictResponse.class)))
	@PatchMapping(path = "/{teamId}")
	public ResponseEntity<Object> updateOne(@PathVariable("teamId") UUID teamId, @RequestBody @Valid TeamUpdateRequest teamUpdateRequest, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			final BadRequestResponse badRequestResponse = new BadRequestResponse();
			bindingResult.getFieldErrors().stream().forEach(e -> badRequestResponse.addError(e.getField(), e.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestResponse);
		}

		logger.info("updating team {}. Payload {}", teamId, teamUpdateRequest);

		teamUpdateRequest.setNewName(teamUpdateRequest.getNewName().toUpperCase());

		TeamResponse teamResponse = this.service.findById(teamId);
		if (teamResponse != null) {
			TeamResponse teamByName = this.service.findByName(teamUpdateRequest.getNewName());
			if (teamByName != null && !teamByName.getTeamId().equals(teamId)) {
				logger.warn("The given name is already in use on another team. Payload {} ", teamUpdateRequest);
				return ResponseEntity.status(HttpStatus.CONFLICT).body(new ConflictResponse<EntityModel<TeamResponse>>("The provided name is already in use on another team", this.teamResponseModelAssembler.toModel(teamByName)));
			}

			teamResponse.setName(teamUpdateRequest.getNewName());
			teamResponse.setState(teamUpdateRequest.getNewState());
			this.service.save(teamResponse);
			logger.info("Team updated successfully {} ", teamUpdateRequest);
			return ResponseEntity.status(HttpStatus.OK).body(this.teamResponseModelAssembler.toModel(teamResponse));
		} else {
			logger.warn("The provided team does not exist {} ", teamUpdateRequest);
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ConflictResponse<EntityModel<TeamUpdateRequest>>("The provided team does not exist", this.teamUpdateRequestModelAssembler.toModel(teamUpdateRequest)));
		}
	}

	@Operation(summary = "Delete a team by its id")
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_NO_CONTENT, description = "Team deleted successfully", content = @Content)
	@DeleteMapping(path = "/{teamId}")
	public ResponseEntity<Object> deleteOne(@PathVariable("teamId") UUID teamId) {
		logger.info("deleting team {}", teamId);

		TeamResponse teamResponse = this.service.findById(teamId);
		if (teamResponse != null) {
			teamResponse.setDeleted(true);
			this.service.save(teamResponse);
			logger.info("Team deleted successfully. ID {}", teamId);
		} else {
			logger.warn("The provided team does not exist. ID {}", teamId);
		}
		return ResponseEntity.noContent().build();
	}
}
