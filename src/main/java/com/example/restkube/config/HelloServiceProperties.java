package com.example.restkube.config;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;


@Getter
@ToString
@ConfigurationProperties(prefix = "app.knoldus")
@Validated
@ConstructorBinding
@AllArgsConstructor
public class HelloServiceProperties {

    @NotNull
    @NestedConfigurationProperty
    private final RestServiceProperties helloServiceSingle;

    @NotNull
    @NestedConfigurationProperty
    private final RestServiceProperties helloServiceList;

    @NotNull
    @NestedConfigurationProperty
    private final RestServiceProperties helloServiceName;

    @NotNull
    @NestedConfigurationProperty
    private final RestServiceProperties helloServiceAddress;

}
