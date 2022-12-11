package com.example.restkube.context;

import com.example.restkube.domian.Address;
import com.example.restkube.domian.Name;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class EmployeeResponseContext {

    private final Name name;
    private final Address address;

}
