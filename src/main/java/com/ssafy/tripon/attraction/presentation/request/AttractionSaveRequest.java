package com.ssafy.tripon.attraction.presentation.request;

import com.ssafy.tripon.attraction.application.command.AttractionSaveCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AttractionSaveRequest (
		@NotBlank
		String title,

		@NotBlank
		String address,

		@NotNull
		Double latitude,

		@NotNull
		Double longitude
){

	public AttractionSaveCommand toCommand() {
		return new AttractionSaveCommand(title, address, latitude, longitude);
	}
}
