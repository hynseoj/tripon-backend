package com.ssafy.tripon.plan.domain;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlanShareLink {

    private Long id;
    private Integer planId;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;

    public PlanShareLink(Integer planId, String token) {
        this.planId = planId;
        this.token = token;
    }
}
