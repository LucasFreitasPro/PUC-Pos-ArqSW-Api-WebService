package com.grouptwo.soccer.transfers.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
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
import org.springframework.web.client.RestTemplate;

import com.grouptwo.soccer.transfers.lib.requests.EmailSendingRequest;
import com.grouptwo.soccer.transfers.lib.requests.PlayerUpdateRequest;
import com.grouptwo.soccer.transfers.lib.requests.TransferRequest;
import com.grouptwo.soccer.transfers.lib.responses.PlayerResponse;
import com.grouptwo.soccer.transfers.lib.responses.TeamResponse;
import com.grouptwo.soccer.transfers.models.Transfer;
import com.grouptwo.soccer.transfers.services.TransferService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/v1/transfers")
public class TransferController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final TransferService service;

	private final RestTemplate restTemplate;

	private final String TEAMS_API = "http://TEAMS/api/v1/teams";
	private final String EMAIL_SENDING_API = "http://EMAIL-SENDING/api/v1/email";

	public TransferController(TransferService service, RestTemplate restTemplate) {
		this.service = service;
		this.restTemplate = restTemplate;
	}

	@Operation(summary = "Transfer a player to another team")
	@ApiResponse(responseCode = "200", description = "Player successfully transferred", content = @Content)
	@ApiResponse(responseCode = "400", description = "Invalid payload", content = @Content)
	@ApiResponse(responseCode = "409", description = "Origin team does not match current team \t\n Destiny team does not exist \t\n Player does not exist", content = @Content)
	@PostMapping
	public ResponseEntity<Object> transferPlayer(@RequestBody @Valid TransferRequest transferRequest, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList()).toString().replace("[", "").replace("]", ""));
		}

		logger.info("new transfer {}", transferRequest);

		PlayerResponse playerResponse = null;
		try {
			playerResponse = restTemplate.getForObject(TEAMS_API + "/{teamName}/players/{playerName}", PlayerResponse.class, transferRequest.getFromTeam(), transferRequest.getPlayerName());
		} catch (HttpClientErrorException e) {
			logger.error("Error calling TEAMS API", e);
			return ResponseEntity.status(HttpStatus.valueOf(e.getRawStatusCode())).body(e.getMessage());
		}

		if (playerResponse != null && playerResponse.getName() != null) {
			TeamResponse teamResponseTo = null;
			TeamResponse teamResponseFrom = null;
			try {
				teamResponseTo = restTemplate.getForObject(TEAMS_API + "/{teamName}", TeamResponse.class, transferRequest.getToTeam());
			} catch (HttpClientErrorException e) {
				logger.error("Error calling TEAMS API", e);
				return ResponseEntity.status(HttpStatus.valueOf(e.getRawStatusCode())).body(e.getMessage());
			}

			try {
				teamResponseFrom = restTemplate.getForObject(TEAMS_API + "/{teamName}", TeamResponse.class, transferRequest.getFromTeam());
			} catch (HttpClientErrorException e) {
				logger.error("Error calling TEAMS API", e);
				return ResponseEntity.status(HttpStatus.valueOf(e.getRawStatusCode())).body(e.getMessage());
			}

			if (teamResponseTo != null && teamResponseTo.getName() != null) {
				if (!transferRequest.getFromTeam().equals(playerResponse.getTeamName())) {
					return ResponseEntity.status(HttpStatus.CONFLICT).body("Origin team does not match current team");
				}
				PlayerUpdateRequest playerUpdateRequest = new PlayerUpdateRequest();
				playerUpdateRequest.setBirth(playerResponse.getBirth());
				playerUpdateRequest.setName(playerResponse.getName());
				playerUpdateRequest.setTeamName(transferRequest.getToTeam());

				try {
					restTemplate.put(TEAMS_API + "/{teamName}/players/{playerName}", playerUpdateRequest, transferRequest.getFromTeam(), transferRequest.getPlayerName());
				} catch (HttpClientErrorException e) {
					logger.error("Error calling TEAMS API", e);
					return ResponseEntity.status(HttpStatus.valueOf(e.getRawStatusCode())).body(e.getMessage());
				}

				Transfer transfer = new Transfer();
				transfer.setPlayerName(transferRequest.getPlayerName());
				transfer.setToTeam(transferRequest.getToTeam());
				transfer.setFromTeam(transferRequest.getFromTeam());
				transfer.setTransferValue(transferRequest.getTransferValue());
				transfer.setTransferredAt(LocalDateTime.now(ZoneId.of("UTC")));
				this.service.save(transfer);

				logger.info("Player successfully transferred {}", transferRequest);

				sendEmailToOwner(playerResponse, teamResponseFrom, teamResponseTo, transferRequest);
				return ResponseEntity.status(HttpStatus.OK).body("Player successfully transferred");
			} else {
				return ResponseEntity.status(HttpStatus.CONFLICT).body("Destiny team does not exist");
			}
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Player does not exist");
		}
	}

	@Operation(summary = "Get all transfers from a team")
	@ApiResponse(responseCode = "200", description = "Player successfully transferred", content = @Content)
	@ApiResponse(responseCode = "404", description = "No transfer found", content = @Content)
	@GetMapping("/from-team/{teamName}")
	public ResponseEntity<List<Transfer>> getAllFromTeam(@PathVariable("teamName") String teamName) {
		List<Transfer> transfers = this.service.findByFromTeam(teamName);
		if (transfers != null && !transfers.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(transfers);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of());
		}
	}

	@Operation(summary = "Get all transfers to a team")
	@ApiResponse(responseCode = "200", description = "Player successfully transferred", content = @Content)
	@ApiResponse(responseCode = "404", description = "No transfers found", content = @Content)
	@GetMapping("/to-team/{teamName}")
	public ResponseEntity<List<Transfer>> getAllToTeam(@PathVariable("teamName") String teamName) {
		List<Transfer> transfers = this.service.findByToTeam(teamName);
		if (transfers != null && !transfers.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(transfers);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of());
		}
	}

	@Operation(summary = "Get all player transfers")
	@ApiResponse(responseCode = "200", description = "Player successfully transferred", content = @Content)
	@ApiResponse(responseCode = "404", description = "No transfers found", content = @Content)
	@GetMapping("/players/{playerName}")
	public ResponseEntity<Object> getAllPlayerTransfers(@PathVariable("playerName") String playerName) {
		List<Transfer> transfers = this.service.findByPlayerName(playerName);
		if (transfers != null && !transfers.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(transfers);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of());
		}
	}

	private void sendEmailToOwner(PlayerResponse playerResponse, TeamResponse teamResponseFrom, TeamResponse teamResponseTo, TransferRequest transferRequest) {
		EmailSendingRequest emailSendingRequest = new EmailSendingRequest();
		try {
			emailSendingRequest.setSubject(String.format("SOCCER API - O jogador %s foi transferido", playerResponse.getName()));
			emailSendingRequest.setToEmail("lucasfrpp@gmail.com");

			StringBuilder message = new StringBuilder();
			message.append("<html>");
			message.append("	<body style='margin: auto; width: 60%'>");
			message.append("		<h2>Transferência realizada com sucesso!</h2><br></br>");
			message.append("		<h3>Jogador ").append(playerResponse.getName()).append("</h3><br>");
			message.append("		<p>");
			message.append("			<strong>Do</strong> ").append(teamResponseFrom.getName()).append(" <strong>para</strong> ").append(teamResponseTo.getName()).append("<br><br>");
			message.append("			<strong>Valor da transação:</strong>: R$ ").append(transferRequest.getTransferValue().intValue()).append(",00");
			message.append("		</p>");
			message.append("	</body>");
			message.append("</html>");

			emailSendingRequest.setMessage(message.toString());
			restTemplate.postForObject(EMAIL_SENDING_API, emailSendingRequest, String.class);
			logger.info("E-mail sending request sent successfully. Payload {}", emailSendingRequest);
		} catch (HttpClientErrorException e) {
			logger.error("Failed to send e-mail. Payload {}", emailSendingRequest, e);
		}
	}
}
