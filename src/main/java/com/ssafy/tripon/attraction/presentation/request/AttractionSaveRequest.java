package com.ssafy.tripon.attraction.presentation.request;

import com.ssafy.tripon.attraction.application.command.AttractionSaveCommand;

public record AttractionSaveRequest (
		String title,
		Integer areaCode,
		Integer siGunGuCode,
		Double latitude,
		Double longitude
	){

	public AttractionSaveCommand toCommand() {
		return new AttractionSaveCommand(title, areaCode, siGunGuCode, latitude, longitude);
	}
}
