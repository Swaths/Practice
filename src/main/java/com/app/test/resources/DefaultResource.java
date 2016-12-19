package com.app.test.resources;

import com.app.test.actor.UserActor;
import com.app.test.config.RestConfiguration;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.app.test.dao.UserDao;
import com.app.test.entity.User;
import com.app.test.utils.DbUtils;
import com.app.test.utils.TransactionManager;
import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.SessionFactoryFactory;
import io.dropwizard.setup.Environment;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Created by swathy.iyer on 17/12/16.
 */
@Path("/")
@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class DefaultResource {

    private final TransactionManager transactionManager;
    private final UserActor userActor;

    @Path("hello-world")
    @GET
    public String printHelloWorld() {
        return "Hello World!!";
    }


    @Path("user-details")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public User getEmployeeData(@QueryParam("empId") int id) {
        transactionManager.beginTransaction();
        User u=userActor.getUserDetails(id);
        transactionManager.commitTransaction();
        return u;
    }
}
