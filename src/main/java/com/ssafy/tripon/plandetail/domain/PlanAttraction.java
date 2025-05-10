package com.ssafy.tripon.plandetail.domain;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlanAttraction {
	
	private Integer id;
	private Integer planDetailId;
	private Integer attractionId;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	public PlanAttraction(Integer planDetailId, Integer attractionId) {
		this.planDetailId = planDetailId;
		this.attractionId = attractionId;
	}

}
