package com.example.restkube.service.mock;

import com.example.restkube.domian.Employee;
import com.example.restkube.domian.Name;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HelloServiceMockTest {
    HelloServiceMock classUnderTest = new HelloServiceMock();

    @Test
    void single() throws ExecutionException, InterruptedException {
        assertTrue(classUnderTest.single().get().equals("Mock String"));
    }

    @Test
    void join() throws ExecutionException, InterruptedException {
        assertThat(classUnderTest.join().get()).asList();
    }

    @Test
    void combine() throws ExecutionException, InterruptedException {
        assertThat(classUnderTest.combine().get()).isExactlyInstanceOf(Employee.class);
    }

    @Test
    void name() throws ExecutionException, InterruptedException {
        assertThat(classUnderTest.name().get()).isExactlyInstanceOf(Name.class);
    }

    @Test
    void normal() {
        assertThat(classUnderTest.normal()).isExactlyInstanceOf(Employee.class);
    }
}