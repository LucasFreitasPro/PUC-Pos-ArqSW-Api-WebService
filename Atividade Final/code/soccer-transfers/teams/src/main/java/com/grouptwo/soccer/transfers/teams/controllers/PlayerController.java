package com.grouptwo.soccer.transfers.teams.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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

import com.grouptwo.soccer.transfers.lib.requests.PlayerRegistrationRequest;
import com.grouptwo.soccer.transfers.lib.requests.PlayerUpdateRequest;
import com.grouptwo.soccer.transfers.lib.responses.ErrorOrConflictResponse;
import com.grouptwo.soccer.transfers.lib.responses.PlayerRegistrationResponse;
import com.grouptwo.soccer.transfers.lib.responses.PlayerResponse;
import com.grouptwo.soccer.transfers.lib.responses.TeamResponse;
import com.grouptwo.soccer.transfers.teams.models.Player;
import com.grouptwo.soccer.transfers.teams.services.PlayerService;
import com.grouptwo.soccer.transfers.teams.services.TeamService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(path = "/api/v1/teams/{teamName}/players")
public class PlayerController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private TeamService teamService;

	private PlayerService playerService;

	public PlayerController(TeamService teamService, PlayerService playerService) {
		super();
		this.teamService = teamService;
		this.playerService = playerService;
	}

	@Operation(summary = "Register a new player in a team")
	@PostMapping
	public ResponseEntity<Object> register(@PathVariable("teamName") String teamName, @RequestBody @Valid PlayerRegistrationRequest playerRegistrationRequest, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(new ErrorOrConflictResponse(bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList()).toString().replace("[", "").replace("]", "")));
		}

		teamName = teamName.toUpperCase();

		logger.info("new player registration {}", playerRegistrationRequest);

		playerRegistrationRequest.setName(playerRegistrationRequest.getName().toUpperCase());
		playerRegistrationRequest.setCountry(playerRegistrationRequest.getCountry().toUpperCase());

		TeamResponse teamResponse = this.teamService.findByName(teamName);
		if (teamResponse != null) {
			if (this.playerService.existsByName(playerRegistrationRequest.getName())) {
				logger.warn("Player already registered. Payload {}", playerRegistrationRequest);
				// TODO retornar o jogador cadastrado
				return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorOrConflictResponse("Player already registered"));
			} else {
				Player player = this.playerService.getConverter().fromRequestToEntity(playerRegistrationRequest);
				player.setTeam(this.teamService.getConverter().fromResponseToEntity(teamResponse));
				player = this.playerService.save(player);

				PlayerRegistrationResponse playerRegistrationResponse = new PlayerRegistrationResponse(teamName, player.getName());
				logger.info("Player registered successfully {}", playerRegistrationResponse);
				return ResponseEntity.status(HttpStatus.CREATED).body(playerRegistrationResponse);
			}
		} else {
			logger.warn("The provided team does not exist. Team {}", teamName);
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorOrConflictResponse("The provided team does not exist"));
		}
	}

	@Operation(summary = "Get registered players from a team")
	@GetMapping
	public ResponseEntity<List<PlayerResponse>> getAll(@PathVariable("teamName") String teamName) {
		logger.info("get all players from team: {}", teamName);

		List<PlayerResponse> players = this.playerService.findByTeamName(teamName.toUpperCase());

		logger.info("list of retrivered players {}", players);

		if (players != null && !players.isEmpty()) {
			players.stream().forEach(p -> p.setTeamName(teamName.toUpperCase()));
			return ResponseEntity.status(HttpStatus.OK).body(players);
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(players);
	}

	@Operation(summary = "Get a player by its name and team")
	@GetMapping(path = "/{playerName}")
	public ResponseEntity<Object> getOne(@PathVariable("teamName") String teamName, @PathVariable("playerName") String playerName) {
		logger.info("get player {} from team {} ", playerName, teamName);

		Player player = this.playerService.getByTeamNameAndPlayerName(teamName.toUpperCase(), playerName);
		if (player != null) {
			PlayerResponse playerResponse = new PlayerResponse();
			BeanUtils.copyProperties(player, playerResponse);
			playerResponse.setTeamName(player.getTeam().getName());

			logger.info("retrivered player {}", playerResponse);
			return ResponseEntity.status(HttpStatus.OK).body(playerResponse);
		} else {
			logger.warn("Player not found. Team {} Player {}", teamName, playerName);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorOrConflictResponse("Player not found"));
		}
	}

	@Operation(summary = "Update a player by its name and team")
	@PatchMapping(path = "/{playerName}")
	public ResponseEntity<Object> updateOne(@PathVariable("teamName") String teamName, @PathVariable("playerName") String playerName, @RequestBody @Valid PlayerUpdateRequest playerUpdateRequest,
			BindingResult bindingResult) {
		teamName = teamName.toUpperCase();

		if (bindingResult.hasErrors()) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(new ErrorOrConflictResponse(bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList()).toString().replace("[", "").replace("]", "")));
		}

		logger.info("updating player {} from team {}. Payload: ", playerName, teamName, playerUpdateRequest);

		playerUpdateRequest.setName(playerUpdateRequest.getName().toUpperCase());

		Player playerByName = this.playerService.findByName(playerUpdateRequest.getName());
		if (playerByName != null && !playerByName.getTeam().getName().equals(teamName)) {
			logger.warn("The given name is already in use by another player. Payload {}", playerUpdateRequest);
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorOrConflictResponse("The provided name is already in use by another player"));
		}

		if (playerUpdateRequest.getTeamName() == null) {
			playerUpdateRequest.setTeamName(teamName);
		}

		TeamResponse teamResponse = this.teamService.findByName(playerUpdateRequest.getTeamName());
		if (teamResponse != null) {
			Player player = this.playerService.findByName(playerName);
			if (player != null) {
				player.setName(playerUpdateRequest.getName());
				player.setBirth(playerUpdateRequest.getBirth());
				player.setTeam(this.teamService.getConverter().fromResponseToEntity(teamResponse));

				PlayerResponse pr = this.playerService.getConverter().fromEntityToResponse(this.playerService.save(player));
				logger.info("player updated successfully {}", pr);

				return ResponseEntity.status(HttpStatus.OK).body(pr);
			} else {
				logger.warn("The provided player does not exist. Payload {}", playerUpdateRequest);
				return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorOrConflictResponse("The provided player does not exist"));
			}
		} else {
			logger.warn("The provided team does not exist. Payload {}", playerUpdateRequest);
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorOrConflictResponse("The provided team does not exist"));
		}
	}

	@Operation(summary = "Delete a player by its name and team")
	@DeleteMapping(path = "/{playerName}")
	public ResponseEntity<Object> deleteOne(@PathVariable("teamName") String teamName, @PathVariable("playerName") String playerName) {
		teamName = teamName.toUpperCase();

		logger.info("deleting player {} from team {} ", playerName, teamName);

		TeamResponse teamResponse = this.teamService.findByName(teamName);
		if (teamResponse != null) {
			Player player = this.playerService.findByName(playerName);
			if (player != null) {
				this.playerService.delete(player);

				logger.info("Player deleted successfully. team {} player {}", teamName, playerName);
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Player deleted successfully");
			} else {
				logger.warn("The provided player does not exist. Team {} Player {}", teamName, playerName);
				return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorOrConflictResponse("The provided player does not exist"));
			}
		} else {
			logger.warn("The provided team does not exist. Team {} Player {}", teamName, playerName);
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorOrConflictResponse("The provided team does not exist"));
		}
	}
}
