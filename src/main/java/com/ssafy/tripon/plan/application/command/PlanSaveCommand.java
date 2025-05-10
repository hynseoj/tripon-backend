package com.ssafy.tripon.plan.application.command;

import com.ssafy.tripon.plan.domain.Plan;

public record PlanSaveCommand(
		String memberId,
		String title,
		String startDate,
		String endDate,
		String memo

	) {
	
	public Plan toPlan() {
		return new Plan(memberId, title, startDate, endDate, memo);
	}
	
}
