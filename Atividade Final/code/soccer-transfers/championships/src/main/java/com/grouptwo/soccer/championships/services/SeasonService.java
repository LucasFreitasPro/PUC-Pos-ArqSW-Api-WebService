package com.grouptwo.soccer.championships.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grouptwo.soccer.championships.converters.SeasonConverter;
import com.grouptwo.soccer.championships.models.Season;
import com.grouptwo.soccer.championships.repositories.SeasonRepository;
import com.grouptwo.soccer.transfers.lib.responses.SeasonResponse;

@Service
public class SeasonService {

	private final SeasonRepository seasonRepository;

	private final SeasonConverter seasonConverter;

	public SeasonService(SeasonRepository seasonRepository, SeasonConverter seasonConverter) {
		this.seasonRepository = seasonRepository;
		this.seasonConverter = seasonConverter;
	}

	public SeasonConverter getSeasonConverter() {
		return seasonConverter;
	}

	public List<SeasonResponse> findAll(UUID championshipId) {
		List<Season> all = this.seasonRepository.findAll(championshipId);
		return all != null && !all.isEmpty() ? all.stream().map(seasonConverter::fromEntityToResponse).collect(Collectors.toList()) : null;
	}

	public SeasonResponse findById(UUID championshipId, UUID seasonId) {
		Season season = this.seasonRepository.findById(championshipId, seasonId);
		return season != null ? this.seasonConverter.fromEntityToResponse(season) : null;
	}

	@Transactional
	public Season save(Season entity) {
		return this.seasonRepository.save(entity);
	}

	public Season findByEndedAtIsNull() {
		return this.seasonRepository.findByEndedAtIsNull();
	}

	public Season findByYear(Short year) {
		return this.seasonRepository.findByYear(year);
	}

	public void save(SeasonResponse response) {
		save(this.seasonConverter.fromResponseToEntity(response));
	}
}
