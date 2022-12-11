package com.example.restkube.controller;

import com.example.restkube.domian.Employee;
import com.example.restkube.domian.Name;
import com.example.restkube.domian.error.ApiErrorResponse;
import com.example.restkube.service.HelloService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@Slf4j
@AllArgsConstructor
public class HelloWorld {

    private final HelloService helloService;

    @ApiOperation(value = "Return the valid number 1 or 2",
            notes = "This method provides a way to lookup the name or number",
            response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK with JSON object containing name in the response ", response = String.class),
            @ApiResponse(code = 400, message = "Bad Request - with an explanation of what is wrong with the request ", response = ApiErrorResponse.class),
            @ApiResponse(code = 404, message = "Not Found -  number is missing in the request ", response = ApiErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error ", response = ApiErrorResponse.class),
            @ApiResponse(code = 503, message = "Service Unavailable", response = ApiErrorResponse.class)})
    @GetMapping("/single")
    public CompletableFuture<String> single() {
        return helloService.single();
    }

    @GetMapping("/join")
    public CompletableFuture<List<String>> join() {
        return helloService.join();
    }

    @GetMapping("/combine")
    public CompletableFuture<Employee> combine() {
        return helloService.combine();
    }

    @GetMapping("/name")
    public CompletableFuture<Name> name() {
        return helloService.name();
    }

    @GetMapping("/normal")
    public Employee normal() {
        return helloService.normal();
    }
}
