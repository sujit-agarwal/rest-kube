package com.example.restkube.client;

import com.example.restkube.config.RestServiceProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@AllArgsConstructor
@Component
@Slf4j
public class BaseRetryableApiClient {

    private final RestTemplate restTemplate;

    private final RetryTemplate retryTemplate;

    public <T> T get(final RestServiceProperties restServiceProperties, final HttpEntity httpEntity,
                     final Class<T> responseType, final Object... urlVariables) {
        ResponseEntity<T> responseEntity = retryTemplate
                .execute(context -> {
                    log.info(String.format("Retry count %d for the service %s", context.getRetryCount(), restServiceProperties.getName()));
                    return restTemplate.exchange(restServiceProperties.getUrl(), HttpMethod.GET, httpEntity,
                            responseType, urlVariables);
                });
        Assert.isTrue(responseEntity.getStatusCode() == HttpStatus.OK, "This service is failing ::" + restServiceProperties.getName());
        return responseEntity.getBody();
    }

    public <T> T getExchange(final RestServiceProperties restServiceProperties, final HttpEntity httpEntity,
                             final ParameterizedTypeReference<T> responseType, final Map<String, String> urlVariables) {
        ResponseEntity<T> responseEntity = retryTemplate
                .execute(context -> {
                    log.info(String.format("Retry count %d for the service %s", context.getRetryCount(), restServiceProperties.getName()));
                    return restTemplate.exchange(restServiceProperties.getUrl(), HttpMethod.GET, httpEntity,
                            responseType, urlVariables);
                });
        log.info("hello worls");
        Assert.isTrue(responseEntity.getStatusCode() == HttpStatus.OK, "This service is failing ::" + restServiceProperties.getName());
        return responseEntity.getBody();
    }


    public <T> T getWith(final RestServiceProperties restServiceProperties, final HttpEntity httpEntity,
                         final Class<T> responseType, final Map<String, String> urlVariables) {
        ResponseEntity<T> responseEntity = retryTemplate
                .execute(context -> {
                    log.info(String.format("Retry count %d for the service %s", context.getRetryCount(), restServiceProperties.getName()));
                    return restTemplate.exchange(restServiceProperties.getUrl(), HttpMethod.GET, httpEntity,
                            responseType, urlVariables);
                });
        Assert.isTrue(responseEntity.getStatusCode() == HttpStatus.OK, "This service is failing ::" + restServiceProperties.getName());
        return responseEntity.getBody();
    }

}
