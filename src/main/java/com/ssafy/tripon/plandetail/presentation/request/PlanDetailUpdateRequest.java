package com.ssafy.tripon.plandetail.presentation.request;

import java.util.List;

import com.ssafy.tripon.attraction.application.command.AttractionSaveCommand;
import com.ssafy.tripon.plandetail.application.command.PlanDetailUpdateCommand;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

public record PlanDetailUpdateRequest(
		@NotBlank
		@Positive
		Integer day,
		@Valid
		@NotEmpty(message = "관광지를 1개 이상 선택해주세요.")
		List<PlanAttractionSaveRequest> attractions
		) {
	public record PlanAttractionSaveRequest(
			@NotBlank(message = "관광지 이름을 입력해주세요.")
			String title,
			@NotBlank
			@Positive
			Double latitude,
			@NotBlank
			@Positive
			Double longitude,
			@NotBlank
			@Positive
			Integer areaCode,
			@NotBlank
			@Positive
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
