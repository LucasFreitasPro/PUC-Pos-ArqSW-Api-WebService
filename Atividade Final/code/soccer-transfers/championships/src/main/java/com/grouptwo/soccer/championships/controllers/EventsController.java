package com.grouptwo.soccer.championships.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/championships/{championshipId}/seasons/{seasonId}/matches/{matchId}/events")
public class EventsController {

	@GetMapping
	public void getAll() {

	}

	@GetMapping(path = "/{eventType}")
	public void getAllByEventType(@PathVariable("eventType") String eventType) {

	}

	@PostMapping(path = "/start-match")
	public void startMatch() {

	}

	@PostMapping(path = "/end-match")
	public void endMatch() {

	}
}
