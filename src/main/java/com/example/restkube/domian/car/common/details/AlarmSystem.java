package com.example.restkube.domian.car.common.details;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AlarmSystem {

    ANTI_THEFT(8181, "clsClauseAntiTheft"),
    ANTI_THEFT_PLUS(8182, "clsClauseAntiTheftPlus"),
    NONE(0, "none");

    private final int code;
    private final String identifier;
}
