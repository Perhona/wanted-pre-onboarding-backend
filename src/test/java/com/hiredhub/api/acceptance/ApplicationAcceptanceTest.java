package com.hiredhub.api.acceptance;

import com.hiredhub.api.dto.ApplicationRequest;
import com.hiredhub.api.dto.JobPostingRequest;
import com.hiredhub.api.model.User;
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

import java.util.ArrayList;

import static com.hiredhub.api.util.AcceptanceMethods.makeJobPosting;
import static org.assertj.core.api.Assertions.assertThat;

@Sql(value = "/table_truncate.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@DisplayName("채용 공고 지원 인수 테스트")
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ApplicationAcceptanceTest {
    /**
     * given 채용 공고 1개를 등록하고
     * when 지원자가 채용 공고에 지원하면
     * then 지원 내역에서 확인할 수 있다.
     */
    @DisplayName("채용 공고 지원")
    @Test
    void createJobApplication() {
        Long jobPostingId = makeJobPosting(new JobPostingRequest.CreateRequest("백엔드 개발자", "한국", "서울", 10_000_000, "java", "test", 1L)).jsonPath().getLong("id");
        User user = new User(1L, "김철수", new ArrayList<>());

        ExtractableResponse<Response> response = RestAssured
                .given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ApplicationRequest(user.getId(), jobPostingId))
                .when()
                .post("/applications")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract();

        assertThat(response.jsonPath().getLong("userId")).isEqualTo(user.getId());
        assertThat(response.jsonPath().getLong("jobPostingId")).isEqualTo(jobPostingId);
    }
}
