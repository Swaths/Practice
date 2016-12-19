package com.app.test;

import com.app.test.config.Constants;
import com.app.test.config.RestConfiguration;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Stage;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.server.ServerProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by swathy.iyer on 17/12/16.
 */
public class RestApplication extends Application<RestConfiguration>{

    GuiceBundle<RestConfiguration> guiceBundle;

    @Override
    public void initialize(Bootstrap<RestConfiguration> bootstrap) {
        guiceBundle = GuiceBundle.<RestConfiguration>newBuilder()
                .addModule(new RestModule())
                .enableAutoConfig(getClass().getPackage().getName())
                .setConfigClass(RestConfiguration.class)
                .build(Stage.DEVELOPMENT);
        bootstrap.addBundle(guiceBundle);
        bootstrap.addBundle(guiceBundle.getInjector().getInstance(Key.get(new TypeLiteral<HibernateBundle<RestConfiguration>>() {
        }, Names.named(Constants.HIBERNATE_BUNDLE))));
    }

    public static void main(String[] args) throws Exception {
        new RestApplication().run(args);
    }

    @Override
    public void run(RestConfiguration restConfiguration, Environment environment) throws Exception {
        Injector injector = guiceBundle.getInjector();
        enableStreamingOutput(environment);
    }

    private void enableStreamingOutput(Environment environment) {
        // Enable properties required for supporting streamingOutput.
        Map<String, Object> properties = new HashMap<>();
        properties.put(ServerProperties.WADL_FEATURE_DISABLE, "false");
        properties.put(ServerProperties.OUTBOUND_CONTENT_LENGTH_BUFFER, 0);
        environment.jersey().getResourceConfig().addProperties(properties);
    }
}
