package com.example.restkube.service.impl;

import com.example.restkube.client.HelloServiceClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.converter.Converter;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HelloServiceImplTest {

    @Mock
    private Converter converter;

    @Mock
    private HelloServiceClient helloServiceClient;

    @InjectMocks
    private HelloServiceImpl classUnderTest;

    @Test
    void single() throws ExecutionException, InterruptedException {
        when(helloServiceClient.single()).thenReturn(CompletableFuture.completedFuture("hello Mock"));
        String result = classUnderTest.single().get();
        assertEquals("hello Mock", result);
    }

    @Test
    void join() throws ExecutionException, InterruptedException {
        List mock = mock(List.class);
        when(helloServiceClient.single()).thenReturn(CompletableFuture.completedFuture("mock1"));
        when(helloServiceClient.getList("mock1")).thenReturn(CompletableFuture.completedFuture(mock));
        List<String> results = classUnderTest.join().get();
        assertThat(results).asList();
    }


}