package com.example.restkube.domian;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Name {

    private final String firstName;

    private final String lastName;

}