package com.grouptwo.soccer.championships.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

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

import com.grouptwo.soccer.championships.hateoas.assemblers.ChampionshipResponseModelAssembler;
import com.grouptwo.soccer.championships.models.Championship;
import com.grouptwo.soccer.championships.services.ChampionshipService;
import com.grouptwo.soccer.championships.services.DivisionService;
import com.grouptwo.soccer.transfers.lib.requests.ChampionshipRegisteringRequest;
import com.grouptwo.soccer.transfers.lib.requests.ChampionshipUpdatingRequest;
import com.grouptwo.soccer.transfers.lib.responses.BadRequestResponse;
import com.grouptwo.soccer.transfers.lib.responses.ChampionshipResponse;
import com.grouptwo.soccer.transfers.lib.responses.ConflictResponse;
import com.grouptwo.soccer.transfers.lib.responses.DivisionResponse;
import com.grouptwo.soccer.transfers.lib.utils.CommonUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(path = "/api/v1/championships")
public class ChampionshipController {

	private final DivisionService divisionService;

	private final ChampionshipService championshipService;

	private final ChampionshipResponseModelAssembler championshipResponseModelAssembler;

	public ChampionshipController(ChampionshipService championshipService, ChampionshipResponseModelAssembler championshipResponseModelAssembler, DivisionService divisionService) {
		this.divisionService = divisionService;
		this.championshipService = championshipService;
		this.championshipResponseModelAssembler = championshipResponseModelAssembler;
	}

	@Operation(summary = "Get all registered championships")
	@GetMapping
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_OK, description = "List of all registered championships", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ChampionshipResponse.class)))
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_NOT_FOUND, description = "No championships found", content = @Content)
	public ResponseEntity<CollectionModel<EntityModel<ChampionshipResponse>>> getAll() {
		List<ChampionshipResponse> all = this.championshipService.findAll();
		if (all != null) {
			List<EntityModel<ChampionshipResponse>> collect = all.stream().map(this.championshipResponseModelAssembler::toModel).collect(Collectors.toList());
			return ResponseEntity.status(HttpStatus.OK).body(CollectionModel.of(collect, linkTo(methodOn(ChampionshipController.class).getAll()).withSelfRel()));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@Operation(summary = "Get a championship by its id")
	@GetMapping(path = "/{championshipId}")
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_OK, description = "Found the championship", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ChampionshipResponse.class)))
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_NOT_FOUND, description = "Championship not found", content = @Content)
	public ResponseEntity<EntityModel<ChampionshipResponse>> getOne(@PathVariable("championshipId") UUID championshipId) {
		ChampionshipResponse championshipResponse = this.championshipService.findById(championshipId);
		if (championshipResponse != null) {
			return ResponseEntity.status(HttpStatus.OK).body(this.championshipResponseModelAssembler.toModel(championshipResponse));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@Operation(summary = "Register a new championship")
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_CREATED, description = "Championship registered successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ChampionshipResponse.class)))
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_CONFLICT, description = "Championship is already registered \t\n The provided division does not exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConflictResponse.class)))
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_BAD_REQUEST, description = "Invalid payload", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestResponse.class)))
	@PostMapping
	public ResponseEntity<Object> register(@RequestBody @Valid ChampionshipRegisteringRequest championshipRegisteringRequest, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			final BadRequestResponse badRequestResponse = new BadRequestResponse();
			bindingResult.getFieldErrors().stream().forEach(e -> badRequestResponse.addError(e.getField(), e.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestResponse);
		}

		championshipRegisteringRequest.setName(championshipRegisteringRequest.getName().toUpperCase());

		ChampionshipResponse cResponse = this.championshipService.findByNameAndDivisionId(championshipRegisteringRequest.getName(), championshipRegisteringRequest.getDivisionId());
		if (cResponse != null) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(new ConflictResponse<EntityModel<ChampionshipResponse>>("Championship is already registered", this.championshipResponseModelAssembler.toModel(cResponse)));
		}
		DivisionResponse divisionResponse = this.divisionService.findById(championshipRegisteringRequest.getDivisionId());
		if (divisionResponse != null) {
			Championship entity = this.championshipService.getChampionshipConverter().fromRequestToEntity(championshipRegisteringRequest);
			entity.setDivision(this.divisionService.getDivisionConverter().fromResponseToEntity(divisionResponse));
			entity = this.championshipService.save(entity);
			entity.setAddLinkToRegisterSeason(Boolean.TRUE);

			return ResponseEntity.status(HttpStatus.CREATED).body(this.championshipResponseModelAssembler.toModel(this.championshipService.getChampionshipConverter().fromEntityToResponse(entity)));
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ConflictResponse<ChampionshipResponse>("The provided division does not exist"));
		}
	}

	@Operation(summary = "Update a championship by its id")
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_OK, description = "Championship updated successfully", content = @Content)
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_CONFLICT, description = "The provided name is already in use \t\n The provided championship id does not exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConflictResponse.class)))
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_BAD_REQUEST, description = "Invalid payload", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestResponse.class)))
	@PatchMapping(path = "/{championshipId}")
	public ResponseEntity<Object> updateOne(@PathVariable("championshipId") UUID championshipId, @RequestBody @Valid ChampionshipUpdatingRequest championshipUpdatingRequest,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			final BadRequestResponse badRequestResponse = new BadRequestResponse();
			bindingResult.getFieldErrors().stream().forEach(e -> badRequestResponse.addError(e.getField(), e.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestResponse);
		}

		championshipUpdatingRequest.setNewName(championshipUpdatingRequest.getNewName().toUpperCase());

		ChampionshipResponse response = this.championshipService.findById(championshipId);
		if (response != null) {
			ChampionshipResponse responseByName = this.championshipService.findByName(championshipUpdatingRequest.getNewName());
			if (responseByName == null || responseByName.getId().equals(response.getId())) {
				response.setName(championshipUpdatingRequest.getNewName());
				this.championshipService.save(response);
				return ResponseEntity.status(HttpStatus.OK).body(this.championshipResponseModelAssembler.toModel(response));
			} else {
				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body(new ConflictResponse<EntityModel<ChampionshipResponse>>("The provided name is already in use", this.championshipResponseModelAssembler.toModel(responseByName)));
			}
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ConflictResponse<ChampionshipResponse>("The provided championship id does not exist"));
		}
	}

	@Operation(summary = "Delete a championship by its id")
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_NO_CONTENT, description = "Championship deleted successfully", content = @Content)
	@DeleteMapping(path = "/{championshipId}")
	public ResponseEntity<Object> deleteOne(@PathVariable("championshipId") UUID championshipId) {
		ChampionshipResponse championshipResponse = this.championshipService.findById(championshipId);
		if (championshipResponse != null) {
			championshipResponse.setDeleted(Boolean.TRUE);
			this.championshipService.save(championshipResponse);
		}
		return ResponseEntity.noContent().build();
	}
}
