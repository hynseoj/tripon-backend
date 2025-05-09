package com.ssafy.tripon.attraction.domain;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomAttraction extends Attraction {

	private Integer id;
	private String title;
	private Integer areaCode;
	private Integer siGunGuCode;
	private Double latitude;
	private Double longitude;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public CustomAttraction(String title, Integer areaCode, Integer siGunguCode, Double latitude, Double longitude) {
		this.title = title;
		this.areaCode = areaCode;
		this.siGunGuCode = siGunguCode;
		this.latitude = latitude;
		this.longitude = longitude;
	}

}
