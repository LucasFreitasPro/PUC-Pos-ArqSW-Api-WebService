package com.grouptwo.soccer.transfers.teams.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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
import com.grouptwo.soccer.transfers.lib.responses.ErrorOrConflictResponse;
import com.grouptwo.soccer.transfers.lib.responses.TeamRegistrationResponse;
import com.grouptwo.soccer.transfers.lib.responses.TeamResponse;
import com.grouptwo.soccer.transfers.teams.hateoas.assemblers.TeamResponseModelAssembler;
import com.grouptwo.soccer.transfers.teams.models.Team;
import com.grouptwo.soccer.transfers.teams.services.TeamService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/v1/teams")
public class TeamController {

	// TODO padronizar respostas

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final TeamService service;
	
	private final TeamResponseModelAssembler teamResponseModelAssembler;

	public TeamController(TeamService service, TeamResponseModelAssembler teamResponseModelAssembler) {
		this.service = service;
		this.teamResponseModelAssembler = teamResponseModelAssembler;
	}

	@Operation(summary = "Register a new team")
	@ApiResponse(responseCode = "201", description = "Team registered successfully", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = TeamRegistrationResponse.class)) })
	@ApiResponse(responseCode = "409", description = "Team is already registered", content = @Content)
	@ApiResponse(responseCode = "400", description = "Invalid payload", content = @Content)
	@PostMapping
	public ResponseEntity<Object> register(@RequestBody @Valid TeamRegistrationRequest teamRegistrationRequest, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorOrConflictResponse(allErrors.stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList()).toString().replace("[", "").replace("]", "")));
		}

		teamRegistrationRequest.setNewName(teamRegistrationRequest.getNewName().toUpperCase());
		teamRegistrationRequest.setState(teamRegistrationRequest.getState().toUpperCase());
		teamRegistrationRequest.setCountry(teamRegistrationRequest.getCountry().toUpperCase());

		logger.info("new team registration {}", teamRegistrationRequest);

		// TODO tratar exclusão lógica
		if (this.service.existsByName(teamRegistrationRequest.getNewName())) {
			logger.warn("Team is already registered {}", teamRegistrationRequest);
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorOrConflictResponse("Team is already registered"));
		} else {
			Team newTeam = new Team(Boolean.FALSE);
			BeanUtils.copyProperties(teamRegistrationRequest, newTeam);
			newTeam = this.service.save(newTeam);

			TeamRegistrationResponse registrationResponse = new TeamRegistrationResponse(newTeam.getName(), newTeam.getState(), newTeam.getCountry());
			logger.info("Team registered successfully {}", registrationResponse);
			return ResponseEntity.status(HttpStatus.CREATED).body(registrationResponse);
		}
	}

//	@PostMapping(path = "/list")
//	public ResponseEntity<String> registerList(@RequestBody List<TeamRegistrationRequest> teams, BindingResult bindingResult) {
//		for (TeamRegistrationRequest teamRegistrationRequest : teams) {
//			register(teamRegistrationRequest, bindingResult);
//		}
//
//		return ResponseEntity.status(HttpStatus.CREATED).body("All teams registered");
//	}

	@Operation(summary = "Get registered teams")
	@ApiResponse(responseCode = "200", description = "List of all registered teams", content = @Content)
	@ApiResponse(responseCode = "404", description = "Team not found", content = @Content)
	@GetMapping
	public ResponseEntity<CollectionModel<EntityModel<TeamResponse>>> getAll(@PageableDefault(page = 0, size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
		logger.info("get all teams");
		Page<TeamResponse> page = this.service.findAll(pageable);
		if (page != null && !page.isEmpty()) {
			logger.info("list of retrivered teams {}", page.getContent());
			List<EntityModel<TeamResponse>> teams = page.stream().map(teamResponseModelAssembler::toModel).collect(Collectors.toList());

			return ResponseEntity.status(HttpStatus.OK).body(CollectionModel.of(teams, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(getClass()).getAll(null)).withSelfRel()));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Operation(summary = "Get a team by its name")
	@ApiResponse(responseCode = "200", description = "Found the team", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = TeamResponse.class)) })
	@ApiResponse(responseCode = "404", description = "Team not found", content = @Content)
	@GetMapping(path = "/{teamName}")
	public ResponseEntity<EntityModel<TeamResponse>> getOne(@PathVariable("teamName") String teamName) {
		logger.info("get one team {}", teamName);

		TeamResponse teamResponse = this.service.findByName(teamName.toUpperCase());
		if (teamResponse != null) {
			logger.info("retrivered team {}", teamResponse);
			return ResponseEntity.status(HttpStatus.OK).body(teamResponseModelAssembler.toModel(teamResponse));
		} else {
			logger.warn("team not found {}", teamName);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(EntityModel.of(new TeamResponse()));
		}
	}

	@Operation(summary = "Update a team by its name")
	@ApiResponse(responseCode = "200", description = "Team updated successfully", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = TeamResponse.class)) })
	@ApiResponse(responseCode = "400", description = "Invalid payload", content = @Content)
	@ApiResponse(responseCode = "409", description = "The provided name is already in use on another team \t\n The provided team does not exist", content = @Content)
	@PatchMapping(path = "/{teamName}")
	public ResponseEntity<Object> updateOne(@PathVariable("teamName") String teamName, @RequestBody @Valid TeamUpdateRequest teamUpdateRequest, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorOrConflictResponse(bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList()).toString().replace("[", "").replace("]", "")));
		}

		logger.info("updating team {}. Payload {}", teamName, teamUpdateRequest);

		teamUpdateRequest.setNewName(teamUpdateRequest.getNewName().toUpperCase());

		TeamResponse teamResponse = this.service.findByName(teamName.toUpperCase());
		if (teamResponse != null) {
			TeamResponse teamByName = this.service.findByName(teamUpdateRequest.getNewName());
			if (teamByName != null && !teamByName.getName().equals(teamName.toUpperCase())) {
				logger.warn("The given name is already in use on another team. Payload {} ", teamUpdateRequest);
				return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorOrConflictResponse("The provided name is already in use on another team"));
			}

			teamResponse.setName(teamUpdateRequest.getNewName());
			teamResponse.setState(teamUpdateRequest.getState());
			this.service.save(teamResponse);
			logger.info("Team updated successfully {} ", teamUpdateRequest);
			return ResponseEntity.status(HttpStatus.OK).body(teamResponse);
		} else {
			logger.warn("The provided team does not exist {} ", teamUpdateRequest);
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorOrConflictResponse("The provided team does not exist"));
		}
	}

	@Operation(summary = "Delete a team by its name")
	@ApiResponse(responseCode = "204", description = "Team deleted successfully", content = @Content)
	@ApiResponse(responseCode = "409", description = "The provided team does not exist", content = @Content)
	@DeleteMapping(path = "/{teamName}")
	public ResponseEntity<Object> deleteOne(@PathVariable("teamName") String teamName) {
		logger.info("deleting team {}", teamName);

		TeamResponse teamResponse = this.service.findByName(teamName.toUpperCase());
		if (teamResponse != null) {
			teamResponse.setDeleted(true);
			this.service.save(teamResponse);
			logger.info("Team deleted successfully. Name {}", teamName);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Team deleted successfully");
		} else {
			logger.warn("The provided team does not exist. Name {}", teamName);
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorOrConflictResponse("The provided team does not exist"));
		}
	}
}
