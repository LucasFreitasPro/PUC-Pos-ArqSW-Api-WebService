package com.grouptwo.soccer.transfers.lib.responses;

import java.util.Set;
import java.util.UUID;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ChampionshipResponse {

	private UUID id;

	private String name;

	private DivisionResponse division;

	private String country;

	@JsonIgnore
	private Boolean deleted;

	@JsonIgnore
	private Set<SeasonResponse> seasons;

	@JsonProperty("seasons")
	private CollectionModel<EntityModel<SeasonResponse>> seasonsCollectionModel;

	@JsonIgnore
	private Boolean addLinkToRegisterSeason;

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

	public DivisionResponse getDivision() {
		return division;
	}

	public void setDivision(DivisionResponse division) {
		this.division = division;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Set<SeasonResponse> getSeasons() {
		return seasons;
	}

	public void setSeasons(Set<SeasonResponse> seasons) {
		this.seasons = seasons;
	}

	public CollectionModel<EntityModel<SeasonResponse>> getSeasonsCollectionModel() {
		return seasonsCollectionModel;
	}

	public void setSeasonsCollectionModel(CollectionModel<EntityModel<SeasonResponse>> seasonsCollectionModel) {
		this.seasonsCollectionModel = seasonsCollectionModel;
	}

	public Boolean getAddLinkToRegisterSeason() {
		return addLinkToRegisterSeason;
	}

	public void setAddLinkToRegisterSeason(Boolean addLinkToRegisterSeason) {
		this.addLinkToRegisterSeason = addLinkToRegisterSeason;
	}
}
