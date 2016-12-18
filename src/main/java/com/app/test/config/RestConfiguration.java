package com.app.test.config;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import lombok.Getter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by swathy.iyer on 17/12/16.
 */
@Getter
public class RestConfiguration extends Configuration {
    public static class EndpointConfig {

        @NotNull
        private String hostname;

        private int port = 80;

        public String getHostname() {
            return hostname;
        }

        public int getPort() {
            return port;
        }
    }
    @Valid
    @NotNull
    @JsonProperty("restDatabase")
    private DataSourceFactory restDatabase;

    @NotNull
    @Valid
//    @JsonProperty("endpoint")
    public EndpointConfig endpointConfig;
}
