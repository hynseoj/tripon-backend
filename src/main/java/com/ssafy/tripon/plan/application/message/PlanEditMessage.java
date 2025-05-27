package com.ssafy.tripon.plan.application.message;

public record PlanEditMessage(
        Integer planId,
        String email,
        String eventType,
        String payload
) {
}
