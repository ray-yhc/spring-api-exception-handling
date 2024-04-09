package com.example.apiexception.constant;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * API 예외 응답을 모두 모아 놓은 enum 클래스입니다.
 */
@Getter
public enum ResponseCode {

    // Common Errors
    SUCCESS(HttpStatus.OK, "Success"),

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    JSON_PARSE_ERROR(HttpStatus.BAD_REQUEST, "JSON 파싱에 실패했습니다."),
    TYPE_MISMATCH(HttpStatus.BAD_REQUEST, "값을 읽어오는 데 실패했습니다:"),
    MISSING_PARAMETER(HttpStatus.BAD_REQUEST, "필수 파라미터가 누락되었습니다."),

    NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 api입니다."),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예상치 못한 문제가 발생했습니다. 관리자에게 문의해주세요."),

    // Custom Errors
    // ...
    ;

    private final HttpStatus httpStatus;
    private final String message;

    ResponseCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
