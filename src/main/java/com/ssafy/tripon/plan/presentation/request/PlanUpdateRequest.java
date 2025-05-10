package com.ssafy.tripon.plan.presentation.request;

import com.ssafy.tripon.plan.application.command.PlanUpdateCommand;

public record PlanUpdateRequest(
			String title,
			String startDate,
			String endDate,
			String memo
		) {
	
	public PlanUpdateCommand toCommand(Integer planId) {
		return new PlanUpdateCommand(planId, title, startDate, endDate, memo);
	}
}
