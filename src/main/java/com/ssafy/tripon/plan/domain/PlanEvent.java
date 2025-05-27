package com.ssafy.tripon.plan.domain;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlanEvent {

    private Long id;
    private Integer planId;
    private String email;
    private String eventType;
    private String payload;
    private LocalDateTime createdAt;

    public PlanEvent(Integer planId, String email, String eventType, String payload) {
        this.planId = planId;
        this.email = email;
        this.eventType = eventType;
        this.payload = payload;
    }
}
