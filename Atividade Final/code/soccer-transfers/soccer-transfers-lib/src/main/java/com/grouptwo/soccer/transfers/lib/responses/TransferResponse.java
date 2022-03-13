package com.grouptwo.soccer.transfers.lib.responses;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransferResponse {

	@JsonProperty("player-id")
	private UUID playerId;

	@JsonProperty("origin-team-id")
	private UUID originTeamId;

	@JsonProperty("destiny-team-id")
	private UUID destinyTeamId;

	@JsonProperty("transferred-at")
	private LocalDateTime transferredAt;

	@JsonProperty("transfer-value")
	private Double transferValue;

	public UUID getPlayerId() {
		return playerId;
	}

	public void setPlayerId(UUID playerId) {
		this.playerId = playerId;
	}

	public UUID getOriginTeamId() {
		return originTeamId;
	}

	public void setOriginTeamId(UUID originTeamId) {
		this.originTeamId = originTeamId;
	}

	public UUID getDestinyTeamId() {
		return destinyTeamId;
	}

	public void setDestinyTeamId(UUID destinyTeamId) {
		this.destinyTeamId = destinyTeamId;
	}

	public LocalDateTime getTransferredAt() {
		return transferredAt;
	}

	public void setTransferredAt(LocalDateTime transferredAt) {
		this.transferredAt = transferredAt;
	}

	public Double getTransferValue() {
		return transferValue;
	}

	public void setTransferValue(Double transferValue) {
		this.transferValue = transferValue;
	}
}
