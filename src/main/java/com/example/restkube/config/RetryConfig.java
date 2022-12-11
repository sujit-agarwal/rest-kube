package com.example.restkube.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.ExceptionClassifierRetryPolicy;
import org.springframework.retry.policy.NeverRetryPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;

@Configuration
public class RetryConfig {


    @Bean
    public NeverRetryPolicy getNeverRetryPolicy() {
        return new NeverRetryPolicy();
    }


    @Bean
    public SimpleRetryPolicy getSimpleRetryPolicy(@Value("${app.service.retry:5}") String retry) {
        return new SimpleRetryPolicy(Integer.valueOf(retry));
    }

    @Bean
    public FixedBackOffPolicy getFixedBackOffPolicy() {
        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
        fixedBackOffPolicy.setBackOffPeriod(1000l);
        return fixedBackOffPolicy;
    }

    @Bean
    public ExceptionClassifierRetryPolicy getExceptionClassifierRetryPolicy() {
        return new ExceptionClassifierRetryPolicy();

    }

}

