package com.ssafy.tripon.attraction.application.command;

public record AttractionFindCommand(
		Integer areaCode,
		Integer siGunGuCode,
		Integer type,
		String keyword,
		Integer cursor,
		int size
	) {

}
