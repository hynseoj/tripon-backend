package com.ssafy.tripon.plan.presentation.response;

import java.util.List;

import com.ssafy.tripon.plan.application.PlanServiceResponse;

import lombok.Data;

public record PlanFindAllByMemberIdResponse(
	    List<PlanServiceResponse> plans,
	    int totalItems
	) {
	}
