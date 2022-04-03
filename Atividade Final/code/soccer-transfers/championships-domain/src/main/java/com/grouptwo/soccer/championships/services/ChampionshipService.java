package com.grouptwo.soccer.championships.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grouptwo.soccer.championships.converters.ChampionshipConverter;
import com.grouptwo.soccer.championships.models.Championship;
import com.grouptwo.soccer.championships.repositories.ChampionshipRepository;
import com.grouptwo.soccer.transfers.lib.responses.ChampionshipResponse;

@Service
public class ChampionshipService {

	private final ChampionshipRepository championshipRepository;

	private final ChampionshipConverter championshipConverter;

	public ChampionshipService(ChampionshipRepository championshipRepository, ChampionshipConverter championshipConverter) {
		this.championshipRepository = championshipRepository;
		this.championshipConverter = championshipConverter;
	}

	public ChampionshipConverter getChampionshipConverter() {
		return championshipConverter;
	}

	public List<ChampionshipResponse> findAll() {
		List<Championship> all = this.championshipRepository.findAll();
		return all == null || all.isEmpty() ? null : all.stream().map(c -> this.championshipConverter.fromEntityToResponse(c)).collect(Collectors.toList());
	}

	public ChampionshipResponse findById(UUID id) {
		Optional<Championship> optional = this.championshipRepository.findById(id);
		return optional.isPresent() ? this.championshipConverter.fromEntityToResponse(optional.get()) : null;
	}

	@Transactional
	public Championship save(Championship entity) {
		return this.championshipRepository.save(entity);
	}

	public ChampionshipResponse findByNameAndDivisionId(String name, UUID divisionId) {
		Championship championship = this.championshipRepository.findByNameAndDivisionId(name, divisionId);
		return championship == null ? null : this.championshipConverter.fromEntityToResponse(championship);
	}

	public void save(ChampionshipResponse championshipResponse) {
		save(this.championshipConverter.fromResponseToEntity(championshipResponse));
	}

	public ChampionshipResponse findByName(String name) {
		Championship championship = this.championshipRepository.findByName(name);
		return championship != null ? this.championshipConverter.fromEntityToResponse(championship) : null;
	}
}
