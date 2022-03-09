package com.grouptwo.soccer.transfers.services;

import java.util.List;

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

	public List<Transfer> findByFromTeamName(String teamName) {
		return this.repository.findByFromTeamName(teamName);
	}

	public List<Transfer> findByToTeamName(String teamName) {
		return this.repository.findByToTeamName(teamName);
	}

	public List<Transfer> findByPlayerName(String playerName) {
		return this.repository.findByPlayerName(playerName);
	}
}
