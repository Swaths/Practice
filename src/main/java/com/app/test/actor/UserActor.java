package com.app.test.actor;

import com.app.test.dao.UserDao;
import com.app.test.entity.User;
import com.sun.jersey.spi.resource.Singleton;
import lombok.RequiredArgsConstructor;

/**
 * Created by swathy.iyer on 18/12/16.
 */
@RequiredArgsConstructor
@Singleton
public class UserActor {
    private final UserDao userDao;
    public User getUserDetails(int empId)
    {
        return userDao.getUserDetails(empId);
    }
}
