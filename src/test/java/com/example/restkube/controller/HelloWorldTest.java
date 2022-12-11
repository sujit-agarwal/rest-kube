package com.example.restkube.controller;

import com.example.restkube.Endpoint;
import com.example.restkube.domian.Employee;
import com.example.restkube.domian.Name;
import com.example.restkube.domian.error.ApiErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"wiremock.reset-mappings-after-each-test=true"})
@AutoConfigureWireMock(port = 8085, files = {"classpath:/files"})
@ExtendWith(OutputCaptureExtension.class)
class HelloWorldTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    void join() {
        final String response = "[\"Hello\",\"1\",\"1\"]";
        stubFor(get(Endpoint.SINGLE.resource())
                .willReturn(aResponse()
                        .withBody("1")
                        .withStatus(200)));

        stubFor(get(Endpoint.JOIN.resource())
                .willReturn(aResponse()
                        .withBody(response)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON.toString())
                        .withStatus(200)));

        List<String> body = this.restTemplate.exchange("http://localhost:" + port + "/join", HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>() {
        }).getBody();

        assertThat(body).asList();

        verify(exactly(1), getRequestedFor(urlPathMatching(Endpoint.SINGLE.resource())));


    }

    @Test
    void name() {
        stubFor(get(Endpoint.SINGLE.resource())
                .willReturn(aResponse()
                        .withBody("1")
                        .withStatus(200)));

        stubFor(get(Endpoint.NAME.resource())
                .willReturn(aResponse()
                        .withBody(Endpoint.NAME.response())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON.toString())
                        .withStatus(200)));
        Name name = this.restTemplate.getForObject("http://localhost:" + port + "/name",
                Name.class);
        assertThat(name).isNotNull();

    }

    @Test
    void single() {
        stubFor(get(Endpoint.SINGLE.resource())
                .willReturn(aResponse()
                        .withBody("Hello")
                        .withStatus(200)));
        String forObject = this.restTemplate.getForObject("http://localhost:" + port + "/single",
                String.class);
        assertThat(forObject).contains("Hello");
    }


    @Test
    void single_Retry(CapturedOutput output) {
        stubFor(get(Endpoint.SINGLE.resource())
                .willReturn(aResponse()
                        .withStatus(INTERNAL_SERVER_ERROR.value())));
        ApiErrorResponse apiError = this.restTemplate.getForObject("http://localhost:" + port + "/single",
                ApiErrorResponse.class);
        assertThat(output.getOut()).contains("Retry count 2 for the service hello-Service-single");
        verify(exactly(3), getRequestedFor(urlPathMatching(Endpoint.SINGLE.resource())));
    }

    @Test
    void combine() {
        stubFor(get(Endpoint.SINGLE.resource())
                .willReturn(aResponse()
                        .withBody("1")
                        .withStatus(200)));

        stubFor(get(Endpoint.NAME.resource())
                .willReturn(aResponse()
                        .withBody(Endpoint.NAME.response())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON.toString())
                        .withStatus(200)));

        stubFor(get(Endpoint.ADDRESS.resource())
                .willReturn(aResponse()
                        .withBody(Endpoint.ADDRESS.response())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON.toString())
                        .withStatus(200)));

        Employee employee = this.restTemplate.getForObject("http://localhost:" + port + "/combine",
                Employee.class);
        assertThat(employee).isNotNull();

    }

}