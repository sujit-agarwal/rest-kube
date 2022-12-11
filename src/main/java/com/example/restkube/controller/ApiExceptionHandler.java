package com.example.restkube.controller;

import com.example.restkube.domian.error.*;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

import static java.util.Optional.ofNullable;
import static org.apache.commons.lang.StringUtils.substringAfter;
import static org.springframework.http.HttpStatus.*;


@ControllerAdvice
@Slf4j
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private final ResponseEntity<Object> badResponse;
    private final ResponseEntity<Object> internalServerErrorResponse;
    private final ResponseEntity<Object> notFoundResponse;
    private final ResponseEntity<Object> notAcceptableMediaType;

    public ApiExceptionHandler() {
        this.badResponse = new ResponseEntity<>(new ApiErrorResponse(createBadRequestBuilder().build()), BAD_REQUEST);
        this.internalServerErrorResponse = new ResponseEntity<>(new ApiErrorResponse(createInternalServerErrorBuilder().build()),
                INTERNAL_SERVER_ERROR);
        this.notFoundResponse = new ResponseEntity<>(new ApiErrorResponse(createResourceHandlerNotFoundError()), NOT_FOUND);
        this.notAcceptableMediaType = new ResponseEntity<>(new ApiErrorResponse(createNotAcceptableError()), createDefaultContentTypeHeaders(),
                HttpStatus.NOT_ACCEPTABLE);
    }

    private static HttpHeaders createDefaultContentTypeHeaders() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        return responseHeaders;
    }

    private static ApiError.ApiErrorBuilder createBadRequestBuilder() {
        return ApiError.builder()
                .code(ApiErrorCode.BAD_REQUEST.getCode())
                .message(ApiErrorMessage.BAD_REQUEST.getMessage())
                .severity(ApiErrorSeverity.ERROR);
    }

    private static ApiError createResourceHandlerNotFoundError() {
        return ApiError.builder()
                .code(ApiErrorCode.NOT_FOUND.getCode())
                .message(ApiErrorMessage.RESOURCE_HANDLER_NOT_FOUND.getMessage())
                .severity(ApiErrorSeverity.ERROR)
                .build();
    }

    private static ApiError createNotAcceptableError() {
        return createInternalServerErrorBuilder()
                .innerError(ApiError.ProductBeApiInnerError.builder()
                        .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
                        .message(HttpStatus.NOT_ACCEPTABLE.getReasonPhrase())
                        .severity(ApiErrorSeverity.ERROR).build())
                .build();
    }

    private static ApiError.ApiErrorBuilder createInternalServerErrorBuilder() {
        return ApiError.builder()
                .code(ApiErrorCode.SERVER_ERROR.getCode())
                .message(ApiErrorMessage.INTERNAL_SERVER_ERROR.getMessage())
                .severity(ApiErrorSeverity.ERROR);
    }

    private static ResponseEntity<Object> createProductBeApiErrorResponse(ResponseStatusException e) {

        final HttpStatus httpStatus = e.getStatus();
        final ApiError productBeApiError = ApiError.builder()
                .code(ApiErrorCode.getCodeByStatus(httpStatus))
                .message(ofNullable(e.getReason()).orElse(ApiErrorMessage.getMessageByStatus(httpStatus)))
                .severity(ApiErrorSeverity.getSeverityByStatus(httpStatus))
                .build();

        return new ResponseEntity<>(new ApiErrorResponse(productBeApiError), httpStatus);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return notFoundResponse;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        if (log.isDebugEnabled()) {
            log.debug("Exception occurred: {}", ex.toString());
        }

        final ApiError.ApiErrorBuilder builder = createBadRequestBuilder();
        ex.getBindingResult().getFieldErrors().forEach(error -> builder.innerError(ApiError.ProductBeApiInnerError.builder()
                .code(ApiErrorCode.BAD_REQUEST.getCode())
                .message(error.getDefaultMessage())
                .severity(ApiErrorSeverity.ERROR)
                .target(error.getField())
                .build()));

        return new ResponseEntity<>(new ApiErrorResponse(builder.build()), BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status,
                                                                      WebRequest request) {
        log.debug("Exception occurred: {}", ex.getMessage());
        return notAcceptableMediaType;
    }

    @ExceptionHandler(JsonMappingException.class)
    public ResponseEntity<Object> handleJsonMappingException(JsonMappingException e, WebRequest webRequest) {
        log.error("Exception occurred: {}", e.getOriginalMessage()); // log original message to avoid json source to be logged with sensitive data
        return badResponse;
    }

    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<Object> handleJsonParseException(JsonParseException e, WebRequest webRequest) {
        log.error("Exception occurred: {}", e.getOriginalMessage()); // log original message to avoid json source to be logged with sensitive data
        return badResponse;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception e, WebRequest webRequest) {
        Throwable cause = e.getCause();
        if (cause instanceof ResponseStatusException) {
            return handleResponseStatusException((ResponseStatusException) cause);
        }
        log.error("Exception occurred: ", e);
        return internalServerErrorResponse;
    }

    /**
     * Handles ResponseStatusException exception and return  API Error Response model
     *
     * @param e occurred exception
     * @return response entity with API error
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException e) {
        log.debug("Exception occurred: {}", e.getMessage());
        return createProductBeApiErrorResponse(e);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> constraintViolationException(ConstraintViolationException e) {
        log.debug("Validation Exception occurred: {}", e.getMessage());

        final ApiError.ApiErrorBuilder builder = createBadRequestBuilder();
        e.getConstraintViolations().forEach(constraint -> builder.innerError(ApiError.ProductBeApiInnerError.builder()
                .code(ApiErrorCode.BAD_REQUEST.getCode())
                .message(constraint.getMessage())
                .severity(ApiErrorSeverity.ERROR)
                .target(substringAfter(constraint.getPropertyPath().toString(), "."))
                .build()));

        return new ResponseEntity<>(new ApiErrorResponse(builder.build()), BAD_REQUEST);
    }

}
