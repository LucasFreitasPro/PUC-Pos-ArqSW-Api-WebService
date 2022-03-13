package com.grouptwo.soccer.transfers.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grouptwo.soccer.transfers.converters.TransferConverter;
import com.grouptwo.soccer.transfers.models.Transfer;
import com.grouptwo.soccer.transfers.repositories.TransferRepository;

@Service
public class TransferService {

	private final TransferRepository repository;

	private final TransferConverter converter;

	public TransferService(TransferRepository repository, TransferConverter converter) {
		this.repository = repository;
		this.converter = converter;
	}

	public TransferConverter getConverter() {
		return converter;
	}

	@Transactional
	public void save(Transfer newTransfer) {
		this.repository.save(newTransfer);
	}

	public List<Transfer> findByOriginTeamId(UUID originTeamId) {
		return this.repository.findByOriginTeamId(originTeamId);
	}

	public List<Transfer> findByDestinyTeamId(UUID destinyTeamId) {
		return this.repository.findByDestinyTeamId(destinyTeamId);
	}

	public List<Transfer> findByPlayerId(UUID playerId) {
		return this.repository.findByPlayerId(playerId);
	}
}
