package com.example.restkube.domian.car.common.details;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
@Slf4j
public enum Mileage {

    LOW(4, "tot10000"),
    MEDIUM(5, "10000Tot30000"),
    HIGH(6, "Vanaf30000");

    @JsonValue
    private final int code;
    private final String identifier;

    public static Mileage forValue(String identifier) {
        return Stream.of(values())
                .filter(pref -> pref.identifier.equalsIgnoreCase(identifier))
                .findAny()
                .orElseGet(() -> {
                    log.warn("Cannot convert Mileage from {}", identifier);
                    return null;
                });
    }
}
