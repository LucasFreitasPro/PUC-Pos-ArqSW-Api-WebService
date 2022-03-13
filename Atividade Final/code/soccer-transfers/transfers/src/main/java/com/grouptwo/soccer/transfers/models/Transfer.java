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
	private UUID playerId;

	@Column(nullable = false)
	private UUID originTeamId;

	@Column(nullable = false)
	private UUID destinyTeamId;

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

	@Override
	public int hashCode() {
		return Objects.hash(destinyTeamId, originTeamId, playerId);
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
		return Objects.equals(destinyTeamId, other.destinyTeamId) && Objects.equals(originTeamId, other.originTeamId) && Objects.equals(playerId, other.playerId);
	}
}
