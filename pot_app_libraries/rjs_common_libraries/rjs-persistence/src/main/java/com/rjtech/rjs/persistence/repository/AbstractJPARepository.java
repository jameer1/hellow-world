package com.rjtech.rjs.persistence.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

import org.springframework.beans.factory.annotation.Autowired;

import com.rjtech.rjs.persistence.entitymanager.RJSEntityManagerFactory;

public abstract class AbstractJPARepository implements GenericRepository {

    @Autowired
    private RJSEntityManagerFactory rjsentityManagerFactory;

    public EntityManager getEntityManager() {
        return rjsentityManagerFactory.getEntityManager();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Object findById(Long id, Class entityClass, boolean lock) {
        Object entity;
        if (lock) {
            entity = getEntityManager().find(entityClass, id, LockModeType.WRITE);
        } else {
            entity = getEntityManager().find(entityClass, id);
        }
        return entity;
    }

    @SuppressWarnings({ "rawtypes" })
    public List findAll(Class entity) {
        return getEntityManager().createQuery(
                new StringBuilder("select entity from ").append(entity.getSimpleName()).append(" as entity").toString())
                .getResultList();
    }

    public Object makePersistent(Object entity) {
        Object entityPersist = getEntityManager().merge(entity);
        flush();
        return entityPersist;
    }

    public void delete(Object entity) {
        getEntityManager().remove(entity);
        flush();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void deleteByID(Long id, Class entityClass) {
        getEntityManager().remove(getEntityManager().find(entityClass, id));
        flush();
    }

    @SuppressWarnings({ "rawtypes" })
    public long countAll(Class entityClass) {
        return (Long) getEntityManager()
                .createQuery(new StringBuilder("select count(*) from ").append(entityClass.getName()).toString())
                .getSingleResult();
    }

    private void flush() {
        getEntityManager().flush();
    }

    public void clear() {
        getEntityManager().clear();
    }

    @SuppressWarnings({ "rawtypes" })
    public List findEntries(Class entityClass, int firstResult, int maxResults, String orderByField) {
        return getEntityManager()
                .createQuery(new StringBuilder("select entity from ").append(entityClass.getSimpleName())
                        .append(" as entity order by entity.").append(orderByField).toString())
                .setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
}
