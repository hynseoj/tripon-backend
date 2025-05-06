package com.ssafy.tripon.attraction.application;

import com.ssafy.tripon.attraction.domain.Attraction;
import com.ssafy.tripon.attraction.domain.CustomAttraction;

public record AttractionServiceResponse(
		String title, 
		Integer areaCode, 
		Integer siGunGuCode, 
		Double latitude, 
		Double longitude,
		Boolean isCustom // 커스텀 관광지 여부
) {

	public static AttractionServiceResponse from(Attraction a) {
		return new AttractionServiceResponse(a.getTitle(), a.getAreaCode(), a.getSiGunGuCode(), a.getLatitude(),
				a.getLongitude(), false);
	}

	public static AttractionServiceResponse from(CustomAttraction c) {
		return new AttractionServiceResponse(c.getTitle(), c.getAreaCode(), c.getSiGunGuCode(), c.getLatitude(),
				c.getLongitude(), true);
	}
}
