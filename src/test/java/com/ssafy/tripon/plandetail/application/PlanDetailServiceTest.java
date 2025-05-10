package com.ssafy.tripon.plandetail.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.tripon.attraction.application.command.AttractionSaveCommand;
import com.ssafy.tripon.plan.application.command.PlanUpdateCommand;
import com.ssafy.tripon.plandetail.application.command.PlanDetailSaveCommand;
import com.ssafy.tripon.plandetail.application.command.PlanDetailUpdateCommand;
import com.ssafy.tripon.plandetail.domain.PlanDetailRepository;

@SpringBootTest
@Transactional
class PlanDetailServiceTest {

	@Autowired
	private PlanDetailService planDetailService;

	@Autowired
	private PlanDetailRepository planDetailRepository;

	private PlanDetailServiceResponse response;

	@DisplayName("N일차 계획을 저장할 수 있다")
	@Test
	void canSavePlanDetail() {
		// given
		List<AttractionSaveCommand> attractions = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			attractions.add(new AttractionSaveCommand("관광지" + i, 1, 1, 18.0, 19.0));
		}
		PlanDetailSaveCommand command = new PlanDetailSaveCommand(1, 1, attractions);

		// when
		Integer planDetailId = planDetailService.savePlanDetail(command);

		// then
		response = planDetailRepository.findPlanDetailById(planDetailId);
		assertThat(response).isNotNull();
		assertThat(response.getDay()).isEqualTo(1);
		assertThat(response.getAttractions()).hasSize(5);
		assertThat(response.getAttractions().get(0).getTitle()).isEqualTo("관광지0");

	}

	@DisplayName("N일차 계획을 아이디로 조회할 수 있다")
	@Test
	void canFindPlanDetailById() {
		// given
		// save
		List<AttractionSaveCommand> attractions = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			attractions.add(new AttractionSaveCommand("관광지" + i, 1, 1, 18.0, 19.0));
		}
		PlanDetailSaveCommand saveCommand = new PlanDetailSaveCommand(1, 1, attractions);

		Integer planDetailId = planDetailService.savePlanDetail(saveCommand);

		// when
		response = planDetailService.findPlanDetailById(planDetailId);

		// then
		assertThat(response).isNotNull();
		assertThat(response.getDay()).isEqualTo(1);
		assertThat(response.getAttractions()).hasSize(5);
		assertThat(response.getAttractions().get(0).getTitle()).isEqualTo("관광지0");
	}

	@DisplayName("N일차 계획을 수정할 수 있다")
	@Test
	void canUpdatePlanDetail() {
		// given
		// save
		List<AttractionSaveCommand> attractions = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			attractions.add(new AttractionSaveCommand("원래관광지" + i, 1, 1, 18.0, 19.0));
		}
		PlanDetailSaveCommand saveCommand = new PlanDetailSaveCommand(1, 1, attractions);

		Integer planDetailId = planDetailService.savePlanDetail(saveCommand);

		// update
		attractions = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			attractions.add(new AttractionSaveCommand("수정관광지" + i, 1, 1, 18.0, 19.0));
		}
		PlanDetailUpdateCommand updateCommand = new PlanDetailUpdateCommand(planDetailId, 1, attractions);

		// when
		planDetailService.updatePlanDetail(updateCommand);

		// then
		response = planDetailRepository.findPlanDetailById(planDetailId);
		assertThat(response).isNotNull();
		assertThat(response.getDay()).isEqualTo(1);
		assertThat(response.getAttractions()).hasSize(3);
		assertThat(response.getAttractions().get(0).getTitle()).isEqualTo("수정관광지0");
	}

	@DisplayName("N일차 계획을 삭제할 수 있다")
	@Test
	void canDeletePlanDetail() {
		// given
		// save
		List<AttractionSaveCommand> attractions = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			attractions.add(new AttractionSaveCommand("관광지" + i, 1, 1, 18.0, 19.0));
		}
		PlanDetailSaveCommand saveCommand = new PlanDetailSaveCommand(1, 1, attractions);

		Integer planDetailId = planDetailService.savePlanDetail(saveCommand);

		// when
		planDetailService.deletePlanDetail(planDetailId);

		// then
		response = planDetailRepository.findPlanDetailById(planDetailId);
		assertThat(response).isNull();
	}

}
