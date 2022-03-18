package com.grouptwo.soccer.championships.models.enums;

public enum EventType {

	START_MATCH("SM", "Start Match"),
	END_MATCH("EM", "End Match"),
	GOAL("GL", "Goal"),
	FOUL("FL", "Foul"),
	RED_CARD("RD", "Red Card"),
	YELLOW_CARD("YD", "Yellow Card"),
	SUBSTITUTION("ST", "Substition"),
	HALFTIME("HT", "Halftime"),
	STOPPAGE_TIME("SP", "Stoppage Time"),
	WARNING("WN", "Warning"),
	OFF_SIDE("OS", "Off-side");

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

	public static EventType fromDesc(String desc) {
		for (EventType et : EventType.values()) {
			if (et.getDesc().equalsIgnoreCase(desc)) {
				return et;
			}
		}
		return null;
	}
}
