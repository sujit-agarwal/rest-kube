package com.example.restkube.domian.error;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum ApiErrorCode {

    BAD_REQUEST("BadRequest", HttpStatus.BAD_REQUEST.value()),
    NOT_FOUND("NotFound", HttpStatus.NOT_FOUND.value()),
    FORBIDDEN("Forbidden", HttpStatus.FORBIDDEN.value()),
    SERVER_ERROR("ServerError", HttpStatus.INTERNAL_SERVER_ERROR.value());

    @NonNull
    private final String code;
    private final int status;

    public static String getCodeByStatus(@NonNull HttpStatus httpStatus) {
        return Stream.of(ApiErrorCode.values())
                .filter(error -> error.getStatus() == httpStatus.value())
                .findFirst()
                .map(ApiErrorCode::getCode)
                .orElseGet(() -> String.valueOf(httpStatus.value()));
    }

}
