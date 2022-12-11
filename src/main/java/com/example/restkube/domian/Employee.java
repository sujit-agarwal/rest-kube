package com.example.restkube.domian;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Employee {
    private final String employeeId;
    private final Name name;
    private final Address address;


}
