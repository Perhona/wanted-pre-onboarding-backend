package com.hiredhub.api.util;

import com.hiredhub.api.dto.JobPostingRequest;
import com.hiredhub.api.dto.JobPostingSearchRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

public class AcceptanceMethods {
    public static ExtractableResponse<Response> makeJobPosting(JobPostingRequest.CreateRequest jobPostingCreateRequest) {
        return RestAssured
                .given()
                .body(jobPostingCreateRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/jobPostings")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
    }

    public static ExtractableResponse<Response> getJobPosting(Long id) {
        return RestAssured
                .given()
                .when()
                .get("/jobPostings/" + id)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract();
    }

    public static ExtractableResponse<Response> listAllJobPostings() {
        return RestAssured
                .given()
                .when()
                .get("/jobPostings")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract();
    }

    public static void deleteJobPosting(Long id) {
        RestAssured
                .given()
                .when()
                .delete("/jobPostings/" + id)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    public static ExtractableResponse<Response> updateJobPosting(Long id, JobPostingRequest.UpdateRequest jobPostingUpdateRequest) {
        return RestAssured
                .given()
                .body(jobPostingUpdateRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .put("/jobPostings/" + id)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract();
    }

    public static ExtractableResponse<Response> searchJobPostings(JobPostingSearchRequest jobPostingSearchRequest) {
        Map<String, String> params = new HashMap<>();
        params.put("position", jobPostingSearchRequest.position());
        params.put("companyName", jobPostingSearchRequest.companyName());

        return RestAssured
                .given()
                .params(params)
                .when()
                .get("/jobPostings/search")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract();
    }
}
