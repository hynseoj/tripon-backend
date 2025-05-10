package com.ssafy.tripon.plandetail.domain;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlanDetail {
	private Integer id;
	private Integer planId;
	private Integer day;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private List<PlanAttraction> planAttractions;
	
	public PlanDetail(Integer planId, Integer day) {
		this.planId = planId;
		this.day = day;
	}
}
