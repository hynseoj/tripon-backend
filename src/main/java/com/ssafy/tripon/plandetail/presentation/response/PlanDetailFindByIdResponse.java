package com.ssafy.tripon.plandetail.presentation.response;

import com.ssafy.tripon.attraction.application.AttractionServiceResponse;
import com.ssafy.tripon.plandetail.application.PlanDetailServiceResponse;
import java.util.List;

public record PlanDetailFindByIdResponse(
		Integer id,
		Integer day,
		List<AttractionServiceResponse> attractions
) {
	
	public static PlanDetailFindByIdResponse from(PlanDetailServiceResponse response) {
		return new PlanDetailFindByIdResponse(
				response.getId(),
				response.getDay(),
				response.getAttractions().stream().map(r -> new AttractionServiceResponse(r.getId(),
						r.getTitle(), r.getAreaCode(), r.getSiGunGuCode(), r.getLatitude(), r.getLongitude(),
						r.getImageUrl(), r.getAddress(), false)).toList()
		);
	}
}
