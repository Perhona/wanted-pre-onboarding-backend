package com.hiredhub.api.unit;

import com.hiredhub.api.exception.CustomException;
import com.hiredhub.api.exception.ErrorCode;
import com.hiredhub.api.model.Company;
import com.hiredhub.api.model.JobPosting;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;


public class JobPostingTest {

    @DisplayName("채용 포지션 업데이트_빈 값 에러")
    @Test
    void updatePosition() {
        JobPosting jobPosting = new JobPosting(1L, "백엔드 개발자", "한국", "서울", 10_000_000, "java", "백엔드 개발자를 모집합니다.", new Company(1L, "hiredhub"));
        assertThatThrownBy(() -> jobPosting.updatePosition(""))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(ErrorCode.POSITION_CANNOT_BE_EMPTY.getMessage());
    }

    @DisplayName("채용 보상금 업데이트_0원보다 적은 값 에러")
    @Test
    void updateReward() {
        JobPosting jobPosting = new JobPosting(1L, "백엔드 개발자", "한국", "서울", 10_000_000, "java", "백엔드 개발자를 모집합니다.", new Company(1L, "hiredhub"));
        assertThatThrownBy(() -> jobPosting.updateReward(-1))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(ErrorCode.INVALID_REWARD_AMOUNT.getMessage());
    }

    @DisplayName("사용 기술 업데이트_빈 값 에러")
    @Test
    void updateTechStack() {
        JobPosting jobPosting = new JobPosting(1L, "백엔드 개발자", "한국", "서울", 10_000_000, "java", "백엔드 개발자를 모집합니다.", new Company(1L, "hiredhub"));
        assertThatThrownBy(() -> jobPosting.updateTechStack(""))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(ErrorCode.TECH_STACK_CANNOT_BE_EMPTY.getMessage());
    }

    @DisplayName("직무 설명 업데이트_빈 값 에러")
    @Test
    void updateJobDescription() {
        JobPosting jobPosting = new JobPosting(1L, "백엔드 개발자", "한국", "서울", 10_000_000, "java", "백엔드 개발자를 모집합니다.", new Company(1L, "hiredhub"));
        assertThatThrownBy(() -> jobPosting.updateJobDescription(""))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(ErrorCode.JOB_DESCRIPTION_CANNOT_BE_EMPTY.getMessage());
    }
}
