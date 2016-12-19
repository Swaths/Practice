package com.app.test.config;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.db.DataSourceFactory;
import lombok.Getter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * Created by swathy.iyer on 17/12/16.
 */
@Getter
@ToString
public class RestConfiguration extends Configuration {

    @JsonProperty
    @NotNull
    private String env;

    @NotNull
    @JsonProperty("jerseyClient")
    private JerseyClientConfiguration jerseyClientConfig = new JerseyClientConfiguration();

    public static class EndpointConfig {
        @JsonProperty
        @NotNull
        private String hostname;

        @JsonProperty
        private int port = 80;
    }

    @NotNull
    @Valid
    @JsonProperty("endpointConfig")
    public EndpointConfig endpointConfig;

    @Valid
    @NotNull
    @JsonProperty("restDatabase")
    private DataSourceFactory restDatabase;

    @JsonProperty("hibernate")
    protected Map<String,String> dbConfig;

    @JsonProperty("system")
    protected Map<String, String> systemProperties;

}
