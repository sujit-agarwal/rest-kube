package com.example.restkube.convertor;

import com.example.restkube.context.EmployeeResponseContext;
import com.example.restkube.domian.Employee;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EmployeeResponseConverter implements Converter<EmployeeResponseContext, Employee> {

    @Override
    public Employee convert(final EmployeeResponseContext source) {
        return Employee.builder()
                .name(source.getName())
                .address(source.getAddress())
                .employeeId("1").build();

    }
}
