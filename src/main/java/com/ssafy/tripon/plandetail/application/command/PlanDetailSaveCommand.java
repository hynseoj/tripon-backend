package com.ssafy.tripon.plandetail.application.command;

import com.ssafy.tripon.plandetail.domain.PlanDetail;
import java.util.List;

public record PlanDetailSaveCommand(
		Integer planId,
		Integer day,
		List<PlanAttractionCommand> attractions
		) {

	public PlanDetail toPlanDetail() {
		return new PlanDetail(planId, day);
	}

}
