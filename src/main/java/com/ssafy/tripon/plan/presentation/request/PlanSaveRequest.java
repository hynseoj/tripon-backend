package com.ssafy.tripon.plan.presentation.request;

import java.time.LocalDate;

import com.ssafy.tripon.plan.application.command.PlanSaveCommand;

public record PlanSaveRequest(
		String title,
		LocalDate startDate,
		LocalDate endDate,
		String memo
	) {
	
	public PlanSaveCommand toCommand(String memberId) {
		return new PlanSaveCommand(memberId, title, startDate, endDate, memo);
	}
}
