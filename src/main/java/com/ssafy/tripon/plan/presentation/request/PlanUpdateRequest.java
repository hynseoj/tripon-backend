package com.ssafy.tripon.plan.presentation.request;

import java.time.LocalDate;

import com.ssafy.tripon.plan.application.command.PlanUpdateCommand;

public record PlanUpdateRequest(
			String title,
			LocalDate startDate,
			LocalDate endDate,
			String memo
		) {
	
	public PlanUpdateCommand toCommand(Integer planId) {
		return new PlanUpdateCommand(planId, title, startDate, endDate, memo);
	}
}
