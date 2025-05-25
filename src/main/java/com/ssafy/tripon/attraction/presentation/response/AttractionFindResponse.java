package com.ssafy.tripon.attraction.presentation.response;

import com.ssafy.tripon.attraction.application.AttractionServiceResponse;

public record AttractionFindResponse(
		Integer id,
        String title,
        Integer areaCode,
        Integer siGunGuCode,
        Double latitude,
        Double longitude,
        Boolean isCustom
) {
    public static AttractionFindResponse from(AttractionServiceResponse response) {
        return new AttractionFindResponse(
        		response.id(),
                response.title(),
                response.areaCode(),
                response.siGunGuCode(),
                response.latitude(),
                response.longitude(),
                response.isCustom()
        );
    }
}
