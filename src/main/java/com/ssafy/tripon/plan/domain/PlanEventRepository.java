package com.ssafy.tripon.plan.domain;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlanEventRepository {
    /** 편집 이벤트 저장 */
    void insertEvent(PlanEvent event);
}
