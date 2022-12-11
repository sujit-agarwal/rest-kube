package com.example.restkube.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class HelloWorldSmokeTest {
    @Autowired
    private HelloWorld helloWorld;

    @Test
    public void contextLoads() throws Exception {
        assertThat(helloWorld).isNotNull();
    }
}
