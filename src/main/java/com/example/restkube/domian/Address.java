package com.example.restkube.domian;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Address {

    private final String postalCode;
    private final String streetName;
    private final String city;
}
