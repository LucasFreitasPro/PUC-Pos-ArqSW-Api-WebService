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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "transfer")
public class Transfer {

	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@Column(nullable = false)
	private String playerName;

	@Column(nullable = false)
	private String fromTeam;

	@Column(nullable = false)
	private String toTeam;

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
		return Objects.hash(fromTeam, playerName, toTeam);
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
		return Objects.equals(fromTeam, other.fromTeam) && Objects.equals(playerName, other.playerName) && Objects.equals(toTeam, other.toTeam);
	}
}
