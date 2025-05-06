package com.ssafy.tripon.attraction.application.command;

import com.ssafy.tripon.attraction.domain.Attraction;
import com.ssafy.tripon.attraction.domain.CustomAttraction;

public record AttractionSaveCommand(
	String title,
	Integer areaCode,
	Integer siGunGuCode,
	Double latitude,
	Double longitude
	) {
	
	public CustomAttraction toCustomAttraction() {
		return new CustomAttraction(title, areaCode, siGunGuCode, latitude, longitude);
	}
	
	public Attraction toAttraction() {
		return new Attraction(title, areaCode, siGunGuCode, latitude, longitude);
	}
	
}
