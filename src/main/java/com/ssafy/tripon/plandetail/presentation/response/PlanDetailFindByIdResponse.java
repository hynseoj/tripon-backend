package com.ssafy.tripon.plandetail.presentation.response;

import java.util.List;

import com.ssafy.tripon.plandetail.application.PlanDetailServiceResponse;
import com.ssafy.tripon.plandetail.application.PlanDetailServiceResponse.PlanAttractionServiceResponse;

public record PlanDetailFindByIdResponse(
		Integer id,
		Integer day,
		List<PlanAttractionFindByIdResponse> attractions
		) {
	
	public record PlanAttractionFindByIdResponse(
			String title,
			Double latitude,
			Double longitude
			) {
		public static PlanAttractionFindByIdResponse from(PlanAttractionServiceResponse response) {
			return new PlanAttractionFindByIdResponse(response.getTitle(), response.getLatitude(), response.getLongitude());
		}
	}
	
	public static PlanDetailFindByIdResponse from(PlanDetailServiceResponse response) {
		return new PlanDetailFindByIdResponse(response.getId(), response.getDay(), 
				response.getAttractions().stream().map(attr -> PlanAttractionFindByIdResponse.from(attr)).toList());
	}
}
