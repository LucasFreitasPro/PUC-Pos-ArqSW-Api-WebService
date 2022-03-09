package com.grouptwo.soccer.transfers.lib.responses;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransferResponse {

	@JsonProperty("player-name")
	private String playerName;

	@JsonProperty("from-team-name")
	private String fromTeamName;

	@JsonProperty("to-team-name")
	private String toTeamName;

	@JsonProperty("transferred-at")
	private LocalDateTime transferredAt;

	@JsonProperty("transfer-value")
	private Double transferValue;

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getFromTeamName() {
		return fromTeamName;
	}

	public void setFromTeamName(String fromTeamName) {
		this.fromTeamName = fromTeamName;
	}

	public String getToTeamName() {
		return toTeamName;
	}

	public void setToTeamName(String toTeamName) {
		this.toTeamName = toTeamName;
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
