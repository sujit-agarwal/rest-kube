package com.example.restkube.domian.error;

import lombok.NonNull;
import org.springframework.http.HttpStatus;

public enum ApiErrorSeverity {

    CRITICAL,
    ERROR,
    WARNING,
    INFO;

    public static ApiErrorSeverity getSeverityByStatus(@NonNull HttpStatus httpStatus) {
        if (httpStatus == HttpStatus.NOT_FOUND) {
            return WARNING;
        } else {
            return ERROR;
        }
    }

}
