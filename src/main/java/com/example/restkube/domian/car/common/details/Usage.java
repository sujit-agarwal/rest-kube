package com.example.restkube.domian.car.common.details;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
@Slf4j
public enum Usage {

    PRIVATE_HOME("P", "priveWoonWerk"),
    PRIVATE_PROFESSION("M", "priveBeroep");

    private final String code;
    private final String identifier;

    public static Usage forValue(String identifier) {
        return Stream.of(values())
                .filter(pref -> pref.identifier.equalsIgnoreCase(identifier))
                .findAny()
                .orElseGet(() -> {
                    log.warn("Cannot convert Status from {}", identifier);
                    return null;
                });
    }
}
