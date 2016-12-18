package com.app.test.utils;
import com.sun.jersey.spi.resource.Singleton;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

/**
 * Created by swathy.iyer on 18/12/16.
 */

@Singleton
public class TransactionManager {
    private final SessionFactory sessionFactory;

    public TransactionManager(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    public void beginTransaction() {
        currentSession().beginTransaction();
    }

    public void commitTransaction() {
        final Transaction txn = currentSession().getTransaction();
        if (txn != null && txn.getStatus() == TransactionStatus.ACTIVE) {
            txn.commit();
        }
    }

    public void rollbackTransaction() {
        final Transaction txn = currentSession().getTransaction();
        if (txn != null && txn.getStatus()== TransactionStatus.ACTIVE) {
            txn.rollback();
        }
    }

}

