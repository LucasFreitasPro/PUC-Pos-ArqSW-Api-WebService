package com.grouptwo.soccer.transfers.teams.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
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

import com.grouptwo.soccer.transfers.lib.requests.PlayerRegistrationRequest;
import com.grouptwo.soccer.transfers.lib.requests.PlayerUpdateRequest;
import com.grouptwo.soccer.transfers.lib.requests.PlayerUpdateTeamRequest;
import com.grouptwo.soccer.transfers.lib.responses.BadRequestResponse;
import com.grouptwo.soccer.transfers.lib.responses.ConflictResponse;
import com.grouptwo.soccer.transfers.lib.responses.PlayerRegistrationResponse;
import com.grouptwo.soccer.transfers.lib.responses.PlayerResponse;
import com.grouptwo.soccer.transfers.lib.responses.TeamResponse;
import com.grouptwo.soccer.transfers.lib.utils.CommonUtil;
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
@RequestMapping(path = "/api/v1/teams/{teamId}/players")
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
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_CREATED, description = "Player registered successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerRegistrationResponse.class)))
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_BAD_REQUEST, description = "Invalid payload", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestResponse.class)))
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_CONFLICT, description = "Player already registered \t\n The provided team does not exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConflictResponse.class)))
	@PostMapping
	public ResponseEntity<Object> register(@PathVariable("teamId") UUID teamId, @RequestBody @Valid PlayerRegistrationRequest playerRegistrationRequest, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			final BadRequestResponse badRequestResponse = new BadRequestResponse();
			bindingResult.getFieldErrors().stream().forEach(e -> badRequestResponse.addError(e.getField(), e.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestResponse);
		}

		logger.info("new player registration {}", playerRegistrationRequest);

		playerRegistrationRequest.setName(playerRegistrationRequest.getName().toUpperCase());
		playerRegistrationRequest.setCountry(playerRegistrationRequest.getCountry().toUpperCase());

		TeamResponse teamResponse = this.teamService.findById(teamId);
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

				PlayerRegistrationResponse playerRegistrationResponse = new PlayerRegistrationResponse(teamId, player.getPlayerId());
				playerRegistrationResponse.setName(player.getName());
				playerRegistrationResponse.setBirth(playerRegistrationRequest.getBirth());
				playerRegistrationResponse.setCountry(playerRegistrationRequest.getCountry());
				logger.info("Player registered successfully {}", playerRegistrationResponse);

				return ResponseEntity.status(HttpStatus.CREATED).body(playerRegistrationResponseModelAssembler.toModel(playerRegistrationResponse));
			}
		} else {
			logger.warn("The provided team does not exist. Team {}", teamId);
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ConflictResponse<TeamResponse>("The provided team does not exist"));
		}
	}

	@Operation(summary = "Get registered players from a team")
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_OK, description = "All registered players from a team", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerResponse.class)))
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_NOT_FOUND, description = "No players found", content = @Content)
	@GetMapping
	public ResponseEntity<CollectionModel<EntityModel<PlayerResponse>>> getAll(@PathVariable("teamId") UUID teamId) {
		logger.info("get all players from team: {}", teamId);

		List<PlayerResponse> players = this.playerService.findByTeamId(teamId);

		logger.info("list of retrivered players {}", players);

		if (players != null && !players.isEmpty()) {
			List<EntityModel<PlayerResponse>> list = players.stream().map(playerResponseModelAssembler::toModel).collect(Collectors.toList());

			return ResponseEntity.status(HttpStatus.OK).body(CollectionModel.of(list, linkTo(methodOn(PlayerController.class).getAll(teamId)).withSelfRel()));
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

	@Operation(summary = "Get a player by its id and team id")
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_OK, description = "A player from a team", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerResponse.class)))
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_NOT_FOUND, description = "Player not found", content = @Content)
	@GetMapping(path = "/{playerId}")
	public ResponseEntity<EntityModel<PlayerResponse>> getOne(@PathVariable("teamId") UUID teamId, @PathVariable("playerId") UUID playerId) {
		logger.info("get player {} from team {} ", playerId, teamId);

		Player player = this.playerService.getByTeamIdAndPlayerId(teamId, playerId);
		if (player != null) {
			PlayerResponse playerResponse = new PlayerResponse();
			BeanUtils.copyProperties(player, playerResponse);
			playerResponse.setTeamId(player.getTeam().getTeamId());

			logger.info("retrivered player {}", playerResponse);
			return ResponseEntity.status(HttpStatus.OK).body(this.playerResponseModelAssembler.toModel(playerResponse));
		} else {
			logger.warn("Player not found. Team {} Player {}", teamId, playerId);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Operation(summary = "Update a player by its id and team id")
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_OK, description = "Player updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerResponse.class)))
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_BAD_REQUEST, description = "Invalid payload", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestResponse.class)))
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_CONFLICT, description = "The provided name is already in use by another player \t\n The provided player does not exist \t\n The provided team does not exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConflictResponse.class)))
	@PatchMapping(path = "/{playerId}")
	public ResponseEntity<Object> updateOne(@PathVariable("teamId") UUID teamId, @PathVariable("playerId") UUID playerId, @RequestBody @Valid PlayerUpdateRequest playerUpdateRequest,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			final BadRequestResponse badRequestResponse = new BadRequestResponse();
			bindingResult.getFieldErrors().stream().forEach(e -> badRequestResponse.addError(e.getField(), e.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestResponse);
		}

		logger.info("updating player {} from team {}. Payload: ", playerId, teamId, playerUpdateRequest);

		playerUpdateRequest.setNewName(playerUpdateRequest.getNewName().toUpperCase());

		PlayerResponse playerByName = this.playerService.findByName(playerUpdateRequest.getNewName());
		if (playerByName != null && !playerByName.getTeamId().equals(teamId)) {
			logger.warn("The given name is already in use by another player. Payload {}", playerUpdateRequest);
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(new ConflictResponse<EntityModel<PlayerResponse>>("The provided name is already in use by another player", this.playerResponseModelAssembler.toModel(playerByName)));
		}

		TeamResponse teamResponse = this.teamService.findById(teamId);
		if (teamResponse != null) {
			PlayerResponse playerResponse = this.playerService.findById(playerId);
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
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_OK, description = "Player updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerResponse.class)))
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_BAD_REQUEST, description = "Invalid payload", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestResponse.class)))
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_CONFLICT, description = "The provided player does not exist \t\n The provided team does not exist \t\n The provided new team does not exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConflictResponse.class)))
	@PatchMapping(path = "/{playerId}/transfer")
	public ResponseEntity<Object> updateTeam(@PathVariable("teamId") UUID teamId, @PathVariable("playerId") UUID playerId,
			@RequestBody @Valid PlayerUpdateTeamRequest playerUpdateTeamRequest, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			final BadRequestResponse badRequestResponse = new BadRequestResponse();
			bindingResult.getFieldErrors().stream().forEach(e -> badRequestResponse.addError(e.getField(), e.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestResponse);
		}

		logger.info("updating player {} from team {}. Payload: ", playerId, teamId, playerUpdateTeamRequest);

		TeamResponse teamResponse = this.teamService.findById(teamId);
		if (teamResponse != null) {
			PlayerResponse playerResponse = this.playerService.findById(playerId);
			if (playerResponse != null) {
				Player player = this.playerService.getConverter().fromResponseToEntity(playerResponse);
				TeamResponse newTeamResponse = this.teamService.findById(playerUpdateTeamRequest.getNewTeamId());
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

	@Operation(summary = "Delete a player by its id and team id")
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_NO_CONTENT, description = "Player deleted successfully", content = @Content)
	@DeleteMapping(path = "/{playerId}")
	public ResponseEntity<Object> deleteOne(@PathVariable("teamId") UUID teamId, @PathVariable("playerId") UUID playerId) {
		logger.info("deleting player {} from team {} ", playerId, teamId);

		TeamResponse teamResponse = this.teamService.findById(teamId);
		if (teamResponse != null) {
			PlayerResponse player = this.playerService.findById(playerId);
			if (player != null) {
				this.playerService.deleteById(player.getPlayerId());

				logger.info("Player deleted successfully. team {} player {}", teamId, playerId);
			} else {
				logger.warn("The provided player does not exist. Team {} Player {}", teamId, playerId);
			}
		} else {
			logger.warn("The provided team does not exist. Team {} Player {}", teamId, playerId);
		}
		return ResponseEntity.noContent().build();
	}
}
