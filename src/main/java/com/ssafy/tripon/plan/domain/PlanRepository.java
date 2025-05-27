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



	/** 현재 버전 조회 */
	Long selectVersion(@Param("id") Integer id);

	/**
	 * 제목/메모 동시 수정 (버전 체크)
	 * -> WHERE version = #{version} 이 만족할 때만 version++, title, memo 업데이트
	 */
	int updateTitleAndMemoWithVersion(
			@Param("id")      Integer id,
			@Param("title")   String title,
			@Param("memo")    String memo,
			@Param("version") Long version
	);
}
