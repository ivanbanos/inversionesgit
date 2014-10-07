/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.dao;

import com.invbf.sistemagestionclientes.entity.Formulario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author ideacentre
 */
public class FormularioDao {

    public FormularioDao() {
    }

    public static void create(Formulario formulario) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            em.persist(formulario);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.close();
        emf.close();
    }

    public static void edit(Formulario formulario) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            em.merge(formulario);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.close();
        emf.close();
    }

    public static void remove(Formulario formulario) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            em.remove(em.merge(formulario));
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.close();
        emf.close();
    }

    public static Formulario find(Integer id) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Formulario formulario = null;

        tx.begin();
        try {
            formulario = em.find(Formulario.class, id);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.close();
        emf.close();
        return formulario;
    }

    public static List<Formulario> findAll() {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Formulario> lista = new ArrayList<Formulario>();

        tx.begin();
        try {
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Formulario.class));
            lista = em.createQuery(cq).getResultList();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.close();
        emf.close();
        return lista;
    }

    public static List<Formulario> findRange(int[] range) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Formulario> lista = new ArrayList<Formulario>();

        tx.begin();
        try {
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Formulario.class));
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
            javax.persistence.criteria.Root<Formulario> rt = cq.from(Formulario.class);
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

    public static Formulario findByAccionAndTabla(String accion, String tabla) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Formulario> formularios = null;
        tx.begin();
        try {
            formularios = em.createNamedQuery("Formularios.findByAccionAndTabla")
                    .setParameter("accion", accion)
                    .setParameter("tabla", tabla)
                    .getResultList();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.close();
        emf.close();
        if (formularios == null || formularios.isEmpty()) {
            return null;
        } else {
            return formularios.get(0);
        }
    }
}
