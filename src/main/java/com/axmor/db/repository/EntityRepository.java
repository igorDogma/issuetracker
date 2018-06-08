package com.axmor.db.repository;

import com.axmor.db.entityes.PersistentEntity;
import com.axmor.util.HibernateSessionFactory;
import org.hibernate.Session;
import java.util.List;

public abstract class EntityRepository<K,V extends PersistentEntity> {

    Session currentSession;
    public void closeCurrentSession() {
        currentSession.close();
    }

    public Session openSession() {
        currentSession = HibernateSessionFactory.getSessionFactory().openSession();
        return currentSession;
    }

    public void save(V entity) {
        Session session;
        try {
            session = openSession();
            session.beginTransaction();
            openSession().save(entity);
            session.getTransaction().commit();
        }catch(Exception e){
            throw new RuntimeException(" Cant update entity " , e);
        }finally{
            closeCurrentSession();
        }
    }

    public void update(V entity) {
        Session session;
        try {
            session = openSession();
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
        }catch(Exception e){
            throw new RuntimeException(" Cant update entity " , e);
        }finally{
            closeCurrentSession();
        }
    }

    public void saveOrUpdate(V entity) {
        Session session;
        try {
            session = openSession();
            session.beginTransaction();
            session.saveOrUpdate(entity);
            session.getTransaction().commit();
        }catch(Exception e){
            throw new RuntimeException(" Cant update entity " , e);
        }finally{
            closeCurrentSession();
        }
    }

    public V findById(Long id) {
        Session session;
        V entity;
        try {
            session = openSession();
            session.beginTransaction();
            entity = openSession().get(getEntityType(), id);
            session.getTransaction().commit();
        }catch(Exception e){
            throw new RuntimeException(" Cant update entity " , e);
        }finally{
            closeCurrentSession();
        }
        return entity;
    }

    public void delete(V entity) {
        Session session;
        try {
            session = openSession();
            session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
        }catch(Exception e){
            throw new RuntimeException(" Cant update entity " , e);
        }finally{
            closeCurrentSession();
        }}

    @SuppressWarnings("unchecked")
    public List<V> findAll() {
        List<V> entities = (List<V>) openSession().createQuery("from " + getEntityName()).list();
        closeCurrentSession();
        return entities;
    }

    public void deleteAll() {
        List<V> entityList = findAll();
        for (V entity : entityList) {
            delete(entity);
        }
    }

    public abstract String getEntityName();
    public abstract Class<V> getEntityType();
}
