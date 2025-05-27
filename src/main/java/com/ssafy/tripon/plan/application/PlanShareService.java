package com.ssafy.tripon.plan.application;

import com.ssafy.tripon.common.exception.CustomException;
import com.ssafy.tripon.common.exception.ErrorCode;
import com.ssafy.tripon.plan.domain.Plan;
import com.ssafy.tripon.plan.domain.PlanRepository;
import com.ssafy.tripon.plan.domain.PlanShareLink;
import com.ssafy.tripon.plan.domain.PlanShareLinkRepository;
import com.ssafy.tripon.plandetail.domain.PlanDetailRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PlanShareService {

    private final PlanRepository planRepository;
    private final PlanShareLinkRepository planShareLinkRepository;
    private final PlanDetailRepository planDetailRepository;

    public String createShareLink(Integer planId) {
        Plan plan = Optional.of(planRepository.findPlanById(planId)).orElseThrow(() -> new CustomException(ErrorCode.PLANS_NOT_FOUND));
        String token = UUID.randomUUID().toString();
        PlanShareLink link = new PlanShareLink(plan.getId(), token);

        planShareLinkRepository.savePlanLink(link);

        return token;
    }

    public PlanServiceResponse validateToken(String token) {
        PlanShareLink link = Optional.of(planShareLinkRepository.findLinkByToken(token)).orElseThrow(() -> new CustomException(ErrorCode.PLAN_SHARE_LINK_NOT_FOUND));

        if (link.getExpiresAt()!=null && link.getExpiresAt().isBefore(LocalDateTime.now()))
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);

        Plan plan = planRepository.findPlanById(link.getPlanId());
        List<Integer> details = planDetailRepository.findAllByPlanId(plan.getId());

        return PlanServiceResponse.from(plan, details);
    }
}
