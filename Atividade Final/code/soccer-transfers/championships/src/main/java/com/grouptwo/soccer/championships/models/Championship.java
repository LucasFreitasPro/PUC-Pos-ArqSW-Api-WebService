package com.grouptwo.soccer.championships.models;

import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Where;

@Entity
@Table(name = "championship", uniqueConstraints = { @UniqueConstraint(columnNames = { "name", "division_id" }) })
@Where(clause = "deleted = 'f'")
public class Championship {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@Column(nullable = false)
	private String name;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "division_id", referencedColumnName = "id")
	private Division division;

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

	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
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
