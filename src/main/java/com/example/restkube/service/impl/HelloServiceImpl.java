package com.example.restkube.service.impl;

import com.example.restkube.client.HelloServiceClient;
import com.example.restkube.context.EmployeeResponseContext;
import com.example.restkube.domian.Address;
import com.example.restkube.domian.Employee;
import com.example.restkube.domian.Name;
import com.example.restkube.service.HelloService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
@AllArgsConstructor
@ConditionalOnExpression("!${app.knoldus.hello-service.mock:false}")
public class HelloServiceImpl implements HelloService {

    private final HelloServiceClient helloServiceClient;

    private final Converter<EmployeeResponseContext, Employee> appConversionService;

    @Override
    public CompletableFuture<String> single() {
        return helloServiceClient.single();
    }

    @Override
    //Aggregate pattern
    public CompletableFuture<List<String>> join() {
        return helloServiceClient.single().thenCompose(response -> helloServiceClient.getList(response));
    }

    @Override
    //Branching/Combine Pattern
    public CompletableFuture<Employee> combine() {
        CompletableFuture<Name> nameCompletableFuture = helloServiceClient.single().thenCompose(response -> helloServiceClient.getName(response));
        return nameCompletableFuture.thenCombine(helloServiceClient.getAddress(),
                (nameResponse, addressResponse) -> appConversionService.convert(EmployeeResponseContext.builder().name(nameResponse)
                        .address(addressResponse).build())
        );
    }


    @Override
    public CompletableFuture<Name> name() {
        CompletableFuture<Name> nameCompletableFuture = helloServiceClient.single().thenCompose(response -> helloServiceClient.getName(response));
        return nameCompletableFuture;
    }

    @Override
    public Employee normal() {
        Name nameResponse = helloServiceClient.getName_normal(helloServiceClient.single_normal());
        Address address = helloServiceClient.getAddress_normal();
        return appConversionService.convert(EmployeeResponseContext.builder().name(nameResponse)
                .address(address).build());
    }
}
