package com.ssafy.tripon.attraction.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Attraction {
	
	private Integer no;
	private Integer contentId;
	private String title;
	private Integer contentTypeId;
	private Integer areaCode;
	private Integer siGunGuCode;
	private String FirstImage1;
	private String FirstImage2;
	private Integer map_level;
	private Double latitude;
	private Double longitude;
	private String tel;
	private String addr1;
	private String addr2;
	private String homepage;
	private String overview;
	
	public Attraction(Integer no, String title, Integer areaCode, Integer siGunguCode, Double latitude, Double longitude) {
		this.no = no;
		this.title = title;
		this.areaCode = areaCode;
		this.siGunGuCode = siGunguCode;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public Attraction(String title, Integer areaCode, Integer siGunguCode, Double latitude, Double longitude) {
		this.title = title;
		this.areaCode = areaCode;
		this.siGunGuCode = siGunguCode;
		this.latitude = latitude;
		this.longitude = longitude;
	}
}
