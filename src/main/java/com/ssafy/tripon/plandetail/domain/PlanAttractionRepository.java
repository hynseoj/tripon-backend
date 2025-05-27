package com.ssafy.tripon.plandetail.domain;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlanAttractionRepository {
	
	// 생성
	void savePlanAttraction(PlanAttraction planAttraction);
	// 개수 조회
	int countPlanAttractionByPlanDetailId(Integer planDetailId);
	// 삭제
	void deletePlanAttractionByPlanDetailId(Integer planDetailId);

	void deletePlanAttractionByPlanDetailIdAndAttractionId(Integer planDetailId, Integer attractionId);
	
	// 조회
	PlanAttraction findPlanAttraction(Integer planDetailId, Integer attractionId) ;
}
