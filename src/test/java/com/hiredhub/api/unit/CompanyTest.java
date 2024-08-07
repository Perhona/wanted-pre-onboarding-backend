package com.hiredhub.api.unit;

import com.hiredhub.api.model.Company;
import com.hiredhub.api.model.JobPosting;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}
