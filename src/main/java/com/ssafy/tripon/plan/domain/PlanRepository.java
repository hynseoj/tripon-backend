package com.ssafy.tripon.plan.domain;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PlanRepository {

	// 계획 생성
	void savePlan(Plan plan);

	// 계획 전체 조회
	List<Plan> findAllPlanByMemberId(String memberId);

	// 계획 상세 조회
	Plan findPlanById(Integer id);

	// 계획 수정
	int updatePlan(Plan plan);

	// 계획 삭제
	int deletePlanById(Integer id);
	
	List<Plan> findPlansByMemberEmail(@Param("email") String email, @Param("offset") int offset, @Param("limit") int limit);

	int countPlansByMemberEmail(@Param("email") String email);

	
}
