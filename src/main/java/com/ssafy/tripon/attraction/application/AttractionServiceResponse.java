package com.ssafy.tripon.attraction.application;

import com.ssafy.tripon.attraction.domain.Attraction;
import com.ssafy.tripon.attraction.domain.CustomAttraction;

public record AttractionServiceResponse(
		Integer id,
		String title,
		Integer areaCode, 
		Integer siGunGuCode, 
		Double latitude, 
		Double longitude,
		String imageUrl,
		String address,
		Boolean isCustom // 커스텀 관광지 여부
) {

	public static AttractionServiceResponse from(Attraction a) {
		if (a instanceof CustomAttraction) {
			return new AttractionServiceResponse(a.getNo(), a.getTitle(), a.getAreaCode(), a.getSiGunGuCode(), a.getLatitude(),
					a.getLongitude(), a.getFirstImage1(), a.getAddr1(),true);
		}
		return new AttractionServiceResponse(a.getNo(), a.getTitle(), a.getAreaCode(), a.getSiGunGuCode(), a.getLatitude(),
				a.getLongitude(), a.getFirstImage1(), a.getAddr1(),false);
	}
}
