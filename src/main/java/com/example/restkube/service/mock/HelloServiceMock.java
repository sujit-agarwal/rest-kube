package com.example.restkube.service.mock;

import com.example.restkube.domian.Address;
import com.example.restkube.domian.Employee;
import com.example.restkube.domian.Name;
import com.example.restkube.service.HelloService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
@AllArgsConstructor
@ConditionalOnExpression("${app.knoldus.hello-service.mock:false}")
public class HelloServiceMock implements HelloService {
    @Override
    public CompletableFuture<String> single() {
        return CompletableFuture.completedFuture("Mock String");
    }

    @Override
    public CompletableFuture<List<String>> join() {
        return CompletableFuture.completedFuture(Arrays.asList("mock", "mock2", "mock3"));
    }

    @Override
    public CompletableFuture<Employee> combine() {
        return CompletableFuture.completedFuture(getEmployee());
    }


    @Override
    public CompletableFuture<Name> name() {
        return CompletableFuture.completedFuture(getName());
    }

    @Override
    public Employee normal() {
        return getEmployee();
    }


    private Employee getEmployee() {
        return Employee.builder().employeeId("mockid").name(getName()).address(getAddress()).build();
    }

    private Name getName() {
        return Name.builder().firstName("first mock").lastName("last mock").build();
    }

    private Address getAddress() {
        return Address.builder()
                .city("mock city")
                .postalCode("mocks postal code")
                .streetName("mock streed").build();

    }
}
