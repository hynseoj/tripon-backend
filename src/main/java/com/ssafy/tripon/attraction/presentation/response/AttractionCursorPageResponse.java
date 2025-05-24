package com.ssafy.tripon.attraction.presentation.response;

import com.ssafy.tripon.attraction.application.AttractionCursorPage;

public record AttractionCursorPageResponse(
		AttractionCursorPage attractions
) {
	public static AttractionCursorPageResponse from(AttractionCursorPage attractions) {
		return new AttractionCursorPageResponse(attractions);
	}
}
