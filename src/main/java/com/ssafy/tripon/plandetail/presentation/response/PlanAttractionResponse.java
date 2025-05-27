package com.ssafy.tripon.plandetail.presentation.response;

public record PlanAttractionResponse(
		Integer id,
		String title,
		Integer orderNumber,
		Integer areaCode, 
		Integer siGunGuCode, 
		Double latitude, 
		Double longitude,
		String imageUrl,
		String address,
		Boolean isCustom // 커스텀 관광지 여부
		) {

}
