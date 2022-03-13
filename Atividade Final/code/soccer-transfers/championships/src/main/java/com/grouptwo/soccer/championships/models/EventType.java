package com.grouptwo.soccer.championships.models;

public enum EventType {

	FAULT("FT", "Fault"),
	RED_CARD("RD", "Red Card"),
	YELLOW_CARD("YD", "Yellow Card");

	private String code;
	private String desc;

	private EventType(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
}
