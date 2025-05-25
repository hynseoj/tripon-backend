package com.ssafy.tripon.plandetail.application;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PlanDetailServiceResponse {
	private Integer id;
	private Integer day;
	private List<PlanAttractionServiceResponse> attractions;

	@Getter
	@Setter
	@ToString
	public static class PlanAttractionServiceResponse {
		private Integer id;
		private String title;
		private Integer areaCode;
		private Integer siGunGuCode;
		private Double latitude;
		private Double longitude;
		private String imageUrl;
		private String address;
	}
}
