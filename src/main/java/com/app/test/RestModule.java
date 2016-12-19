package com.app.test;

import com.app.test.config.Constants;
import com.app.test.config.RestConfiguration;
import com.app.test.utils.DbUtils;
import com.google.common.collect.ImmutableList;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.SessionFactoryFactory;
import org.hibernate.SessionFactory;

import java.awt.*;

/**
 * Created by swathy.iyer on 18/12/16.
 */
public class RestModule extends AbstractModule {

    @Override
    public void configure() {

    }

    @Provides
    @Singleton
    @Named(Constants.HIBERNATE_BUNDLE)
    public HibernateBundle<RestConfiguration> provideHibernateBundle() {
        final String packagePath = Dimension.class.getPackage().getName();
        ImmutableList.Builder<Class<?>> classBuilder = ImmutableList.builder();

        return new HibernateBundle<RestConfiguration>(ImmutableList.<Class<?>>builder().addAll(DbUtils.getEntityClasses()).build(), new SessionFactoryFactory()) {
            @Override
            public DataSourceFactory getDataSourceFactory(RestConfiguration configuration) {
                return configuration.getRestDatabase();
            }
        };
    }

    @Provides
    @Singleton
    @Named(Constants.SESSION_FACTORY)
    public SessionFactory provideSessionFactory(@Named(Constants.HIBERNATE_BUNDLE) HibernateBundle<RestConfiguration> hibernateBundle) {
        return hibernateBundle.getSessionFactory();
    }


}
