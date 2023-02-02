package com.example.restkube.domian.car.premium;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PremiumCoverage {

    CIVIL_LIABILITY("civilLiability"),
    OMNIUM("omnium"),
    MINI_OMNIUM("miniOmnium"),
    LEGAL_ASSISTANCE("legalAssistance");

    private final String coverage;

    @JsonCreator
    public static PremiumCoverage forValue(String coverage) {
        for (PremiumCoverage coverageType : values()) {
            if (coverageType.coverage.equals(coverage)) {
                return coverageType;
            }
        }
        return null;
    }

    @JsonValue
    public String getCoverage() {
        return coverage;
    }

}
