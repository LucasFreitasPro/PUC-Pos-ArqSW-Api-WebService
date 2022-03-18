package com.grouptwo.soccer.championships.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grouptwo.soccer.championships.models.SubstitutionEvent;

public interface SubstitutionEventRepository extends JpaRepository<SubstitutionEvent, UUID> {

}
