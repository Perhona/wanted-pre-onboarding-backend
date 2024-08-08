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
}
