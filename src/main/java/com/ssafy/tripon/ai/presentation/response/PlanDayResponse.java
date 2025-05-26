package com.ssafy.tripon.ai.presentation.response;

import java.util.List;

public record PlanDayResponse(
    int day,
    List<AttractionResponse> attractions
) {}

