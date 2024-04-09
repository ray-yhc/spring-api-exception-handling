package com.example.apiexception.exception;

import com.example.apiexception.constant.ResponseCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {
    private final HttpStatus httpStatus;

    public CustomException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public CustomException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.httpStatus = responseCode.getHttpStatus();
    }
}
