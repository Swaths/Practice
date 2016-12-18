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
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.SessionFactoryFactory;
import org.hibernate.SessionFactory;

/**
 * Created by swathy.iyer on 17/12/16.
 */
@Path("/")
public class DefaultResource {
    private RestConfiguration restConfiguration;
    private SessionFactory sessionFactory;
    private TransactionManager transactionManager;
    private HibernateBundle hibernateBundle;
    private UserDao userDao;
    private UserActor userActor;

    int f;
    public DefaultResource()
    {
        f=10;
        hibernateBundle = new HibernateBundle<RestConfiguration>(ImmutableList.<Class<?>>builder().addAll(DbUtils.getEntityClasses()).build(), new SessionFactoryFactory()) {
            @Override
            public DataSourceFactory getDataSourceFactory(RestConfiguration restConfiguration) {
                return restConfiguration.getRestDatabase();
            }
        };
        sessionFactory=hibernateBundle.getSessionFactory();
        transactionManager=new TransactionManager(sessionFactory);
        userDao=new UserDao(sessionFactory);
        userActor=new UserActor(userDao);
    }
    public DefaultResource(RestConfiguration restConfiguration)
    {
        this.restConfiguration=restConfiguration;
    }

    @Path("hello-world")
    @GET
    public String printHelloWorld() {
        return "Hello World!!";
    }


    @Path("user-details")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public User getEmployeeData(@QueryParam("empId") int id) {
        hibernateBundle = new HibernateBundle<RestConfiguration>(ImmutableList.<Class<?>>builder().addAll(DbUtils.getEntityClasses()).build(), new SessionFactoryFactory()) {
            @Override
            public DataSourceFactory getDataSourceFactory(RestConfiguration restConfiguration) {
                return restConfiguration.getRestDatabase();
            }
        };
        sessionFactory=hibernateBundle.getSessionFactory();
        transactionManager=new TransactionManager(sessionFactory);
        userDao=new UserDao(sessionFactory);
        userActor=new UserActor(userDao);
        transactionManager.beginTransaction();
        User u=userActor.getUserDetails(id);
        transactionManager.commitTransaction();
        return u;
    }
}
