package com.app.test;

import com.app.test.config.RestConfiguration;
import com.app.test.resources.DefaultResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * Created by swathy.iyer on 17/12/16.
 */
public class RestApplication extends Application<RestConfiguration>{


//    private final SwaggerDropwizard swaggerDropwizard = new SwaggerDropwizard();

    @Override
    public void initialize(Bootstrap<RestConfiguration> bootstrap) {

    }


    public static void main(String[] args) throws Exception {
        new RestApplication().run(args);
    }

    @Override
    public void run(RestConfiguration restConfiguration, Environment environment) throws Exception {
        environment.jersey().register(new DefaultResource(restConfiguration));
    }
}
