package com.ssafy.tripon.plandetail.application.command;

import java.util.List;

import com.ssafy.tripon.attraction.application.command.AttractionSaveCommand;
import com.ssafy.tripon.plandetail.domain.PlanDetail;

public record PlanDetailSaveCommand(
		Integer planId,
		Integer day,
		List<AttractionSaveCommand> attractions
		) {

	public PlanDetail toPlanDetail() {
		return new PlanDetail(planId, day);
	}

}
