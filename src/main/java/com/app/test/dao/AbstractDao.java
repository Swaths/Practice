package com.app.test.dao;

import com.app.test.entity.BaseEntity;
import com.google.common.reflect.TypeToken;
import lombok.NonNull;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by swathy.iyer on 18/12/16.
 */
public abstract class AbstractDao<E extends BaseEntity> {
    protected final SessionFactory sessionFactory;
    protected final Class<E> entityClass;
    @SuppressWarnings("FieldCanBeLocal")
    protected final TypeToken<E> typeToken = new TypeToken<E>(getClass()) {
    };

    @SuppressWarnings("unchecked")
    protected AbstractDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.entityClass = (Class<E>) typeToken.getRawType();
    }


    /**
     * Checks whether an element of the input collection matches type of DAO
     */
    private void ensureTypeMatch(List list) {
        ensureTypeMatch(list, entityClass);
    }

    /**
     * Checks whether an element of the input collection matches specified Type
     */
    private void ensureTypeMatch(List list, Class tClass) {
        if (!list.isEmpty()) {
            Class elementClass = list.get(0).getClass();
            if (!tClass.isAssignableFrom(elementClass)) {
                throw new IllegalStateException(String.format("Database query returned List<%s> but List<%s> was expected", elementClass.getSimpleName(), entityClass.getSimpleName()));
            }
        }
    }


    /**
     * Returns the current {@link Session}.
     *
     * @return the current session
     */
    protected Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * Creates a new {@link Criteria} query for {@code <E>}.
     *
     * @return a new {@link Criteria} query
     * @see Session#createCriteria(Class)
     */
    protected Criteria criteria() {
        return currentSession().createCriteria(entityClass);
    }

    /**
     * Returns a named {@link Query}.
     *
     * @param queryName the name of the query
     * @return the named query
     * @see Session#getNamedQuery(String)
     */
    protected Query namedQuery(@NonNull String queryName) throws HibernateException {
        return currentSession().getNamedQuery(queryName);
    }

    /**
     * Returns the entity class managed by this DAO.
     *
     * @return the entity class managed by this DAO
     */
    public Class<E> getEntityClass() {
        return entityClass;
    }

    /**
     * Convenience method to return a single instance that matches the criteria, or null if the
     * criteria returns no results.
     *
     * @param criteria the {@link Criteria} query to run
     * @return the single result or {@code null}
     * @throws HibernateException if there is more than one matching result
     * @see Criteria#uniqueResult()
     */
    @SuppressWarnings("unchecked")
    protected E uniqueResult(@NonNull Criteria criteria) throws HibernateException {
        return (E) criteria.uniqueResult();
    }

    /**
     * Convenience method to return a single instance that matches the query, or null if the query
     * returns no results.
     *
     * @param query the query to run
     * @return the single result or {@code null}
     * @throws HibernateException if there is more than one matching result
     * @see Query#uniqueResult()
     */
    @SuppressWarnings("unchecked")
    protected E uniqueResult(@NonNull Query query) throws HibernateException {
        return (E) query.uniqueResult();
    }

    /**
     * Get the results of a {@link Criteria} query.
     *
     * @param criteria the {@link Criteria} query to run
     * @return the list of matched query results
     * @see Criteria#list()
     */
    @SuppressWarnings("unchecked")
    protected List<E> list(@NonNull Criteria criteria) throws HibernateException {
        List list = criteria.list();
        ensureTypeMatch(list);
        return list;
    }

    /**
     * Get the results of a {@link Criteria} query.
     *
     * @param criteria the {@link Criteria} query to run
     * @param tClass   the class to be Typed
     * @return the list of matched query results
     * @see Criteria#list()
     */
    @SuppressWarnings("unchecked")
    protected <T> List<T> listAs(@NonNull Criteria criteria, Class<T> tClass) {
        List list = criteria.list();
        ensureTypeMatch(list, tClass);
        return list;
    }

    /**
     * Get the results of a query.
     *
     * @param query  the query to run
     * @param tClass the class to be Typed
     * @return the list of matched query results
     * @see Query#list()
     */
    @SuppressWarnings("unchecked")
    protected <T> List<T> listAs(@NonNull Query query, Class<T> tClass) {
        List list = query.list();
        ensureTypeMatch(list, tClass);
        return list;
    }

    @SuppressWarnings("unchecked")
    protected <T> List<T> listAs(@NonNull Query query, TypeToken<T> typeToken) {
        return listAs(query, (Class<T>) typeToken.getRawType());
    }

    /**
     * Get the results of a {@link Criteria} query as a set
     *
     * @param criteria the {@link Criteria} query to run
     * @return the list of matched query results
     * @see Criteria#list()
     */
    @SuppressWarnings("unchecked")
    protected Set<E> listAsSet(@NonNull Criteria criteria) throws HibernateException {
        List list = criteria.list();
        ensureTypeMatch(list);
        return new HashSet<>(list);
    }


    /**
     * Get all entities. To be used for configuration entities only.
     * Use with caution
     *
     * @return All entities as a {@link Set}
     * @throws HibernateException
     */
    @SuppressWarnings("unchecked")
    public Set<E> getAll() throws HibernateException {
        return new HashSet<>(criteria().list());
    }

    public List<E> getByIds(@NonNull List<? extends Serializable> ids) throws HibernateException {
        //return currentSession().byMultipleIds(entityClass).multiLoad(ids);
        return criteria().setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).add(Restrictions.in("id", ids)).list();
    }

    /**
     * Get the results of a query.
     *
     * @param query the query to run
     * @return the list of matched query results
     * @see Query#list()
     */
    @SuppressWarnings("unchecked")
    protected List<E> list(@NonNull Query query) throws HibernateException {
        List list = query.list();
        ensureTypeMatch(list);
        return list;
    }

    /**
     * Get the results of a query.
     *
     * @param query the query to run
     * @return the list of matched query results as a set
     * @see Query#list()
     */
    @SuppressWarnings("unchecked")
    protected Set<E> listAsSet(@NonNull Query query) throws HibernateException {
        List list = query.list();
        ensureTypeMatch(list);
        return new HashSet<>(list);
    }

    /**
     * Return the persistent instance of {@code <E>} with the given identifier, or {@code null} if
     * there is no such persistent instance. (If the instance, or a proxy for the instance, is
     * already associated with the session, return that instance or proxy.)
     *
     * @param id an identifier
     * @return a persistent instance or {@code null}
     * @throws HibernateException
     * @see Session#get(Class, Serializable)
     */
    @SuppressWarnings("unchecked")
    public E get(@NonNull Serializable id) {
        return (E) currentSession().get(entityClass, id);
    }



    /**
     * Either save or update the given instance, depending upon resolution of the unsaved-value
     * checks (see the manual for discussion of unsaved-value checking).
     * <p/>
     * This operation cascades to associated instances if the association is mapped with
     * <tt>cascade="save-update"</tt>.
     *
     * @param entity a transient or detached instance containing new or updated state
     * @throws HibernateException
     * @see Session#saveOrUpdate(Object)
     */
    public E persist(@NonNull E entity) throws HibernateException {
        currentSession().saveOrUpdate(entity);
        return entity;
    }

    public E update(@NonNull E entity) throws HibernateException {
        currentSession().update(entity);
        return entity;
    }

    public List<E> update(@NonNull List<E> entities) throws HibernateException {
        entities.stream().forEach(this::update);
        return entities;
    }

    /**
     * Force initialization of a proxy or persistent collection.
     * <p/>
     * Note: This only ensures initialization of a proxy object or collection;
     * it is not guaranteed that the elements INSIDE the collection will be initialized/materialized.
     *
     * @param proxy a persistable object, proxy, persistent collection or {@code null}
     * @throws HibernateException if we can't initialize the proxy at this time, eg. the {@link Session} was closed
     */
    protected <T> T initialize(T proxy) throws HibernateException {
        if (!Hibernate.isInitialized(proxy)) {
            Hibernate.initialize(proxy);
        }
        return proxy;
    }

    // Delete hibernate entity
    protected E delete(@NonNull E entity) throws HibernateException {
        currentSession().delete(entity);
        return entity;
    }

    public E persistAndFlush(@NonNull E entity) throws HibernateException {
        currentSession().saveOrUpdate(entity);
        currentSession().flush();
        return entity;
    }

    public E updateAndFlush(@NonNull E entity) throws HibernateException {
        update(entity);
        currentSession().flush();
        return entity;
    }

    public List<E> updateAndFlush(@NonNull List<E> entities) throws HibernateException {
        entities.stream().forEach(this::update);
        currentSession().flush();
        return entities;
    }


}
