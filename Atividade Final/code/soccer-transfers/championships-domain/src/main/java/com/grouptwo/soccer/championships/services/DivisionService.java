package com.grouptwo.soccer.championships.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grouptwo.soccer.championships.converters.DivisionConverter;
import com.grouptwo.soccer.championships.models.Division;
import com.grouptwo.soccer.championships.repositories.DivisionRepository;
import com.grouptwo.soccer.transfers.lib.responses.DivisionResponse;

@Service
public class DivisionService {

	private final DivisionRepository divisionRepository;

	private final DivisionConverter divisionConverter;

	public DivisionService(DivisionRepository divisionRepository, DivisionConverter divisionConverter) {
		this.divisionRepository = divisionRepository;
		this.divisionConverter = divisionConverter;
	}

	public DivisionConverter getDivisionConverter() {
		return divisionConverter;
	}

	public List<DivisionResponse> findAll() {
		List<Division> all = this.divisionRepository.findAll();
		return all != null && !all.isEmpty() ? all.stream().map(this.divisionConverter::fromEntityToResponse).collect(Collectors.toList()) : null;
	}

	public DivisionResponse findById(UUID divisionId) {
		Optional<Division> optional = this.divisionRepository.findById(divisionId);
		return optional.isPresent() ? this.divisionConverter.fromEntityToResponse(optional.get()) : null;
	}

	public DivisionResponse findByName(String name) {
		Division division = this.divisionRepository.findByName(name);
		return division != null ? this.divisionConverter.fromEntityToResponse(division) : null;
	}

	@Transactional
	public Division save(Division entity) {
		return this.divisionRepository.save(entity);
	}

	@Transactional
	public void deleteById(UUID divisionId) {
		this.divisionRepository.deleteById(divisionId);
	}

	public Division save(DivisionResponse response) {
		Division entity = this.divisionConverter.fromResponseToEntity(response);
		return save(entity);
	}
}
