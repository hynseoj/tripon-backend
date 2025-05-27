package com.ssafy.tripon.plandetail.presentation.request;

import com.ssafy.tripon.plandetail.application.command.PlanDetailSaveCommand;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;

public record PlanDetailSaveRequest(
		@NotNull
		@Positive
		Integer day,
		@Valid
		@NotNull
		@NotEmpty(message = "관광지를 1개 이상 선택해주세요.")
		List<PlanAttractionRequest> attractions
) {
	public PlanDetailSaveCommand toCommand(Integer planId) {
		return new PlanDetailSaveCommand(planId, day, attractions.stream().map(t -> t.toCommand()).toList());
	}
}
