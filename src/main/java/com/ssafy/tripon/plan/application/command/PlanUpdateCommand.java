package com.ssafy.tripon.plan.application.command;

import com.ssafy.tripon.plan.domain.Plan;

public record PlanUpdateCommand(
		Integer id,
		String title,
		String startDate,
		String endDate,
		String memo
	) {
	public Plan toPlan() {
		return new Plan(id, title, startDate, endDate, memo);
	}
}
