package com.ssafy.tripon.plan.domain;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlanShareLinkRepository {

    void savePlanLink(PlanShareLink link);
    PlanShareLink findLinkByPlanIdAndToken(Integer planId, String token);
    PlanShareLink findLinkByToken(String token);
}
