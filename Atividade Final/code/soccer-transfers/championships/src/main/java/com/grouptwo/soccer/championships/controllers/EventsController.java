package com.grouptwo.soccer.championships.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grouptwo.soccer.championships.hateoas.assemblers.EventResponseModelAssembler;
import com.grouptwo.soccer.championships.models.enums.EventType;
import com.grouptwo.soccer.championships.models.enums.Half;
import com.grouptwo.soccer.championships.services.EventService;
import com.grouptwo.soccer.championships.services.MatchService;
import com.grouptwo.soccer.transfers.lib.dtos.EventDTO;
import com.grouptwo.soccer.transfers.lib.dtos.SubstitutionEventDTO;
import com.grouptwo.soccer.transfers.lib.requests.EventRegisteringRequest;
import com.grouptwo.soccer.transfers.lib.responses.BadRequestResponse;
import com.grouptwo.soccer.transfers.lib.responses.ConflictResponse;
import com.grouptwo.soccer.transfers.lib.responses.EventResponse;
import com.grouptwo.soccer.transfers.lib.responses.MatchResponse;
import com.grouptwo.soccer.transfers.lib.utils.CommonUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(path = "/api/v1/championships/{championshipId}/seasons/{seasonId}/matches/{matchId}/events")
public class EventsController {

	private final MatchService matchService;

	private final EventService eventService;

	private final EventResponseModelAssembler eventResponseModelAssembler;

	private final RabbitTemplate rabbitTemplate;

	private final FanoutExchange fanoutExchange;

	public EventsController(MatchService matchService, EventService eventService, EventResponseModelAssembler eventResponseModelAssembler, RabbitTemplate rabbitTemplate,
			FanoutExchange fanoutExchange) {
		this.matchService = matchService;
		this.eventService = eventService;
		this.eventResponseModelAssembler = eventResponseModelAssembler;
		this.rabbitTemplate = rabbitTemplate;
		this.fanoutExchange = fanoutExchange;
	}

	@Operation(summary = "Get all registered events")
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_OK, description = "List of all registered events", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponse.class)))
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_NOT_FOUND, description = "No events found", content = @Content)
	@GetMapping
	public ResponseEntity<Object> getAll(@PathVariable("championshipId") UUID championshipId, @PathVariable("seasonId") UUID seasonId, @PathVariable("matchId") UUID matchId) {
		List<EventResponse> all = this.eventService.findAll(championshipId, seasonId, matchId);
		if (all != null && !all.isEmpty()) {
			List<EntityModel<EventResponse>> collect = all.stream().map(this.eventResponseModelAssembler::toModel).collect(Collectors.toList());
			return ResponseEntity.status(HttpStatus.OK).body(CollectionModel.of(collect));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@Operation(summary = "Get all registered events by its event name")
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_OK, description = "List of all registered events", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponse.class)))
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_NOT_FOUND, description = "No events found", content = @Content)
	@GetMapping(path = "/{eventName}")
	public ResponseEntity<Object> getAllByEventType(@PathVariable("championshipId") UUID championshipId, @PathVariable("seasonId") UUID seasonId, @PathVariable("matchId") UUID matchId,
			@PathVariable("eventName") String eventName) {
		EventType et = EventType.fromDesc(eventName);
		if (et == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The provided event name does not exist");
		}

		List<EventResponse> all = this.eventService.findAllByEventType(championshipId, seasonId, matchId, et);
		if (all != null && !all.isEmpty()) {
			List<EntityModel<EventResponse>> collect = all.stream().map(this.eventResponseModelAssembler::toModel).collect(Collectors.toList());
			return ResponseEntity.status(HttpStatus.OK).body(CollectionModel.of(collect));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@Operation(summary = "Register the start match event")
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_CREATED, description = "EventDTO registered successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponse.class)))
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_CONFLICT, description = "The provided match does not exist \t\n Match has already started \t\n Match has already ended", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConflictResponse.class)))
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_BAD_REQUEST, description = "Invalid payload", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestResponse.class)))
	@PostMapping(path = "/start-match")
	public ResponseEntity<Object> startMatch(@PathVariable("championshipId") UUID championshipId, @PathVariable("seasonId") UUID seasonId, @PathVariable("matchId") UUID matchId,
			@RequestBody @Valid EventRegisteringRequest eventRegisteringRequest, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			final BadRequestResponse badRequestResponse = new BadRequestResponse();
			bindingResult.getFieldErrors().stream().forEach(e -> badRequestResponse.addError(e.getField(), e.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestResponse);
		}

		Half half = Half.fromDesc(eventRegisteringRequest.getHalf());
		if (half == null) {
			final BadRequestResponse badRequestResponse = new BadRequestResponse();
			badRequestResponse.addError("half", "The provided half does not exist. Possible values are " + Half.toListDesc().toString());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestResponse);
		}

		MatchResponse matchResponse = this.matchService.findById(championshipId, seasonId, matchId);
		if (matchResponse != null) {
			List<EventResponse> all = this.eventService.findAllByEventType(championshipId, seasonId, matchId, EventType.START_MATCH);
			if (all != null && !all.isEmpty()) {
				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body(new ConflictResponse<EntityModel<EventResponse>>("Match has already started", this.eventResponseModelAssembler.toModel(all.get(0))));
			} else {
				all = this.eventService.findAllByEventType(championshipId, seasonId, matchId, EventType.END_MATCH);
				if (all != null && !all.isEmpty()) {
					return ResponseEntity.status(HttpStatus.CONFLICT)
							.body(new ConflictResponse<EntityModel<EventResponse>>("Match has already ended", this.eventResponseModelAssembler.toModel(all.get(0))));
				} else {
					EventDTO event = new EventDTO();
					event.setEventName(EventType.START_MATCH.getDesc());
					event.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
					event.setMatchId(matchResponse.getId());
					event.setHalfName(half.getDesc());
					event.setTimeInHalf(eventRegisteringRequest.getTimeInHalf());

					convertAndSend(event);

					return ResponseEntity.status(HttpStatus.ACCEPTED).build();
				}
			}
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ConflictResponse<EventResponse>("The provided match does not exist"));
		}
	}

	@Operation(summary = "Register the end match event")
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_CREATED, description = "EventDTO registered successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponse.class)))
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_CONFLICT, description = "The provided match does not exist \t\n Match must be started first \t\n Match has already ended", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConflictResponse.class)))
	@PostMapping(path = "/end-match")
	public ResponseEntity<Object> endMatch(@PathVariable("championshipId") UUID championshipId, @PathVariable("seasonId") UUID seasonId, @PathVariable("matchId") UUID matchId) {
		MatchResponse matchResponse = this.matchService.findById(championshipId, seasonId, matchId);
		if (matchResponse != null) {
			List<EventResponse> all = this.eventService.findAllByEventType(championshipId, seasonId, matchId, EventType.START_MATCH);
			if (all == null) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body(new ConflictResponse<EventResponse>("Match must be started first"));
			} else {
				all = this.eventService.findAllByEventType(championshipId, seasonId, matchId, EventType.END_MATCH);
				if (all != null && !all.isEmpty()) {
					return ResponseEntity.status(HttpStatus.CONFLICT)
							.body(new ConflictResponse<EntityModel<EventResponse>>("Match has already ended", this.eventResponseModelAssembler.toModel(all.get(0))));
				} else {
					EventDTO event = new EventDTO();
					event.setEventName(EventType.END_MATCH.getDesc());
					event.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
					event.setMatchId(matchResponse.getId());

					convertAndSend(event);

					return ResponseEntity.status(HttpStatus.ACCEPTED).build();
				}
			}
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ConflictResponse<EventResponse>("The provided match does not exist"));
		}
	}

	@Operation(summary = "Register the goal event in a match")
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_CREATED, description = "EventDTO registered successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponse.class)))
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_CONFLICT, description = "The provided match does not exist \t\n Match has already ended \t\n Match must be started first", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConflictResponse.class)))
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_BAD_REQUEST, description = "Invalid payload", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestResponse.class)))
	@PostMapping(path = "/goal")
	public ResponseEntity<Object> goal(@PathVariable("championshipId") UUID championshipId, @PathVariable("seasonId") UUID seasonId, @PathVariable("matchId") UUID matchId,
			@RequestBody @Valid EventRegisteringRequest eventRegisteringRequest, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			final BadRequestResponse badRequestResponse = new BadRequestResponse();
			bindingResult.getFieldErrors().stream().forEach(e -> badRequestResponse.addError(e.getField(), e.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestResponse);
		}

		if (eventRegisteringRequest.getPlayerId() == null) {
			final BadRequestResponse badRequestResponse = new BadRequestResponse();
			badRequestResponse.addError("player-id", "Must not be null");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestResponse);
		}

		Half half = Half.fromDesc(eventRegisteringRequest.getHalf());
		if (half == null) {
			final BadRequestResponse badRequestResponse = new BadRequestResponse();
			badRequestResponse.addError("half", "The provided half does not exist. Possible values are " + Half.toListDesc().toString());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestResponse);
		}

		MatchResponse matchResponse = this.matchService.findById(championshipId, seasonId, matchId);
		if (matchResponse != null) {
			List<EventResponse> all = this.eventService.findAllByEventType(championshipId, seasonId, matchId, EventType.END_MATCH);
			if (all != null) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body(new ConflictResponse<EventResponse>("Match has already ended"));
			} else {
				all = this.eventService.findAllByEventType(championshipId, seasonId, matchId, EventType.START_MATCH);
				if (all == null) {
					return ResponseEntity.status(HttpStatus.CONFLICT).body(new ConflictResponse<EventResponse>("Match must be started first"));
				} else {
					EventDTO event = new EventDTO();
					event.setEventName(EventType.GOAL.getDesc());
					event.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
					event.setMatchId(matchResponse.getId());
					event.setHalfName(half.getDesc());
					event.setPlayerId(eventRegisteringRequest.getPlayerId());
					event.setTimeInHalf(eventRegisteringRequest.getTimeInHalf());

					convertAndSend(event);

					return ResponseEntity.status(HttpStatus.ACCEPTED).build();
				}
			}
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ConflictResponse<EventResponse>("The provided match does not exist"));
		}
	}

	@Operation(summary = "Register the foul event in a match")
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_CREATED, description = "EventDTO registered successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponse.class)))
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_CONFLICT, description = "The provided match does not exist \t\n Match has already ended \t\n Match must be started first", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConflictResponse.class)))
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_BAD_REQUEST, description = "Invalid payload", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestResponse.class)))
	@PostMapping(path = "/foul")
	public ResponseEntity<Object> foul(@PathVariable("championshipId") UUID championshipId, @PathVariable("seasonId") UUID seasonId, @PathVariable("matchId") UUID matchId,
			@RequestBody @Valid EventRegisteringRequest eventRegisteringRequest, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			final BadRequestResponse badRequestResponse = new BadRequestResponse();
			bindingResult.getFieldErrors().stream().forEach(e -> badRequestResponse.addError(e.getField(), e.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestResponse);
		}

		if (eventRegisteringRequest.getPlayerId() == null) {
			final BadRequestResponse badRequestResponse = new BadRequestResponse();
			badRequestResponse.addError("player-id", "Must not be null");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestResponse);
		}

		Half half = Half.fromDesc(eventRegisteringRequest.getHalf());
		if (half == null) {
			final BadRequestResponse badRequestResponse = new BadRequestResponse();
			badRequestResponse.addError("half", "The provided half does not exist. Possible values are " + Half.toListDesc().toString());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestResponse);
		}

		MatchResponse matchResponse = this.matchService.findById(championshipId, seasonId, matchId);
		if (matchResponse != null) {
			List<EventResponse> all = this.eventService.findAllByEventType(championshipId, seasonId, matchId, EventType.END_MATCH);
			if (all != null) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body(new ConflictResponse<EventResponse>("Match has already ended"));
			} else {
				all = this.eventService.findAllByEventType(championshipId, seasonId, matchId, EventType.START_MATCH);
				if (all == null) {
					return ResponseEntity.status(HttpStatus.CONFLICT).body(new ConflictResponse<EventResponse>("Match must be started first"));
				} else {
					EventDTO event = new EventDTO();
					event.setEventName(EventType.FOUL.getDesc());
					event.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
					event.setMatchId(matchResponse.getId());
					event.setHalfName(half.getDesc());
					event.setPlayerId(eventRegisteringRequest.getPlayerId());
					event.setTimeInHalf(eventRegisteringRequest.getTimeInHalf());

					convertAndSend(event);

					return ResponseEntity.status(HttpStatus.ACCEPTED).build();
				}
			}
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ConflictResponse<EventResponse>("The provided match does not exist"));
		}
	}

	@Operation(summary = "Register the red card event in a match")
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_CREATED, description = "EventDTO registered successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponse.class)))
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_CONFLICT, description = "The provided match does not exist \t\n Match has already ended \t\n Match must be started first \t\n The player has already received a red card", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConflictResponse.class)))
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_BAD_REQUEST, description = "Invalid payload", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestResponse.class)))
	@PostMapping(path = "/red-card")
	public ResponseEntity<Object> redCard(@PathVariable("championshipId") UUID championshipId, @PathVariable("seasonId") UUID seasonId, @PathVariable("matchId") UUID matchId,
			@RequestBody @Valid EventRegisteringRequest eventRegisteringRequest, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			final BadRequestResponse badRequestResponse = new BadRequestResponse();
			bindingResult.getFieldErrors().stream().forEach(e -> badRequestResponse.addError(e.getField(), e.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestResponse);
		}

		if (eventRegisteringRequest.getPlayerId() == null) {
			final BadRequestResponse badRequestResponse = new BadRequestResponse();
			badRequestResponse.addError("player-id", "Must not be null");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestResponse);
		}

		Half half = Half.fromDesc(eventRegisteringRequest.getHalf());
		if (half == null) {
			final BadRequestResponse badRequestResponse = new BadRequestResponse();
			badRequestResponse.addError("half", "The provided half does not exist. Possible values are " + Half.toListDesc().toString());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestResponse);
		}

		MatchResponse matchResponse = this.matchService.findById(championshipId, seasonId, matchId);
		if (matchResponse != null) {
			List<EventResponse> all = this.eventService.findAllByEventType(championshipId, seasonId, matchId, EventType.END_MATCH);
			if (all != null) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body(new ConflictResponse<EventResponse>("Match has already ended"));
			} else {
				all = this.eventService.findAllByEventType(championshipId, seasonId, matchId, EventType.START_MATCH);
				if (all == null) {
					return ResponseEntity.status(HttpStatus.CONFLICT).body(new ConflictResponse<EventResponse>("Match must be started first"));
				} else {
					all = this.eventService.findAllByEventType(championshipId, seasonId, matchId, EventType.RED_CARD);
					if (all != null && !all.isEmpty()) {
						return ResponseEntity.status(HttpStatus.CONFLICT)
								.body(new ConflictResponse<EntityModel<EventResponse>>("The player has already received a red card", this.eventResponseModelAssembler.toModel(all.get(0))));
					} else {
						EventDTO event = new EventDTO();
						event.setEventName(EventType.RED_CARD.getDesc());
						event.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
						event.setMatchId(matchResponse.getId());
						event.setHalfName(half.getDesc());
						event.setPlayerId(eventRegisteringRequest.getPlayerId());
						event.setTimeInHalf(eventRegisteringRequest.getTimeInHalf());

						convertAndSend(event);

						return ResponseEntity.status(HttpStatus.ACCEPTED).build();
					}
				}
			}
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ConflictResponse<EventResponse>("The provided match does not exist"));
		}
	}

	@Operation(summary = "Register the yellow card event in a match")
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_CREATED, description = "EventDTO registered successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponse.class)))
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_CONFLICT, description = "Match has already ended \t\n Match must be started first \t\n The player has already received two yellow cards \t\n The provided match does not exist \t\n The player has already received a red card", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConflictResponse.class)))
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_BAD_REQUEST, description = "Invalid payload", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestResponse.class)))
	@PostMapping(path = "/yellow-card")
	public ResponseEntity<Object> yellowCard(@PathVariable("championshipId") UUID championshipId, @PathVariable("seasonId") UUID seasonId, @PathVariable("matchId") UUID matchId,
			@RequestBody @Valid EventRegisteringRequest eventRegisteringRequest, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			final BadRequestResponse badRequestResponse = new BadRequestResponse();
			bindingResult.getFieldErrors().stream().forEach(e -> badRequestResponse.addError(e.getField(), e.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestResponse);
		}

		if (eventRegisteringRequest.getPlayerId() == null) {
			final BadRequestResponse badRequestResponse = new BadRequestResponse();
			badRequestResponse.addError("player-id", "Must not be null");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestResponse);
		}

		Half half = Half.fromDesc(eventRegisteringRequest.getHalf());
		if (half == null) {
			final BadRequestResponse badRequestResponse = new BadRequestResponse();
			badRequestResponse.addError("half", "The provided half does not exist. Possible values are " + Half.toListDesc().toString());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestResponse);
		}

		MatchResponse matchResponse = this.matchService.findById(championshipId, seasonId, matchId);
		if (matchResponse != null) {
			List<EventResponse> all = this.eventService.findAllByEventType(championshipId, seasonId, matchId, EventType.END_MATCH);
			if (all != null) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body(new ConflictResponse<EventResponse>("Match has already ended"));
			} else {
				all = this.eventService.findAllByEventType(championshipId, seasonId, matchId, EventType.START_MATCH);
				if (all == null) {
					return ResponseEntity.status(HttpStatus.CONFLICT).body(new ConflictResponse<EventResponse>("Match must be started first"));
				} else {
					List<EventResponse> yellowCards = this.eventService.findAllByEventType(championshipId, seasonId, matchId, EventType.YELLOW_CARD);
					if (yellowCards != null && !yellowCards.isEmpty() && all.size() == 2) {
						List<EntityModel<EventResponse>> collect = yellowCards.stream().map(this.eventResponseModelAssembler::toModel).collect(Collectors.toList());
						return ResponseEntity.status(HttpStatus.CONFLICT)
								.body(new ConflictResponse<CollectionModel<EntityModel<EventResponse>>>("The player has already received two yellow cards", CollectionModel.of(collect)));
					} else {
						all = this.eventService.findAllByEventType(championshipId, seasonId, matchId, EventType.RED_CARD);
						if (all != null && !all.isEmpty()) {
							return ResponseEntity.status(HttpStatus.CONFLICT)
									.body(new ConflictResponse<EntityModel<EventResponse>>("The player has already received a red card", this.eventResponseModelAssembler.toModel(all.get(0))));
						} else {
							EventDTO event = new EventDTO();
							event.setEventName(EventType.YELLOW_CARD.getDesc());
							event.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
							event.setMatchId(matchResponse.getId());
							event.setHalfName(half.getDesc());
							event.setPlayerId(eventRegisteringRequest.getPlayerId());
							event.setTimeInHalf(eventRegisteringRequest.getTimeInHalf());
							if (yellowCards.size() == 1) {
								redCard(championshipId, seasonId, matchId, eventRegisteringRequest, bindingResult);
							}

							convertAndSend(event);

							return ResponseEntity.status(HttpStatus.ACCEPTED).build();
						}
					}
				}
			}
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ConflictResponse<EventResponse>("The provided match does not exist"));
		}
	}

	@Operation(summary = "Register the substitution event in a match")
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_CREATED, description = "EventDTO registered successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponse.class)))
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_CONFLICT, description = "Match has already ended \t\n Match must be started first \t\n The player has already received two yellow cards \t\n The provided match does not exist \t\n The player has already received a red card", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConflictResponse.class)))
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_BAD_REQUEST, description = "Invalid payload", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestResponse.class)))
	@PostMapping(path = "/substitution")
	public ResponseEntity<Object> substition(@PathVariable("championshipId") UUID championshipId, @PathVariable("seasonId") UUID seasonId, @PathVariable("matchId") UUID matchId,
			@RequestBody @Valid EventRegisteringRequest eventRegisteringRequest, BindingResult bindingResult) {

		final BadRequestResponse badRequestResponse = new BadRequestResponse();
		if (bindingResult.hasErrors()) {
			bindingResult.getFieldErrors().stream().forEach(e -> badRequestResponse.addError(e.getField(), e.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestResponse);
		}

		if (eventRegisteringRequest.getSubstitution() == null) {
			badRequestResponse.addError("substitution", "Must not be null");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestResponse);
		}
		if (eventRegisteringRequest.getSubstitution().getPlayerIdIn() == null) {
			badRequestResponse.addError("player-id-in", "Must not be null");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestResponse);
		}
		if (eventRegisteringRequest.getSubstitution().getPlayerIdOut() == null) {
			badRequestResponse.addError("player-id-out", "Must not be null");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestResponse);
		}
		if (eventRegisteringRequest.getSubstitution().getTeamId() == null) {
			badRequestResponse.addError("team-id", "Must not be null");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestResponse);
		}
		if (eventRegisteringRequest.getSubstitution().getPlayerIdIn().equals(eventRegisteringRequest.getSubstitution().getPlayerIdOut())) {
			badRequestResponse.addError("player-id-in", "Incoming and outcoming player cannot be the same");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestResponse);
		}

		Half half = Half.fromDesc(eventRegisteringRequest.getHalf());
		if (half == null) {
			badRequestResponse.addError("half", "The provided half does not exist. Possible values are " + Half.toListDesc().toString());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestResponse);
		}

		MatchResponse matchResponse = this.matchService.findById(championshipId, seasonId, matchId);
		if (matchResponse != null) {
			List<EventResponse> all = this.eventService.findAllByEventType(championshipId, seasonId, matchId, EventType.END_MATCH);
			if (all != null) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body(new ConflictResponse<EventResponse>("Match has already ended"));
			} else {
				all = this.eventService.findAllByEventType(championshipId, seasonId, matchId, EventType.START_MATCH);
				if (all == null) {
					return ResponseEntity.status(HttpStatus.CONFLICT).body(new ConflictResponse<EventResponse>("Match must be started first"));
				} else {
					all = this.eventService.findAllByEventTypeAndPlayerId(championshipId, seasonId, matchId, EventType.RED_CARD, eventRegisteringRequest.getSubstitution().getPlayerIdIn());
					if (all != null && !all.isEmpty()) {
						return ResponseEntity.status(HttpStatus.CONFLICT)
								.body(new ConflictResponse<EntityModel<EventResponse>>("The incoming player has already received a red card", this.eventResponseModelAssembler.toModel(all.get(0))));
					} else {
						all = this.eventService.findAllByEventTypeAndPlayerId(championshipId, seasonId, matchId, EventType.RED_CARD, eventRegisteringRequest.getSubstitution().getPlayerIdOut());
						if (all != null && !all.isEmpty()) {
							return ResponseEntity.status(HttpStatus.CONFLICT).body(
									new ConflictResponse<EntityModel<EventResponse>>("The outcoming player has already received a red card", this.eventResponseModelAssembler.toModel(all.get(0))));
						} else {
							EventDTO event = new EventDTO();
							event.setEventName(EventType.SUBSTITUTION.getDesc());
							event.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
							event.setMatchId(matchResponse.getId());
							event.setHalfName(half.getDesc());
							event.setPlayerId(null);
							event.setTimeInHalf(eventRegisteringRequest.getTimeInHalf());

							SubstitutionEventDTO substitutionEventDTO = new SubstitutionEventDTO();
							substitutionEventDTO.setPlayerIdIn(eventRegisteringRequest.getSubstitution().getPlayerIdIn());
							substitutionEventDTO.setPlayerIdOut(eventRegisteringRequest.getSubstitution().getPlayerIdOut());
							substitutionEventDTO.setTeamId(eventRegisteringRequest.getSubstitution().getTeamId());
							event.setSubstitutionEvent(substitutionEventDTO);

							convertAndSend(event);

							return ResponseEntity.status(HttpStatus.ACCEPTED).build();
						}
					}
				}
			}
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ConflictResponse<EventResponse>("The provided match does not exist"));
		}
	}

	@Operation(summary = "Register the start halftime event in a match")
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_CREATED, description = "EventDTO registered successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponse.class)))
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_CONFLICT, description = "The provided match does not exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConflictResponse.class)))
	@PostMapping(path = "/halftime/start")
	public ResponseEntity<Object> startHalftime(@PathVariable("championshipId") UUID championshipId, @PathVariable("seasonId") UUID seasonId, @PathVariable("matchId") UUID matchId) {
		MatchResponse matchResponse = this.matchService.findById(championshipId, seasonId, matchId);
		if (matchResponse != null) {
			EventDTO event = new EventDTO();
			event.setEventName(EventType.START_HALFTIME.getDesc());
			event.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
			event.setMatchId(matchResponse.getId());

			convertAndSend(event);

			return ResponseEntity.status(HttpStatus.ACCEPTED).build();
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ConflictResponse<EventResponse>("The provided match does not exist"));
		}
	}

	@Operation(summary = "Register the end halftime event in a match")
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_CREATED, description = "EventDTO registered successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponse.class)))
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_CONFLICT, description = "The provided match does not exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConflictResponse.class)))
	@PostMapping(path = "/halftime/end")
	public ResponseEntity<Object> endHalftime(@PathVariable("championshipId") UUID championshipId, @PathVariable("seasonId") UUID seasonId, @PathVariable("matchId") UUID matchId) {
		MatchResponse matchResponse = this.matchService.findById(championshipId, seasonId, matchId);
		if (matchResponse != null) {
			EventDTO event = new EventDTO();
			event.setEventName(EventType.END_HALFTIME.getDesc());
			event.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
			event.setMatchId(matchResponse.getId());

			convertAndSend(event);

			return ResponseEntity.status(HttpStatus.ACCEPTED).build();
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ConflictResponse<EventResponse>("The provided match does not exist"));
		}
	}

	@Operation(summary = "Register the stoppage time event in a match")
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_CREATED, description = "EventDTO registered successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponse.class)))
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_CONFLICT, description = "The provided match does not exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConflictResponse.class)))
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_BAD_REQUEST, description = "Invalid payload", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestResponse.class)))
	@PostMapping(path = "/stoppage-time")
	public ResponseEntity<Object> stoppageTime(@PathVariable("championshipId") UUID championshipId, @PathVariable("seasonId") UUID seasonId, @PathVariable("matchId") UUID matchId,
			@RequestBody @Valid EventRegisteringRequest eventRegisteringRequest, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			final BadRequestResponse badRequestResponse = new BadRequestResponse();
			bindingResult.getFieldErrors().stream().forEach(e -> badRequestResponse.addError(e.getField(), e.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestResponse);
		}

		if (eventRegisteringRequest.getStoppageTime() == null) {
			final BadRequestResponse badRequestResponse = new BadRequestResponse();
			badRequestResponse.addError("stoppage-time", "Must not be null");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestResponse);
		}
		if (eventRegisteringRequest.getStoppageTime().getTime() == null || eventRegisteringRequest.getStoppageTime().getTime() <= Short.valueOf("0")) {
			final BadRequestResponse badRequestResponse = new BadRequestResponse();
			badRequestResponse.addError("time", "Must not be null, zero or negative");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestResponse);
		}

		MatchResponse matchResponse = this.matchService.findById(championshipId, seasonId, matchId);
		if (matchResponse != null) {
			EventDTO event = new EventDTO();
			event.setEventName(EventType.STOPPAGE_TIME.getDesc());
			event.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
			event.setMatchId(matchResponse.getId());
			event.setStoppageTime(eventRegisteringRequest.getStoppageTime().getTime());

			convertAndSend(event);

			return ResponseEntity.status(HttpStatus.ACCEPTED).build();
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ConflictResponse<EventResponse>("The provided match does not exist"));
		}
	}

	@Operation(summary = "Register the warning event in a match")
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_CREATED, description = "EventDTO registered successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponse.class)))
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_CONFLICT, description = "The provided match does not exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConflictResponse.class)))
	@PostMapping(path = "/warning")
	public ResponseEntity<Object> warning(@PathVariable("championshipId") UUID championshipId, @PathVariable("seasonId") UUID seasonId, @PathVariable("matchId") UUID matchId) {
		MatchResponse matchResponse = this.matchService.findById(championshipId, seasonId, matchId);
		if (matchResponse != null) {
			EventDTO event = new EventDTO();
			event.setEventName(EventType.WARNING.getDesc());
			event.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
			event.setMatchId(matchResponse.getId());

			convertAndSend(event);

			return ResponseEntity.status(HttpStatus.ACCEPTED).build();
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ConflictResponse<EventResponse>("The provided match does not exist"));
		}
	}

	private void convertAndSend(EventDTO event) {
		this.rabbitTemplate.convertAndSend(this.fanoutExchange.getName(), "", event);
	}
}
