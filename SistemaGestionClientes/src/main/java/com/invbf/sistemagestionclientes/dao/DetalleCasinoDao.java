/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.dao;

import com.invbf.sistemagestionclientes.entitySGB.Casinosdetalles;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author ivan
 */
public class DetalleCasinoDao {

    public DetalleCasinoDao() {
    }

    public static void create(Casinosdetalles casino) {
        if (casino.getCiudad() != null) {
            casino.setCiudad(casino.getCiudad().toUpperCase());
        }
        if (casino.getAbreCiudad() != null) {
            casino.setAbreCiudad(casino.getAbreCiudad().toUpperCase());
        }
        if (casino.getAbreviacion() != null) {
            casino.setAbreviacion(casino.getAbreviacion().toUpperCase());
        }
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("gestionBonosPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            em.persist(casino);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.close();
        emf.close();
    }

    public static void edit(Casinosdetalles casino) {
        if (casino.getCiudad() != null) {
            casino.setCiudad(casino.getCiudad().toUpperCase());
        }
        if (casino.getAbreCiudad() != null) {
            casino.setAbreCiudad(casino.getAbreCiudad().toUpperCase());
        }
        if (casino.getAbreviacion() != null) {
            casino.setAbreviacion(casino.getAbreviacion().toUpperCase());
        }
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("gestionBonosPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            em.merge(casino);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.close();
        emf.close();
    }

    public static void remove(Casinosdetalles casino) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("gestionBonosPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            em.remove(em.merge(casino));
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.close();
        emf.close();
    }

    public static Casinosdetalles find(Integer id) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("gestionBonosPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Casinosdetalles casino = null;

        tx.begin();
        try {
            casino = em.find(Casinosdetalles.class, id);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.close();
        emf.close();
        return casino;
    }

    public static List<Casinosdetalles> findAll() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("gestionBonosPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Casinosdetalles> lista = new ArrayList<Casinosdetalles>();

        tx.begin();
        try {
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Casinosdetalles.class));
            lista = em.createQuery(cq).getResultList();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.close();
        emf.close();
        return lista;
    }

    public static List<Casinosdetalles> findRange(int[] range) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("gestionBonosPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Casinosdetalles> lista = new ArrayList<Casinosdetalles>();

        tx.begin();
        try {
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Casinosdetalles.class));
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
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("gestionBonosPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        int count = 0;

        tx.begin();
        try {
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            javax.persistence.criteria.Root<Casinosdetalles> rt = cq.from(Casinosdetalles.class);
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
