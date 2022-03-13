package com.grouptwo.soccer.championships.models;

import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "championship")
public class Championship {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String divisionName;

	@Column(nullable = false)
	private String country;

	@Column(nullable = false)
	private Boolean deleted;

	@OneToMany(mappedBy = "championship", fetch = FetchType.EAGER)
	private Set<Season> seasons;

	@Transient
	private Boolean addLinkToRegisterSeason;

	public Championship() {
	}

	public Championship(UUID id) {
		this.id = id;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDivisionName() {
		return divisionName;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Set<Season> getSeasons() {
		return seasons;
	}

	public void setSeasons(Set<Season> seasons) {
		this.seasons = seasons;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Boolean getAddLinkToRegisterSeason() {
		return addLinkToRegisterSeason;
	}

	public void setAddLinkToRegisterSeason(Boolean addLinkToRegisterSeason) {
		this.addLinkToRegisterSeason = addLinkToRegisterSeason;
	}
}
