package com.ssafy.tripon.plan.presentation.response;

import com.ssafy.tripon.plan.application.PlanServiceResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record PlanFindByIdResponse(
		Integer id,
		String title,
		LocalDate startDate,
		LocalDate endDate,
		String memo,
		Long version,
		List<Integer> details,
		LocalDateTime createdAt,
		LocalDateTime updatedAt
) {
	public static PlanFindByIdResponse from(PlanServiceResponse response) {
		return new PlanFindByIdResponse(
				response.planId(),
				response.title(),
				response.startDate(),
				response.endDate(),
				response.memo(),
				response.version(),
				response.details(),
				response.createdAt(),
				response.updatedAt()
		);
	}
}
