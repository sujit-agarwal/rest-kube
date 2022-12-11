package com.example.restkube.config;

import com.google.common.base.Converter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.classify.Classifier;
import org.springframework.cloud.sleuth.instrument.async.TraceableExecutorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.ExceptionClassifierRetryPolicy;
import org.springframework.retry.policy.NeverRetryPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
@AllArgsConstructor
@ConfigurationPropertiesScan(basePackageClasses = {HelloServiceProperties.class})
public class AppConfig {

    private final BeanFactory beanFactory;
    private final NeverRetryPolicy neverRetryPolicy;
    private final SimpleRetryPolicy simpleRetryPolicy;
    private final FixedBackOffPolicy fixedBackOffPolicy;

    private final ExceptionClassifierRetryPolicy policy;


    @Bean
    public RestTemplate getTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.setConnectTimeout(Duration.ofMillis(200)).setReadTimeout(Duration.ofMillis(200)).build();

    }

    @Bean
    public ExecutorService traceableExecutorService(ExecutorService executorService) {
        return new TraceableExecutorService(beanFactory, executorService);
    }


    @Bean
    public ExecutorService asyncExecutor() {
        return new ThreadPoolExecutor(10, 20, 10000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
    }

    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();
        policy.setExceptionClassifier(configureStatusCodeBasedRetryPolicy());
        retryTemplate.setBackOffPolicy(fixedBackOffPolicy);
        retryTemplate.setRetryPolicy(policy);
        return retryTemplate;
    }

    private Classifier<Throwable, RetryPolicy> configureStatusCodeBasedRetryPolicy() {
        return throwable -> {
            if (throwable instanceof HttpStatusCodeException) {
                HttpStatusCodeException exception = (HttpStatusCodeException) throwable;
                return getRetryPolicyForStatus(exception.getStatusCode());
            }
            return simpleRetryPolicy;
        };
    }

    private RetryPolicy getRetryPolicyForStatus(HttpStatus httpStatus) {
        switch (httpStatus) {
            case BAD_GATEWAY:
            case SERVICE_UNAVAILABLE:
            case INTERNAL_SERVER_ERROR:
            case GATEWAY_TIMEOUT:
                return simpleRetryPolicy;
            default:
                return neverRetryPolicy;
        }
    }

    @Bean
    public ConversionService appConversionService(Set<Converter> converters) {
        ConversionServiceFactoryBean factory = new ConversionServiceFactoryBean();
        factory.setConverters(converters);
        factory.afterPropertiesSet();
        return factory.getObject();
    }
}
