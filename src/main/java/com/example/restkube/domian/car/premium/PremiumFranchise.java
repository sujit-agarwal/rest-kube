package com.example.restkube.domian.car.premium;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public enum PremiumFranchise {

    SR400(400),
    SR600(600),
    SR800(800),
    SR1200(1200);

    private final int franchise;

    @JsonCreator
    public static PremiumFranchise forFranchise(Integer franchise) {
        for (PremiumFranchise element : values()) {
            if (Objects.equals(element.franchise, franchise)) {
                return element;
            }
        }
        return null;
    }

    @JsonValue
    public int getFranchise() {
        return franchise;
    }

}
