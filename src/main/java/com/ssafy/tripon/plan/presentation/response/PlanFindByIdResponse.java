package com.ssafy.tripon.plan.presentation.response;

public record PlanFindByIdResponse(
		Integer id,
		String title,
		String startDate,
		String endDate,
		String memo,
		String createdAt
	) {

}
