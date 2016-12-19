package com.app.test.dao;

import com.app.test.config.Constants;
import com.app.test.entity.User;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

/**
 * Created by swathy.iyer on 18/12/16.
 */
@Singleton
public class UserDao extends AbstractDao<User>{
    @Inject
    public UserDao(@Named(Constants.SESSION_FACTORY) SessionFactory sessionFactory){
        super(sessionFactory);

    }
    public User getUserDetails(int id)
    {
        Criteria criteria=currentSession().createCriteria(User.class,"Users");
        criteria.add(Restrictions.eq("empId",id));
        return uniqueResult(criteria);
    }

}
