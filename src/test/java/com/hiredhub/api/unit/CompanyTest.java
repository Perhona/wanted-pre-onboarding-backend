package com.hiredhub.api.unit;

import com.hiredhub.api.model.Company;
import com.hiredhub.api.model.JobPosting;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CompanyTest {
    @DisplayName("채용 공고 등록")
    @Test
    void addJopPosting() {
        Company company = new Company(1L, "hiredhub");
        JobPosting jobPosting = new JobPosting(1L, "백엔드 개발자", "한국", "서울", 10_000_000, "java", "백엔드 개발자를 모집합니다.", company);

        company.addJobPosting(jobPosting);
        assertThat(company.getJobPostings()).contains(jobPosting);
    }

    @DisplayName("회사의 다른 채용 공고 id 조회")
    @Test
    void getOtherJobPostingIds() {
        Company company = new Company(1L, "hiredhub");
        JobPosting jobPosting = new JobPosting(1L, "백엔드 개발자", "한국", "서울", 10_000_000, "java", "백엔드 개발자를 모집합니다.", company);
        JobPosting jobPosting2 = new JobPosting(2L, "백엔드 개발자", "한국", "서울", 10_000_000, "java", "백엔드 개발자를 모집합니다.", company);
        company.addJobPosting(jobPosting);
        company.addJobPosting(jobPosting2);

        List<Long> otherJobPostingIds = company.getOtherJobPostingIds(jobPosting.getId());
        assertThat(otherJobPostingIds).hasSize(1);
    }
}
