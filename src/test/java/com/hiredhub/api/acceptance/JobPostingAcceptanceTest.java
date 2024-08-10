package com.hiredhub.api.acceptance;

import com.hiredhub.api.dto.JobPostingRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static com.hiredhub.api.util.AcceptanceMethods.makeJobPosting;
import static org.assertj.core.api.Assertions.assertThat;

@Sql(value = "/table_truncate.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@DisplayName("채용 공고 테스트")
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class JobPostingAcceptanceTest {
    /**
     * when 채용 공고를 등록하면
     * then 채용 공고 목록에서 조회할 수 있다.
     */
    @DisplayName("채용 공고 등록")
    @Test
    void createJobPosting() {
        JobPostingRequest jobPostingRequest = new JobPostingRequest("백엔드 개발자", "한국", "서울", 10_000_000, "java", "test", 1L);

        ExtractableResponse<Response> response = RestAssured
                .given()
                .body(jobPostingRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/jobPostings")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
        long id = response.jsonPath().getLong("id");

        RestAssured
                .given()
                .when()
                .get("/jobPostings/" + id)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    /**
     * given 채용 공고를 등록하고
     * when 채용 공고 목록를 조회하면
     * then 생성한 채용 공고 id를 응답받을 수 있다.
     */
    @DisplayName("공고 목록 조회")
    @Test
    void listJobPostings() {
        Long jobPostingId1 = makeJobPosting(new JobPostingRequest("백엔드 개발자", "한국", "서울", 10_000_000, "java", "test", 1L)).jsonPath().getLong("id");
        Long jobPostingId2 = makeJobPosting(new JobPostingRequest("백엔드 개발자", "한국", "판교", 500_000, "python", "test", 1L)).jsonPath().getLong("id");

        ExtractableResponse<Response> response = RestAssured
                .given()
                .when()
                .get("/jobPostings")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract();
        assertThat(response.jsonPath().getList("id", Long.class)).contains(jobPostingId1, jobPostingId2);
    }

    /**
     * given 채용 공고를 2개 등록하고
     * when 채용 공고 상세를 조회하면
     * then 생성한 채용 공고의 id, 공고 내용, 다른 채용 공고를 응답 받을 수 있다.
     */
    @DisplayName("채용 공고 상세 조회")
    @Test
    void getJobPosting() {
        Long jobPostingId = makeJobPosting(new JobPostingRequest("백엔드 개발자", "한국", "서울", 10_000_000, "java", "test", 1L)).jsonPath().getLong("id");
        makeJobPosting(new JobPostingRequest("백엔드 개발자", "한국", "판교", 500_000, "python", "test", 1L));

        ExtractableResponse<Response> response = RestAssured
                .given()
                .when()
                .get("/jobPostings/" + jobPostingId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract();

        assertThat(response.jsonPath().getLong("id")).isEqualTo(jobPostingId);
        assertThat(response.jsonPath().getString("jobDescription")).isEqualTo("test");
        assertThat(response.jsonPath().getList("companyOtherJobPostingIds", Long.class)).hasSize(1);
    }

    /**
     * given 채용 공고를 1개 등록하고
     * when 채용 공고를 수정하면
     * then 수정된 내용을 확인할 수 있다.
     */
    @DisplayName("채용 공고 수정")
    @Test
    void updateJobPosting() {
        Long jobPostingId = makeJobPosting(new JobPostingRequest("백엔드 개발자", "한국", "서울", 10_000_000, "java", "test", 1L)).jsonPath().getLong("id");

        ExtractableResponse<Response> response = RestAssured
                .given()
                .body(new JobPostingRequest.UpdateRequest("프론트엔드 개발자", 10_000_000, "JavaScript", "자바 스크립트 프론트엔드 개발자를 모집합니다."))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .put("/jobPostings/" + jobPostingId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract();

        assertThat(response.jsonPath().getLong("id")).isEqualTo(jobPostingId);
        assertThat(response.jsonPath().getString("position")).isEqualTo("프론트엔드 개발자");
        assertThat(response.jsonPath().getInt("reward")).isEqualTo(10_000_000);
        assertThat(response.jsonPath().getString("techStack")).isEqualTo("JavaScript");
        assertThat(response.jsonPath().getString("jobDescription")).isEqualTo("자바 스크립트 프론트엔드 개발자를 모집합니다.");
    }

    /**
     * given 채용 공고를 1개 등록하고
     * when 채용 공고를 삭제하면
     * then 삭제된 채용 공고를 조회할 수 없다.
     */
    @DisplayName("채용 공고 삭제")
    @Test
    void deleteJobPosting() {
        Long jobPostingId = makeJobPosting(new JobPostingRequest("백엔드 개발자", "한국", "서울", 10_000_000, "java", "test", 1L)).jsonPath().getLong("id");
        makeJobPosting(new JobPostingRequest("백엔드 개발자", "한국", "판교", 500_000, "python", "test", 1L));

        RestAssured
                .given()
                .when()
                .delete("/jobPostings/" + jobPostingId)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        RestAssured
                .given()
                .when()
                .get("/jobPostings/" + jobPostingId)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        ExtractableResponse<Response> response = RestAssured
                .given()
                .when()
                .get("/jobPostings")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract();

        assertThat(response.jsonPath().getList("id", Long.class)).hasSize(1);
    }
}
