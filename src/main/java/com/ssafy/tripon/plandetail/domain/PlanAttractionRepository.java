package com.ssafy.tripon.plandetail.domain;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlanAttractionRepository {
	
	// 생성
	void savePlanAttraction(PlanAttraction planAttraction);
	// 삭제
	void deletePlanAttractionByPlanDetailId(Integer planDetailId);
}
