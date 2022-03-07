package com.grouptwo.soccer.transfers.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grouptwo.soccer.transfers.models.Transfer;
import com.grouptwo.soccer.transfers.repositories.TransferRepository;

@Service
public class TransferService {

	private final TransferRepository repository;

	public TransferService(TransferRepository repository) {
		this.repository = repository;
	}

	@Transactional
	public void save(Transfer newTransfer) {
		this.repository.save(newTransfer);
	}

	public List<Transfer> findByFromTeam(String teamName) {
		return this.repository.findByFromTeam(teamName);
	}

	public List<Transfer> findByToTeam(String teamName) {
		return this.repository.findByToTeam(teamName);
	}

	public List<Transfer> findByPlayerName(String playerName) {
		return this.repository.findByPlayerName(playerName);
	}
}
