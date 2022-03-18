package com.grouptwo.soccer.championships.controllers;

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

import com.grouptwo.soccer.championships.hateoas.assemblers.DivisionResponseModelAssembler;
import com.grouptwo.soccer.championships.models.Division;
import com.grouptwo.soccer.championships.services.DivisionService;
import com.grouptwo.soccer.transfers.lib.requests.DivisionRegisteringRequest;
import com.grouptwo.soccer.transfers.lib.requests.DivisionUpdatingRequest;
import com.grouptwo.soccer.transfers.lib.responses.BadRequestResponse;
import com.grouptwo.soccer.transfers.lib.responses.ConflictResponse;
import com.grouptwo.soccer.transfers.lib.responses.DivisionResponse;
import com.grouptwo.soccer.transfers.lib.utils.CommonUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(path = "/api/v1/divisions")
public class DivisionController {

	private final DivisionService divisionService;

	private final DivisionResponseModelAssembler divisionResponseModelAssembler;

	public DivisionController(DivisionService divisionService, DivisionResponseModelAssembler divisionResponseModelAssembler) {
		this.divisionService = divisionService;
		this.divisionResponseModelAssembler = divisionResponseModelAssembler;
	}

	@Operation(summary = "Get all registered divisions")
	@GetMapping
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_OK, description = "List of all registered divisions", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DivisionResponse.class)))
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_NOT_FOUND, description = "No divisions found", content = @Content)
	public ResponseEntity<CollectionModel<EntityModel<DivisionResponse>>> getAll() {
		List<DivisionResponse> all = this.divisionService.findAll();
		if (all != null && !all.isEmpty()) {
			List<EntityModel<DivisionResponse>> collect = all.stream().map(this.divisionResponseModelAssembler::toModel).collect(Collectors.toList());
			return ResponseEntity.status(HttpStatus.OK).body(CollectionModel.of(collect));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@Operation(summary = "Get a division by its id")
	@GetMapping(path = "/{divisionId}")
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_OK, description = "Found the division", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DivisionResponse.class)))
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_NOT_FOUND, description = "Division not found", content = @Content)
	public ResponseEntity<EntityModel<DivisionResponse>> getOne(@PathVariable("divisionId") UUID divisionId) {
		DivisionResponse response = this.divisionService.findById(divisionId);
		if (response != null) {
			return ResponseEntity.status(HttpStatus.OK).body(this.divisionResponseModelAssembler.toModel(response));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@Operation(summary = "Register a new division")
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_CREATED, description = "Division registered successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DivisionResponse.class)))
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_CONFLICT, description = "Division is already registered", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConflictResponse.class)))
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_BAD_REQUEST, description = "Invalid payload", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestResponse.class)))
	@PostMapping
	public ResponseEntity<Object> register(@RequestBody @Valid DivisionRegisteringRequest divisionRegisteringRequest, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			final BadRequestResponse badRequestResponse = new BadRequestResponse();
			bindingResult.getFieldErrors().stream().forEach(e -> badRequestResponse.addError(e.getField(), e.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestResponse);
		}

		divisionRegisteringRequest.setName(divisionRegisteringRequest.getName().toUpperCase());

		DivisionResponse response = this.divisionService.findByName(divisionRegisteringRequest.getName());
		if (response == null) {
			Division entity = this.divisionService.getDivisionConverter().fromRequestToEntity(divisionRegisteringRequest);
			entity = this.divisionService.save(entity);
			return ResponseEntity.status(HttpStatus.CREATED).body(this.divisionResponseModelAssembler.toModel(this.divisionService.getDivisionConverter().fromEntityToResponse(entity)));
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(new ConflictResponse<EntityModel<DivisionResponse>>("Division is already registered", this.divisionResponseModelAssembler.toModel(response)));
		}
	}

	@Operation(summary = "Update a division by its id")
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_OK, description = "Division updated successfully", content = @Content)
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_CONFLICT, description = "The provided name is already in use \t\n The provided division id does not exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConflictResponse.class)))
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_BAD_REQUEST, description = "Invalid payload", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestResponse.class)))
	@PatchMapping(path = "/{divisionId}")
	public ResponseEntity<Object> updateOne(@PathVariable("divisionId") UUID divisionId, @RequestBody @Valid DivisionUpdatingRequest divisionUpdatingRequest, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			final BadRequestResponse badRequestResponse = new BadRequestResponse();
			bindingResult.getFieldErrors().stream().forEach(e -> badRequestResponse.addError(e.getField(), e.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestResponse);
		}

		divisionUpdatingRequest.setNewName(divisionUpdatingRequest.getNewName().toUpperCase());

		DivisionResponse response = this.divisionService.findById(divisionId);
		if (response != null) {
			DivisionResponse responseByName = this.divisionService.findByName(divisionUpdatingRequest.getNewName());
			if (responseByName == null || responseByName.getId().equals(response.getId())) {
				response.setName(divisionUpdatingRequest.getNewName());
				this.divisionService.save(response);
				return ResponseEntity.status(HttpStatus.OK).body(this.divisionResponseModelAssembler.toModel(response));
			} else {
				return ResponseEntity.status(HttpStatus.CONFLICT).body(new ConflictResponse<EntityModel<DivisionResponse>>("The provided name is already in use", this.divisionResponseModelAssembler.toModel(responseByName)));
			}
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ConflictResponse<DivisionResponse>("The provided division id does not exist"));
		}
	}

	@Operation(summary = "Delete a division by its id")
	@ApiResponse(responseCode = CommonUtil.HTTP_STATUS_CODE_NO_CONTENT, description = "Division deleted successfully", content = @Content)
	@DeleteMapping(path = "/{divisionId}")
	public ResponseEntity<Object> deleteOne(@PathVariable("divisionId") UUID divisionId) {
		DivisionResponse response = this.divisionService.findById(divisionId);
		if (response != null) {
			this.divisionService.deleteById(divisionId);
		}
		return ResponseEntity.noContent().build();
	}
}
