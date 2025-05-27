package com.ssafy.tripon.plan.application;

import com.ssafy.tripon.plan.domain.Plan;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public record PlanServiceResponse(
		Integer planId,
		String title,
		LocalDate startDate,
		LocalDate endDate,
		String memo,
		Long version,
		List<Integer> details,
		LocalDateTime createdAt,
		LocalDateTime updatedAt
	) {
	
	public static PlanServiceResponse from(Plan plan, List<Integer> details) {
	    return new PlanServiceResponse(
				plan.getId(),
				plan.getTitle(),
				plan.getStartDate(),
				plan.getEndDate(),
				plan.getMemo(),
				plan.getVersion(),
				details,
				plan.getCreatedAt(),
				plan.getUpdatedAt()
		);
	}

	public static PlanServiceResponse from(Plan plan) {
		return new PlanServiceResponse(
				plan.getId(),
				plan.getTitle(),
				plan.getStartDate(),
				plan.getEndDate(),
				plan.getMemo(),
				plan.getVersion(),
				Collections.emptyList(),
				plan.getCreatedAt(),
				plan.getUpdatedAt()
		);
	}
}
