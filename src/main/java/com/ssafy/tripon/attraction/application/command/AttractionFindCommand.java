package com.ssafy.tripon.attraction.application.command;

public record AttractionFindCommand(
		Integer areaCode,
		Integer siGunGuCode,
		String keyword
	) {

}
