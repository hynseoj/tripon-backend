package com.ssafy.tripon.plan.application;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.ssafy.tripon.plan.domain.Plan;

public record PlanServiceResponse(
		Integer planId,
		String title,
		LocalDate startDate,
		LocalDate endDate,
		String memo,
		String createdAt,
		String updatedAt
	) {
	
	public static PlanServiceResponse from(Plan plan) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String formattedCreatedAt = plan.getCreatedAt().format(formatter);
		String formattedUpdatedAt = plan.getUpdatedAt().format(formatter);
	    return new PlanServiceResponse(plan.getId(), plan.getTitle(), plan.getStartDate(), plan.getEndDate(), plan.getMemo(), formattedCreatedAt, formattedUpdatedAt);
	}
}
