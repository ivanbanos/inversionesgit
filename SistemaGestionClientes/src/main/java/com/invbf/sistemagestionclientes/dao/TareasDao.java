/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.dao;

import com.invbf.sistemagestionclientes.entity.Tarea;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.Root;

/**
 *
 * @author ideacentre
 */
public class TareasDao {

    public TareasDao() {
    }

    public static void create(Tarea tarea) {
        tarea.setNombre(tarea.getNombre().toUpperCase());
        tarea.setDescripcion(tarea.getDescripcion().toUpperCase());
        tarea.setSpeech(tarea.getSpeech().toUpperCase());
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            em.persist(tarea);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.close();
        emf.close();
    }

    public static void edit(Tarea tarea) {
        tarea.setNombre(tarea.getNombre().toUpperCase());
        tarea.setDescripcion(tarea.getDescripcion().toUpperCase());
        tarea.setSpeech(tarea.getSpeech().toUpperCase());
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            em.merge(tarea);
            tx.commit();
        } catch (Exception e) {
            System.out.println(e);
            tx.rollback();
        }

        em.close();
        emf.close();
    }

    public static void remove(Tarea tarea) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            em.remove(em.merge(tarea));
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.close();
        emf.close();
    }

    public static Tarea find(Integer id) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Tarea tarea = null;

        tx.begin();
        try {
            tarea = em.find(Tarea.class, id);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.close();
        emf.close();
        return tarea;
    }

    public static List<Tarea> findAll() {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Tarea> lista = new ArrayList<Tarea>();

        tx.begin();
        try {
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tarea> c = cq.from(Tarea.class);
            cq.select(c);
            cq.orderBy(em.getCriteriaBuilder().desc(c.get("fechaFinalizacion")));
            lista = em.createQuery(cq).getResultList();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.close();
        emf.close();
        return lista;
    }

    public static List<Tarea> findRange(int[] range) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Tarea> lista = new ArrayList<Tarea>();

        tx.begin();
        try {
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tarea.class));
            javax.persistence.Query q = em.createQuery(cq);
            q.setMaxResults(range[1] - range[0]);
            q.setFirstResult(range[0]);
            lista = q.getResultList();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.close();
        emf.close();
        return lista;
    }

    public static int count() {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        int count = 0;

        tx.begin();
        try {
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            javax.persistence.criteria.Root<Tarea> rt = cq.from(Tarea.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            javax.persistence.Query q = em.createQuery(cq);
            count = ((Long) q.getSingleResult()).intValue();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.close();
        emf.close();
        return count;


    }
}
