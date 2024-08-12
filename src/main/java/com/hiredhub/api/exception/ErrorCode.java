package com.hiredhub.api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    COMPANY_NOT_FOUND(HttpStatus.BAD_REQUEST, "회사를 찾을 수 없습니다."),
    JOB_POSTING_NOT_FOUND(HttpStatus.BAD_REQUEST, "채용 공고를 찾을 수 없습니다."),
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "사용자를 찾을 수 없습니다."),
    APPLICATION_NOT_FOUND(HttpStatus.BAD_REQUEST, "지원 내역을 찾을 수 없습니다."),

    POSITION_CANNOT_BE_EMPTY(HttpStatus.BAD_REQUEST, "채용 포지션은 비어 있을 수 없습니다."),
    INVALID_REWARD_AMOUNT(HttpStatus.BAD_REQUEST, "채용 보상금은 0원 이상이어야 합니다."),
    TECH_STACK_CANNOT_BE_EMPTY(HttpStatus.BAD_REQUEST, "사용 기술은 비어 있을 수 없습니다."),
    JOB_DESCRIPTION_CANNOT_BE_EMPTY(HttpStatus.BAD_REQUEST, "직무 설명은 비어 있을 수 없습니다."),
    APPLICATION_DUPLICATED(HttpStatus.BAD_REQUEST, "해당 공고에 이미 지원 내역이 있습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
