package com.ssafy.tripon.plan.application.message;

public record PlanUpdateBroadcast(
        Integer planId,
        String eventType,
        String payload,
        Long version
) {
}
