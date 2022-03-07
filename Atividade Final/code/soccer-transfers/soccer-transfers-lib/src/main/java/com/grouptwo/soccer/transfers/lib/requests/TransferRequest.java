package com.grouptwo.soccer.transfers.lib.requests;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransferRequest {

	@JsonProperty("player-name")
	@NotNull(message = "Player name must not be null")
	private String playerName;

	@JsonProperty("from-team-name")
	@NotNull(message = "From Team name must not be null")
	private String fromTeam;

	@JsonProperty("to-team-name")
	@NotNull(message = "To Team name must not be null")
	private String toTeam;

	@JsonProperty("transfer-value")
	@NotNull(message = "Transfer value must not be null")
	@Positive(message = "Only positive value")
	private Double transferValue;

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getFromTeam() {
		return fromTeam;
	}

	public void setFromTeam(String fromTeam) {
		this.fromTeam = fromTeam;
	}

	public String getToTeam() {
		return toTeam;
	}

	public void setToTeam(String toTeam) {
		this.toTeam = toTeam;
	}

	public Double getTransferValue() {
		return transferValue;
	}

	public void setTransferValue(Double transferValue) {
		this.transferValue = transferValue;
	}

	@Override
	public String toString() {
		return "TransferRequest [playerName=" + playerName + ", fromTeam=" + fromTeam + ", toTeam=" + toTeam + ", transferValue=" + transferValue + "]";
	}
}
