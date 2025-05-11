package com.ssafy.tripon.attraction.presentation.request;

import com.ssafy.tripon.attraction.application.command.AttractionSaveCommand;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record AttractionSaveRequest (
		@NotBlank
		String title,
		@NotBlank
		@Positive
		Integer areaCode,
		@NotBlank
		@Positive
		Integer siGunGuCode,
		@NotBlank
		@Positive
		Double latitude,
		@NotBlank
		@Positive
		Double longitude
	){

	public AttractionSaveCommand toCommand() {
		return new AttractionSaveCommand(title, areaCode, siGunGuCode, latitude, longitude);
	}
}
