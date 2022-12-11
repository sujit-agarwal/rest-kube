package com.example.restkube.client;

import com.example.restkube.config.HelloServiceProperties;
import com.example.restkube.domian.Address;
import com.example.restkube.domian.Name;
import com.google.common.collect.ImmutableMap;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;


@AllArgsConstructor
@Component
@Slf4j
public class HelloServiceClient {
    private final BaseRetryableApiClient baseRetryableApiClient;
    private final ExecutorService traceableExecutorService;
    private final HelloServiceProperties helloServiceProperties;

    public CompletableFuture<String> single() {

        return CompletableFuture.supplyAsync(() -> baseRetryableApiClient.get(helloServiceProperties.getHelloServiceSingle(), null, String.class), traceableExecutorService);

    }

    public CompletableFuture<List<String>> getList(final String fetchId) {
        return CompletableFuture.supplyAsync(() -> baseRetryableApiClient.getExchange(helloServiceProperties.getHelloServiceList(), null, new ParameterizedTypeReference<List<String>>() {
        }, getHeaders(fetchId)), traceableExecutorService);
    }

    public CompletableFuture<Name> getName(final String fetchId) {
        return CompletableFuture.supplyAsync(() -> baseRetryableApiClient.getWith(helloServiceProperties.getHelloServiceName(), null, Name.class, getHeaders(fetchId)), traceableExecutorService);

    }

    public CompletableFuture<Address> getAddress() {
        return CompletableFuture.supplyAsync(() -> baseRetryableApiClient.get(helloServiceProperties.getHelloServiceAddress(), null, Address.class), traceableExecutorService);

    }

    public String single_normal() {
        return baseRetryableApiClient.get(helloServiceProperties.getHelloServiceSingle(), null, String.class);
    }

    public Name getName_normal(final String fetchId) {
        return baseRetryableApiClient.getWith(helloServiceProperties.getHelloServiceName(), null, Name.class, getHeaders(fetchId));
    }

    public Address getAddress_normal() {
        return baseRetryableApiClient.get(helloServiceProperties.getHelloServiceAddress(), null, Address.class);

    }

    private Map<String, String> getHeaders(final String id) {
        return ImmutableMap.<String, String>builder()
                .put("id", id)
                .build();
    }


}
