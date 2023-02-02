package com.example.restkube.domian.car.common.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PolicyRequestGender {

    MALE("M"),
    FEMALE("F");

    private final String value;

    /**
     * retrieves policy requester gender instance
     *
     * @param value gender
     * @return customerGender instance
     */
    @JsonCreator
    public static PolicyRequestGender forValue(final String value) {
        for (PolicyRequestGender gender : values()) {
            if (gender.value.equals(value)) {
                return gender;
            }
        }
        return null;
    }

    /**
     * returns gender value
     *
     * @return gender
     */
    @JsonValue
    public String getValue() {
        return value;
    }

}
