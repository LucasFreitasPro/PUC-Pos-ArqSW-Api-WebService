package com.grouptwo.soccer.transfers.lib.requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransferRequest {

	@JsonProperty("player-name")
	@NotBlank
	private String playerName;

	@JsonProperty("from-team-name")
	@NotBlank
	private String fromTeamName;

	@JsonProperty("to-team-name")
	@NotBlank
	private String toTeamName;

	@JsonProperty("transfer-value")
	@NotNull
	@Positive
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

	public Double getTransferValue() {
		return transferValue;
	}

	public void setTransferValue(Double transferValue) {
		this.transferValue = transferValue;
	}

	@Override
	public String toString() {
		return "TransferRequest [playerName=" + playerName + ", fromTeamName=" + fromTeamName + ", toTeamName=" + toTeamName + ", transferValue=" + transferValue + "]";
	}
}
