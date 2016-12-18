package com.app.test.dao;

import com.app.test.entity.User;
import com.sun.jersey.spi.resource.Singleton;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

/**
 * Created by swathy.iyer on 18/12/16.
 */
@Singleton
public class UserDao extends AbstractDao<User>{
    public UserDao(SessionFactory sessionFactory){
        super(sessionFactory);

    }
    public User getUserDetails(int id)
    {
        Criteria criteria=currentSession().createCriteria(User.class,"Users");
        criteria.add(Restrictions.eq("empId",id));
        return uniqueResult(criteria);
    }

}
