package com.grouptwo.soccer.transfers.models;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "transfer")
public class Transfer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@Column(nullable = false)
	private String playerName;

	@Column(nullable = false)
	private String fromTeamName;

	@Column(nullable = false)
	private String toTeamName;

	@Column(nullable = false)
	private LocalDateTime transferredAt;

	@Column(nullable = false)
	private Double transferValue;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

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

	@Override
	public int hashCode() {
		return Objects.hash(fromTeamName, playerName, toTeamName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transfer other = (Transfer) obj;
		return Objects.equals(fromTeamName, other.fromTeamName) && Objects.equals(playerName, other.playerName) && Objects.equals(toTeamName, other.toTeamName);
	}
}
