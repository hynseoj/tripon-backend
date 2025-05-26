package com.ssafy.tripon.attraction.application.command;

import com.ssafy.tripon.attraction.domain.CustomAttraction;

public record AttractionSaveCommand(
	String title,
	String address,
	Double latitude,
	Double longitude
) {
	public CustomAttraction toCustomAttraction() {
		return new CustomAttraction(title, address, latitude, longitude);
	}
}
