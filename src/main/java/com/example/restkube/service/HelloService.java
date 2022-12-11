package com.example.restkube.service;

import com.example.restkube.domian.Employee;
import com.example.restkube.domian.Name;

import java.util.List;
import java.util.concurrent.CompletableFuture;


public interface HelloService {
    CompletableFuture<String> single();

    CompletableFuture<List<String>> join();

    CompletableFuture<Employee> combine();

    CompletableFuture<Name> name();

    Employee normal();
}
