package com.hiredhub.api.util;

import com.hiredhub.api.dto.JobPostingRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class AcceptanceMethods {
    public static ExtractableResponse<Response> makeJobPosting(JobPostingRequest jobPostingRequest) {
        return RestAssured
                .given()
                .body(jobPostingRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/jobPostings")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
    }

}
