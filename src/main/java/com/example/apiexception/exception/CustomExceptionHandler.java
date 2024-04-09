package com.example.apiexception.exception;

import com.example.apiexception.constant.ResponseCode;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleCustomException(CustomException e, WebRequest request) {
        return handleExceptionInternal(e, e.getHttpStatus(), request);
    }

    ///////////////////////////////////////////////
    // todo: 이 곳에 예외처리 코드를 작성해주세요 ///////



    ///////////////////////////////////////////////
    /**
     * 404 Not Found <br>
     * No handler found
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return handleExceptionInternal(ex, ResponseCode.NOT_FOUND, request);
    }

    private ResponseEntity<Object> handleExceptionInternal(Exception e, ResponseCode responseCode, WebRequest request) {
        return handleExceptionInternal(e, responseCode.getMessage(), responseCode.getHttpStatus(), request);
    }

    private ResponseEntity<Object> handleExceptionInternal(Exception e, HttpStatus httpStatus, WebRequest request) {
        return handleExceptionInternal(e, e.getMessage(), httpStatus, request);
    }

    private ResponseEntity<Object> handleExceptionInternal(Exception e, String message, HttpStatus httpStatus, WebRequest request) {
        return handleExceptionInternal(e,
                ExceptionResponse.builder()
                        .code(httpStatus.value())
                        .error(httpStatus.getReasonPhrase())
                        .message(message)
                        .path(((ServletWebRequest) request).getRequest().getRequestURI())
                        .build()
                , HttpHeaders.EMPTY, httpStatus, request);
    }
}

@Builder
@Getter
class ExceptionResponse {
    private final int code;
    private final String error;
    private final String message;
    private final String path;

    public ExceptionResponse(int code, String error, String message, String path) {
        this.code = code;
        this.error = error;
        this.message = message;
        this.path = path;
    }
}