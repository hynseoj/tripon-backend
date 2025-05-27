package com.ssafy.tripon.plandetail.presentation.request;

import com.ssafy.tripon.plandetail.application.command.PlanAttractionCommand;

public record PlanAttractionRequest(
		Integer id,
		Integer orderNumber
		) {
	public PlanAttractionCommand toCommand() {
		return new PlanAttractionCommand(id, orderNumber);
	}
}
