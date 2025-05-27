package com.ssafy.tripon.plan.presentation;

import com.ssafy.tripon.plan.application.PlanCollabService;
import com.ssafy.tripon.plan.application.message.PlanEditMessage;
import com.ssafy.tripon.plan.application.message.PlanUpdateBroadcast;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class PlanCollabController {

    private final SimpMessagingTemplate messagingTemplate;
    private final PlanCollabService planCollabService;

    /**
     * 클라이언트가 "/app/plan.edit" 로 보낸 편집 이벤트를 처리하고,
     * "/exchange/amq.topic/plan.{planId}" 구독자들에게 브로드캐스트합니다.
     */
    @MessageMapping("/plan.edit")
    public void handlePlanEdit(PlanEditMessage message) throws Exception {
        PlanUpdateBroadcast broadcast = planCollabService.applyEvent(message);
        messagingTemplate.convertAndSend(
                "/exchange/amq.topic/plan." + message.planId(),
                broadcast
        );
    }
}
