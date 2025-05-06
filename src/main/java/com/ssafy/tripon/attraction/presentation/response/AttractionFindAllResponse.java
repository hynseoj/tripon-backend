package com.ssafy.tripon.attraction.presentation.response;

import java.util.List;

import com.ssafy.tripon.attraction.application.AttractionServiceResponse;

public record AttractionFindAllResponse(
		List<AttractionServiceResponse> attractions
		) {

}
