package com.grouptwo.soccer.transfers.lib.requests;

import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransferRequest {

	@JsonProperty("player-id")
	@NotNull
	private UUID playerId;

	@JsonProperty("origin-team-id")
	@NotNull
	private UUID originTeamId;

	@JsonProperty("destiny-team-id")
	@NotNull
	private UUID destinyTeamId;

	@JsonProperty("transfer-value")
	@NotNull
	@Positive
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

	public Double getTransferValue() {
		return transferValue;
	}

	public void setTransferValue(Double transferValue) {
		this.transferValue = transferValue;
	}

	@Override
	public String toString() {
		return "TransferRequest [playerId=" + playerId + ", originTeamId=" + originTeamId + ", destinyTeamId=" + destinyTeamId + ", transferValue=" + transferValue + "]";
	}
}
