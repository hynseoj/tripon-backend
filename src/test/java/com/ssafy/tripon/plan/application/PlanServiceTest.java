package com.ssafy.tripon.plan.application;

import com.ssafy.tripon.plan.application.command.PlanSaveCommand;
import com.ssafy.tripon.plan.application.command.PlanUpdateCommand;
import com.ssafy.tripon.plan.domain.Plan;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PlanServiceTest {

    @Autowired
    private PlanService planService;
    private PlanServiceResponse response;

    @DisplayName("계획을 저장할 수 있다")
    @Test
    void canSavePlan() {
        // given
        PlanSaveCommand command = new PlanSaveCommand(
                "admin@ssafy.com", "제주 여행", LocalDate.now(), LocalDate.now().plusDays(2), "제주도 메모"
        );

        // when
        Integer planId = planService.savePlan(command);

        // then
        response = planService.findPlanById(planId);
        assertThat(response.title()).isEqualTo("제주 여행");
    }

    @DisplayName("사용자 ID로 계획 목록을 조회할 수 있다")
    @Test
    void canFindAllPlanByMemberId() {
        // given
        String memberId = "admin@ssafy.com";
        planService.savePlan(new PlanSaveCommand(memberId, "A 여행", LocalDate.now(), LocalDate.now().plusDays(1), ""));
        planService.savePlan(new PlanSaveCommand(memberId, "B 여행", LocalDate.now(), LocalDate.now().plusDays(2), ""));

        // when
        List<PlanServiceResponse> plans = planService.findAllPlanByMemberId(memberId);

        // then
        assertThat(plans).hasSizeGreaterThanOrEqualTo(2);
    }

    @DisplayName("계획을 아이디로 조회할 수 있다")
    @Test
    void canFindPlanById() {
        // given
        PlanSaveCommand command = new PlanSaveCommand(
                "admin@ssafy.com", "부산 여행", LocalDate.now(), LocalDate.now().plusDays(3), "부산 메모"
        );
        Integer planId = planService.savePlan(command);

        // when
        response = planService.findPlanById(planId);

        // then
        assertThat(response.memo()).isEqualTo("부산 메모");
    }

    @DisplayName("계획을 수정할 수 있다")
    @Test
    void canUpdatePlan() {
        // given
        Integer planId = planService.savePlan(new PlanSaveCommand("admin@ssafy.com", "오래된 여행", LocalDate.now(), LocalDate.now().plusDays(1), ""));
        PlanUpdateCommand updateCommand = new PlanUpdateCommand(
        		planId, "업데이트된 여행", LocalDate.now(), LocalDate.now().plusDays(4), "업데이트 메모"
        );

        // when
        planService.updatePlanById(updateCommand);

        // then
        response = planService.findPlanById(planId);
        assertThat(response.title()).isEqualTo("업데이트된 여행");
        assertThat(response.memo()).isEqualTo("업데이트 메모");
    }

    @DisplayName("계획을 삭제할 수 있다")
    @Test
    void canDeletePlan() {
        // given
        Integer planId = planService.savePlan(new PlanSaveCommand("admin@ssafy.com", "삭제할 여행", LocalDate.now(), LocalDate.now().plusDays(1), ""));

        // when
        planService.deletePlanById(planId);

        // then
        List<PlanServiceResponse> plans = planService.findAllPlanByMemberId("admin@ssafy.com");
        assertThat(plans).noneMatch(plan -> plan.planId().equals(planId));
    }
}
