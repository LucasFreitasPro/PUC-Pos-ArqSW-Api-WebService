package com.grouptwo.soccer.championships.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grouptwo.soccer.championships.converters.SubstitutionEventConverter;
import com.grouptwo.soccer.championships.models.SubstitutionEvent;
import com.grouptwo.soccer.championships.repositories.SubstitutionEventRepository;
import com.grouptwo.soccer.transfers.lib.responses.SubstitutionEventResponse;

@Service
public class SubstitutionEventService {

	private final SubstitutionEventRepository substitutionEventRepository;

	private final SubstitutionEventConverter substitutionEventConverter;

	public SubstitutionEventService(SubstitutionEventRepository substitutionEventRepository, SubstitutionEventConverter substitutionEventConverter) {
		this.substitutionEventRepository = substitutionEventRepository;
		this.substitutionEventConverter = substitutionEventConverter;
	}

	@Transactional
	public SubstitutionEventResponse save(SubstitutionEvent entity) {
		return this.substitutionEventConverter.fromEntityToResponse(this.substitutionEventRepository.save(entity));
	}
}
