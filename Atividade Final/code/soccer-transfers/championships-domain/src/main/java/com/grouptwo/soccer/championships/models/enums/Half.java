package com.grouptwo.soccer.championships.models.enums;

import java.util.List;
import java.util.stream.Collectors;

public enum Half {

	FISRT_HALF("FH", "First Half"),
	SECOND_HALF("SH", "Second Half"),
	EXTRATIME_FISRT_HALF("EF", "Extratime First Half"),
	EXTRATIME_SECOND_HALF("ES", "Extratime Second Half");

	private String code;
	private String desc;

	private Half(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public static Half fromDesc(String desc) {
		for (Half h : Half.values()) {
			if (h.getDesc().equalsIgnoreCase(desc)) {
				return h;
			}
		}
		return null;
	}

	public static List<String> toListDesc() {
		return List.of(Half.values()).stream().map(Half::getDesc).collect(Collectors.toList());
	}
}
