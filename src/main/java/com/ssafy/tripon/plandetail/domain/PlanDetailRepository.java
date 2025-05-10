package com.ssafy.tripon.plandetail.domain;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.tripon.plandetail.application.PlanDetailServiceResponse;

@Mapper
public interface PlanDetailRepository {

	// N일차 계획 생성
	Integer savePlanDetail(PlanDetail command);
	// N일차 계획 상세 조회
	PlanDetailServiceResponse findPlanDetailById(Integer planDetailId);
	// N일차 계획 수정
	void updatePlanDetail(PlanDetail command);
	// N일차 계획 삭제
	void deletePlanDetail(Integer id);

}
