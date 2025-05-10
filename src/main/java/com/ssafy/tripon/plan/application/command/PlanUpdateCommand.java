package com.ssafy.tripon.plan.application.command;

import java.time.LocalDate;

import com.ssafy.tripon.plan.domain.Plan;

public record PlanUpdateCommand(
		Integer id,
		String title,
		LocalDate startDate,
		LocalDate endDate,
		String memo
	) {
	public Plan toPlan() {
		return new Plan(id, title, startDate, endDate, memo);
	}
}
