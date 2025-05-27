package com.ssafy.tripon.plandetail.application.command;

import java.util.List;

public record PlanDetailUpdateCommand(
		Integer id,
		Integer day,
		List<PlanAttractionCommand> attractions
		) {
}
