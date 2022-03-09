package com.grouptwo.soccer.transfers.teams.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
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

import com.grouptwo.soccer.transfers.lib.requests.PlayerRegistrationRequest;
import com.grouptwo.soccer.transfers.lib.requests.PlayerUpdateRequest;
import com.grouptwo.soccer.transfers.lib.requests.PlayerUpdateTeamRequest;
import com.grouptwo.soccer.transfers.lib.responses.BadRequestResponse;
import com.grouptwo.soccer.transfers.lib.responses.ConflictResponse;
import com.grouptwo.soccer.transfers.lib.responses.PlayerRegistrationResponse;
import com.grouptwo.soccer.transfers.lib.responses.PlayerResponse;
import com.grouptwo.soccer.transfers.lib.responses.TeamResponse;
import com.grouptwo.soccer.transfers.teams.hateoas.assemblers.PlayerRegistrationResponseModelAssembler;
import com.grouptwo.soccer.transfers.teams.hateoas.assemblers.PlayerResponseModelAssembler;
import com.grouptwo.soccer.transfers.teams.models.Player;
import com.grouptwo.soccer.transfers.teams.models.Team;
import com.grouptwo.soccer.transfers.teams.services.PlayerService;
import com.grouptwo.soccer.transfers.teams.services.TeamService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(path = "/api/v1/teams/{teamName}/players")
public class PlayerController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final TeamService teamService;

	private final PlayerService playerService;

	private final PlayerResponseModelAssembler playerResponseModelAssembler;

	private final PlayerRegistrationResponseModelAssembler playerRegistrationResponseModelAssembler;

	public PlayerController(TeamService teamService, PlayerService playerService, PlayerResponseModelAssembler playerResponseModelAssembler,
			PlayerRegistrationResponseModelAssembler playerRegistrationResponseModelAssembler) {
		super();
		this.teamService = teamService;
		this.playerService = playerService;
		this.playerResponseModelAssembler = playerResponseModelAssembler;
		this.playerRegistrationResponseModelAssembler = playerRegistrationResponseModelAssembler;
	}

	@Operation(summary = "Register a new player in a team")
	@ApiResponse(responseCode = "201", description = "Player registered successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerRegistrationResponse.class)))
	@ApiResponse(responseCode = "400", description = "Invalid payload", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestResponse.class)))
	@ApiResponse(responseCode = "409", description = "Player already registered \t\n The provided team does not exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConflictResponse.class)))
	@PostMapping
	public ResponseEntity<Object> register(@PathVariable("teamName") String teamName, @RequestBody @Valid PlayerRegistrationRequest playerRegistrationRequest, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			final BadRequestResponse badRequestResponse = new BadRequestResponse();
			bindingResult.getFieldErrors().stream().forEach(e -> badRequestResponse.addError(e.getField(), e.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestResponse);
		}

		teamName = teamName.toUpperCase();

		logger.info("new player registration {}", playerRegistrationRequest);

		playerRegistrationRequest.setName(playerRegistrationRequest.getName().toUpperCase());
		playerRegistrationRequest.setCountry(playerRegistrationRequest.getCountry().toUpperCase());

		TeamResponse teamResponse = this.teamService.findByName(teamName);
		if (teamResponse != null) {
			PlayerResponse playerResponse = this.playerService.findByName(playerRegistrationRequest.getName());
			if (playerResponse != null) {
				logger.warn("Player already registered. Payload {}", playerRegistrationRequest);
				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body(new ConflictResponse<EntityModel<PlayerResponse>>("Player already registered", this.playerResponseModelAssembler.toModel(playerResponse)));
			} else {
				Player player = this.playerService.getConverter().fromRequestToEntity(playerRegistrationRequest);
				player.setTeam(this.teamService.getConverter().fromResponseToEntity(teamResponse));
				player = this.playerService.save(player);

				PlayerRegistrationResponse playerRegistrationResponse = new PlayerRegistrationResponse(teamName, player.getName());
				playerRegistrationResponse.setBirth(playerRegistrationRequest.getBirth());
				playerRegistrationResponse.setCountry(playerRegistrationRequest.getCountry());
				logger.info("Player registered successfully {}", playerRegistrationResponse);

				return ResponseEntity.status(HttpStatus.CREATED).body(playerRegistrationResponseModelAssembler.toModel(playerRegistrationResponse));
			}
		} else {
			logger.warn("The provided team does not exist. Team {}", teamName);
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ConflictResponse<TeamResponse>("The provided team does not exist"));
		}
	}

	@Operation(summary = "Get registered players from a team")
	@ApiResponse(responseCode = "200", description = "All registered players from a team", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerResponse.class)))
	@ApiResponse(responseCode = "404", description = "No players found", content = @Content)
	@GetMapping
	public ResponseEntity<CollectionModel<EntityModel<PlayerResponse>>> getAll(@PathVariable("teamName") String teamName) {
		logger.info("get all players from team: {}", teamName);

		List<PlayerResponse> players = this.playerService.findByTeamName(teamName.toUpperCase());

		logger.info("list of retrivered players {}", players);

		if (players != null && !players.isEmpty()) {
			players.stream().forEach(p -> p.setTeamName(teamName.toUpperCase()));

			List<EntityModel<PlayerResponse>> list = players.stream().map(playerResponseModelAssembler::toModel).collect(Collectors.toList());

			return ResponseEntity.status(HttpStatus.OK).body(CollectionModel.of(list, linkTo(methodOn(PlayerController.class).getAll(teamName)).withSelfRel()));
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

	@Operation(summary = "Get a player by its name and team")
	@ApiResponse(responseCode = "200", description = "A player from a team", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerResponse.class)))
	@ApiResponse(responseCode = "404", description = "Player not found", content = @Content)
	@GetMapping(path = "/{playerName}")
	public ResponseEntity<EntityModel<PlayerResponse>> getOne(@PathVariable("teamName") String teamName, @PathVariable("playerName") String playerName) {
		logger.info("get player {} from team {} ", playerName, teamName);

		Player player = this.playerService.getByTeamNameAndPlayerName(teamName.toUpperCase(), playerName);
		if (player != null) {
			PlayerResponse playerResponse = new PlayerResponse();
			BeanUtils.copyProperties(player, playerResponse);
			playerResponse.setTeamName(player.getTeam().getName());

			logger.info("retrivered player {}", playerResponse);
			return ResponseEntity.status(HttpStatus.OK).body(this.playerResponseModelAssembler.toModel(playerResponse));
		} else {
			logger.warn("Player not found. Team {} Player {}", teamName, playerName);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Operation(summary = "Update a player by its name and team")
	@ApiResponse(responseCode = "200", description = "Player updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerResponse.class)))
	@ApiResponse(responseCode = "400", description = "Invalid payload", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestResponse.class)))
	@ApiResponse(responseCode = "409", description = "The provided name is already in use by another player \t\n The provided player does not exist \t\n The provided team does not exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConflictResponse.class)))
	@PatchMapping(path = "/{playerName}")
	public ResponseEntity<Object> updateOne(@PathVariable("teamName") String teamName, @PathVariable("playerName") String playerName, @RequestBody @Valid PlayerUpdateRequest playerUpdateRequest,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			final BadRequestResponse badRequestResponse = new BadRequestResponse();
			bindingResult.getFieldErrors().stream().forEach(e -> badRequestResponse.addError(e.getField(), e.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestResponse);
		}

		teamName = teamName.toUpperCase();

		logger.info("updating player {} from team {}. Payload: ", playerName, teamName, playerUpdateRequest);

		playerUpdateRequest.setNewName(playerUpdateRequest.getNewName().toUpperCase());

		PlayerResponse playerByName = this.playerService.findByName(playerUpdateRequest.getNewName());
		if (playerByName != null && !playerByName.getTeamName().equals(teamName)) {
			logger.warn("The given name is already in use by another player. Payload {}", playerUpdateRequest);
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(new ConflictResponse<EntityModel<PlayerResponse>>("The provided name is already in use by another player", this.playerResponseModelAssembler.toModel(playerByName)));
		}

		TeamResponse teamResponse = this.teamService.findByName(teamName);
		if (teamResponse != null) {
			PlayerResponse playerResponse = this.playerService.findByName(playerName);
			if (playerResponse != null) {
				Player player = new Player();
				player.setName(playerUpdateRequest.getNewName());
				player.setBirth(playerResponse.getBirth());
				player.setCountry(playerResponse.getCountry());
				player.setTeam(new Team(teamResponse.getTeamId()));

				PlayerResponse pr = this.playerService.getConverter().fromEntityToResponse(this.playerService.save(player));
				logger.info("player updated successfully {}", pr);

				return ResponseEntity.status(HttpStatus.OK).body(this.playerResponseModelAssembler.toModel(pr));
			} else {
				logger.warn("The provided player does not exist. Payload {}", playerUpdateRequest);
				return ResponseEntity.status(HttpStatus.CONFLICT).body(new ConflictResponse<PlayerResponse>("The provided player does not exist"));
			}
		} else {
			logger.warn("The provided team does not exist. Payload {}", playerUpdateRequest);
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ConflictResponse<TeamResponse>("The provided team does not exist"));
		}
	}

	@Operation(summary = "Transfer a player to another team")
	@ApiResponse(responseCode = "200", description = "Player updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerResponse.class)))
	@ApiResponse(responseCode = "400", description = "Invalid payload", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestResponse.class)))
	@ApiResponse(responseCode = "409", description = "The provided player does not exist \t\n The provided team does not exist \t\n The provided new team does not exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConflictResponse.class)))
	@PatchMapping(path = "/{playerName}/transfer")
	public ResponseEntity<Object> updateTeam(@PathVariable("teamName") String teamName, @PathVariable("playerName") String playerName,
			@RequestBody @Valid PlayerUpdateTeamRequest playerUpdateTeamRequest, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			final BadRequestResponse badRequestResponse = new BadRequestResponse();
			bindingResult.getFieldErrors().stream().forEach(e -> badRequestResponse.addError(e.getField(), e.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestResponse);
		}

		teamName = teamName.toUpperCase();

		logger.info("updating player {} from team {}. Payload: ", playerName, teamName, playerUpdateTeamRequest);

		playerUpdateTeamRequest.setNewTeamName(playerUpdateTeamRequest.getNewTeamName().toUpperCase());

		TeamResponse teamResponse = this.teamService.findByName(teamName);
		if (teamResponse != null) {
			PlayerResponse playerResponse = this.playerService.findByName(playerName);
			if (playerResponse != null) {
				Player player = this.playerService.getConverter().fromResponseToEntity(playerResponse);
				TeamResponse newTeamResponse = this.teamService.findByName(playerUpdateTeamRequest.getNewTeamName());
				if (newTeamResponse != null) {
					player.setTeam(new Team(newTeamResponse.getTeamId()));
				} else {
					logger.warn("The provided new team does not exist. Payload {}", playerUpdateTeamRequest);
					return ResponseEntity.status(HttpStatus.CONFLICT).body(new ConflictResponse<TeamResponse>("The provided new team does not exist"));
				}

				PlayerResponse pr = this.playerService.getConverter().fromEntityToResponse(this.playerService.save(player));
				logger.info("player updated successfully {}", pr);

				return ResponseEntity.status(HttpStatus.OK).body(this.playerResponseModelAssembler.toModel(pr));
			} else {
				logger.warn("The provided player does not exist. Payload {}", playerUpdateTeamRequest);
				return ResponseEntity.status(HttpStatus.CONFLICT).body(new ConflictResponse<PlayerResponse>("The provided player does not exist"));
			}
		} else {
			logger.warn("The provided team does not exist. Payload {}", playerUpdateTeamRequest);
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ConflictResponse<TeamResponse>("The provided team does not exist"));
		}
	}

	@Operation(summary = "Delete a player by its name and team")
	@ApiResponse(responseCode = "204", description = "Player deleted successfully", content = @Content)
	@ApiResponse(responseCode = "409", description = "The provided player does not exist \t\n The provided team does not exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConflictResponse.class)))
	@DeleteMapping(path = "/{playerName}")
	public ResponseEntity<Object> deleteOne(@PathVariable("teamName") String teamName, @PathVariable("playerName") String playerName) {
		teamName = teamName.toUpperCase();

		logger.info("deleting player {} from team {} ", playerName, teamName);

		TeamResponse teamResponse = this.teamService.findByName(teamName);
		if (teamResponse != null) {
			PlayerResponse player = this.playerService.findByName(playerName);
			if (player != null) {
				this.playerService.deleteById(player.getPlayerId());

				logger.info("Player deleted successfully. team {} player {}", teamName, playerName);
				return ResponseEntity.noContent().build();
			} else {
				logger.warn("The provided player does not exist. Team {} Player {}", teamName, playerName);
				return ResponseEntity.status(HttpStatus.CONFLICT).body(new ConflictResponse<PlayerResponse>("The provided player does not exist"));
			}
		} else {
			logger.warn("The provided team does not exist. Team {} Player {}", teamName, playerName);
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ConflictResponse<TeamResponse>("The provided team does not exist"));
		}
	}
}
