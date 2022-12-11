package com.example.restkube.domian.error;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.stream.Stream;

import static java.lang.Integer.MIN_VALUE;

@Getter
@RequiredArgsConstructor
public enum ApiErrorMessage {

    RESOURCE_HANDLER_NOT_FOUND("Could not find handler for endpoint", HttpStatus.NOT_FOUND.value()),
    RESOURCE_NOT_FOUND("Resource not found", MIN_VALUE),
    BAD_REQUEST("Bad Request", HttpStatus.BAD_REQUEST.value()),
    FORBIDDEN("Forbidden", HttpStatus.FORBIDDEN.value()),
    INTERNAL_SERVER_ERROR("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR.value());

    @NonNull
    private final String message;
    private final int status;

    public static String getMessageByStatus(@NonNull HttpStatus httpStatus) {
        return Stream.of(ApiErrorMessage.values())
                .filter(error -> error.getStatus() == httpStatus.value())
                .findFirst()
                .map(ApiErrorMessage::getMessage)
                .orElseGet(httpStatus::getReasonPhrase);
    }

}
