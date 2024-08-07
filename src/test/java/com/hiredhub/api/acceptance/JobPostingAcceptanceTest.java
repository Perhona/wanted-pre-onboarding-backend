package com.hiredhub.api.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

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
    void createJob() {
        ExtractableResponse<Response> response = RestAssured.given()
                .body("")
                .when()
                .post("/jobPostings")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
        long id = response.jsonPath().getLong("id");

        RestAssured.given()
                .when()
                .get("/jobPostings/" + id)
                .then()
                .statusCode(HttpStatus.OK.value());
    }
}
