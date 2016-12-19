package com.app.test.actor;

import com.app.test.dao.UserDao;
import com.app.test.entity.User;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;

/**
 * Created by swathy.iyer on 18/12/16.
 */

@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Singleton
public class UserActor {
    private final UserDao userDao;
    public User getUserDetails(int empId)
    {
        return userDao.getUserDetails(empId);
    }
}
