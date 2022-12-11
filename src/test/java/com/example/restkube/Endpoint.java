package com.example.restkube;

import com.github.jknack.handlebars.Handlebars;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import static org.apache.commons.io.FileUtils.readFileToString;

public enum Endpoint {

    SINGLE("/hello/number", "", ""),
    JOIN("/hello/1/list", "", ""),
    ADDRESS("/address", "", "address_response.json"),
    EMPLOYEE("/address", "", "employee_response.json"),
    NAME("/hello/1/name", "", "name_response.json");

    private final String resource;
    private final String responseTemplate;
    private final String requestTemplate;
    private final Handlebars handlebars;

    Endpoint(String resource, String requestTemplate, String responseTemplate) {
        this.resource = resource;
        this.responseTemplate = responseTemplate;
        this.requestTemplate = requestTemplate;
        this.handlebars = new Handlebars();
    }

    public String resource() {
        return resource;
    }

    public String resource(Object... args) {
        return String.format(resource, args);
    }

    public String responseTemplate() {
        return responseTemplate;
    }

    public String requestTemplate() {
        return requestTemplate;
    }

    public String request(Object context) {
        return compile(this.requestTemplate, context);
    }

    public String request() {
        return compile(this.requestTemplate, new Object());
    }

    public String response(Object context) {
        String compile = compile(this.responseTemplate, context);
        return compile;
    }

    public String response() {
        return compile(this.responseTemplate, new Object());
    }

    private String compile(String templateName, Object context) {
        if (StringUtils.isEmpty(templateName)) {
            throw new RuntimeException("Template Required!");
        }

        try {
            File templateFile = ResourceUtils.getFile("classpath:templates/" + templateName);
            String readFileToString = readFileToString(templateFile, String.valueOf(Charset.defaultCharset()));
            return handlebars.compileInline(readFileToString).apply(context);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
