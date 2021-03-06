package com.grouptwo.soccer.transfers.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.grouptwo.soccer.transfers.assemblers.TransferResponseModelAssembler;
import com.grouptwo.soccer.transfers.lib.requests.PlayerUpdateTeamRequest;
import com.grouptwo.soccer.transfers.lib.requests.TransferRequest;
import com.grouptwo.soccer.transfers.lib.responses.BadRequestResponse;
import com.grouptwo.soccer.transfers.lib.responses.ConflictResponse;
import com.grouptwo.soccer.transfers.lib.responses.PlayerResponse;
import com.grouptwo.soccer.transfers.lib.responses.TeamResponse;
import com.grouptwo.soccer.transfers.lib.responses.TransferResponse;
import com.grouptwo.soccer.transfers.lib.utils.CommonUtil;
import com.grouptwo.soccer.transfers.models.Transfer;
import com.grouptwo.soccer.transfers.services.TransferService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/v1/transfers")
public class TransferController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final String TEAMS_API = "http://TEAMS/api/v1/teams";

	private final TransferService service;

	private final RestTemplate restTemplate;
	
	private final TransferResponseModelAssembler transferResponseModelAssembler;

	public TransferController(TransferService service, RestTemplate restTemplate, TransferResponseModelAssembler transferResponseModelAssembler) {
		this.service = service;
		this.restTemplate = restTemplate;
		this.transferResponseModelAssembler = transferResponseModelAssembler;
	}

	@Operation(summary = "Transfer a player to another team")
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_CREATED, description = "Player successfully transferred", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransferResponse.class)))
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_BAD_REQUEST, description = "Invalid payload", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestResponse.class)))
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_CONFLICT, description = "Origin team does not exist \t\n Destiny team does not exist \t\n Player does not exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConflictResponse.class)))
	@PostMapping
	public ResponseEntity<Object> transferPlayer(@RequestBody @Valid TransferRequest transferRequest, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			final BadRequestResponse badRequestResponse = new BadRequestResponse();
			bindingResult.getFieldErrors().stream().forEach(e -> badRequestResponse.addError(e.getField(), e.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestResponse);
		}

		logger.info("new transfer {}", transferRequest);

		PlayerResponse playerResponse = null;
		try {
			playerResponse = restTemplate.getForObject(TEAMS_API + "/{teamId}/players/{playerId}", PlayerResponse.class, transferRequest.getOriginTeamId(), transferRequest.getPlayerId());
		} catch (HttpClientErrorException e) {
			logger.error("Error calling TEAMS API", e);
			return ResponseEntity.status(HttpStatus.valueOf(e.getRawStatusCode())).body(e.getMessage());
		}

		if (playerResponse != null && playerResponse.getName() != null) {
			TeamResponse teamResponseTo = null;
			TeamResponse teamResponseFrom = null;
			try {
				teamResponseTo = restTemplate.getForObject(TEAMS_API + "/{teamId}", TeamResponse.class, transferRequest.getDestinyTeamId());
			} catch (HttpClientErrorException e) {
				logger.error("Error calling TEAMS API", e);
				return ResponseEntity.status(HttpStatus.valueOf(e.getRawStatusCode())).body(e.getMessage());
			}

			try {
				teamResponseFrom = restTemplate.getForObject(TEAMS_API + "/{teamId}", TeamResponse.class, transferRequest.getOriginTeamId());
				if (teamResponseFrom == null) {
					return ResponseEntity.status(HttpStatus.CONFLICT).body(new ConflictResponse<TeamResponse>("Origin team does not exist"));
				}
			} catch (HttpClientErrorException e) {
				logger.error("Error calling TEAMS API", e);
				return ResponseEntity.status(HttpStatus.valueOf(e.getRawStatusCode())).body(e.getMessage());
			}

			if (teamResponseTo != null && teamResponseTo.getName() != null) {
				PlayerUpdateTeamRequest playerUpdateTeamRequest = new PlayerUpdateTeamRequest(transferRequest.getDestinyTeamId());

				try {
					restTemplate.patchForObject(TEAMS_API + "/{teamId}/players/{playerId}/transfer", playerUpdateTeamRequest, PlayerResponse.class, transferRequest.getOriginTeamId(), transferRequest.getPlayerId());
				} catch (HttpClientErrorException e) {
					logger.error("Error calling TEAMS API", e);
					return ResponseEntity.status(HttpStatus.valueOf(e.getRawStatusCode())).body(e.getMessage());
				} catch (ResourceAccessException e) {
					logger.error("Error calling TEAMS API", e);
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
				}

				Transfer transfer = this.service.getConverter().fromRequestToEntity(transferRequest);
				transfer.setTransferredAt(LocalDateTime.now(ZoneId.of("UTC")));
				this.service.save(transfer);

				logger.info("Player successfully transferred {}", transferRequest);

				return ResponseEntity.status(HttpStatus.CREATED).body(transferResponseModelAssembler.toModel(this.service.getConverter().fromEntityToResponse(transfer)));
			} else {
				return ResponseEntity.status(HttpStatus.CONFLICT).body(new ConflictResponse<TeamResponse>("Destiny team does not exist"));
			}
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ConflictResponse<PlayerResponse>("Player does not exist"));
		}
	}

	@Operation(summary = "Get all transfers from a team")
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_OK, description = "Transfers found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransferResponse.class)))
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_NOT_FOUND, description = "No transfer found", content = @Content)
	@GetMapping("/from-team/{teamId}")
	public ResponseEntity<List<TransferResponse>> getAllFromTeam(@PathVariable("teamId") UUID teamId) {
		List<Transfer> transfers = this.service.findByOriginTeamId(teamId);
		if (transfers != null && !transfers.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(transfers.stream().map(this.service.getConverter()::fromEntityToResponse).collect(Collectors.toList()));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@Operation(summary = "Get all transfers to a team")
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_OK, description = "Transfers found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransferResponse.class)))
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_NOT_FOUND, description = "No transfers found", content = @Content)
	@GetMapping("/to-team/{teamId}")
	public ResponseEntity<List<TransferResponse>> getAllToTeam(@PathVariable("teamId") UUID teamId) {
		List<Transfer> transfers = this.service.findByDestinyTeamId(teamId);
		if (transfers != null && !transfers.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(transfers.stream().map(this.service.getConverter()::fromEntityToResponse).collect(Collectors.toList()));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@Operation(summary = "Get all player transfers")
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_OK, description = "Transfers found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransferResponse.class)))
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_NOT_FOUND, description = "No transfers found", content = @Content)
	@GetMapping("/from-player/{playerId}")
	public ResponseEntity<List<TransferResponse>> getAllPlayerTransfers(@PathVariable("playerId") UUID playerId) {
		List<Transfer> transfers = this.service.findByPlayerId(playerId);
		if (transfers != null && !transfers.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(transfers.stream().map(this.service.getConverter()::fromEntityToResponse).collect(Collectors.toList()));
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
