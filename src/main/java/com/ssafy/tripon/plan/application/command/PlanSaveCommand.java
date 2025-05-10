package com.ssafy.tripon.plan.application.command;

import java.time.LocalDate;

import com.ssafy.tripon.plan.domain.Plan;

public record PlanSaveCommand(
		String memberId,
		String title,
		LocalDate startDate,
		LocalDate endDate,
		String memo

	) {
	
	public Plan toPlan() {
		return new Plan(memberId, title, startDate, endDate, memo);
	}
	
}
