package com.ssafy.tripon.plandetail.presentation.request;

import java.util.List;

import com.ssafy.tripon.attraction.application.command.AttractionSaveCommand;
import com.ssafy.tripon.plandetail.application.command.PlanDetailUpdateCommand;

public record PlanDetailUpdateRequest(
		Integer day,
		List<PlanAttractionSaveRequest> attractions
		) {
	public record PlanAttractionSaveRequest(
			String title,
			Double latitude,
			Double longitude,
			Integer areaCode,
			Integer siGunGuCode
			) {
		
		public AttractionSaveCommand toCommand() {
			return new AttractionSaveCommand(title, areaCode, siGunGuCode, latitude, longitude);
		}
	}
	
	public PlanDetailUpdateCommand toCommand(Integer id) {
		return new PlanDetailUpdateCommand(id, day, 
				attractions.stream()
				.map(attraction -> attraction.toCommand()).toList());
	}
}
