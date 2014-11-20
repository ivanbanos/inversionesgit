/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.dao;

import com.invbf.sistemagestionclientes.entity.Accion;
import com.invbf.sistemagestionclientes.entitySGB.Cargos;
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
public class CargoDao {

    public CargoDao() {
    }
    
    public static void create(Cargos cargo) {
        
        cargo.setNombre(cargo.getNombre().toUpperCase());
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("gestionBonosPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            em.persist(cargo);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.close();
        emf.close();
    }
    
    public static void edit(Cargos cargo) {
        
        cargo.setNombre(cargo.getNombre().toUpperCase());
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("gestionBonosPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            em.merge(cargo);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.close();
        emf.close();
    }

    public static void remove(Cargos cargo) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("gestionBonosPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            em.remove(em.merge(cargo));
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.close();
        emf.close();
    }

    public static Cargos find(Integer id) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("gestionBonosPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Cargos cargo = null;

        tx.begin();
        try {
            cargo = em.find(Cargos.class, id);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.close();
        emf.close();
        return cargo;
    }

    public static List<Cargos> findAll() {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("gestionBonosPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Cargos> lista = new ArrayList<Cargos>();

        tx.begin();
        try {
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cargos.class));
            lista = em.createQuery(cq).getResultList();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.close();
        emf.close();
        return lista;
    }

    public static List<Cargos> findRange(int[] range) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("gestionBonosPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Cargos> lista = new ArrayList<Cargos>();

        tx.begin();
        try {
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cargos.class));
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
                Persistence.createEntityManagerFactory("gestionBonosPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        int count = 0;

        tx.begin();
        try {
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            javax.persistence.criteria.Root<Accion> rt = cq.from(Cargos.class);
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

    public static Cargos findByNombreAccion(String nombre) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("gestionBonosPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Cargos> cargos = null;
        tx.begin();
        try {
            cargos = (List<Cargos>) em.createNamedQuery("Cargos.findByNombre")
                    .setParameter("nombre", nombre)
                    .getResultList();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.close();
        emf.close();

        if (cargos == null || cargos.isEmpty()) {
            return null;
        } else {
            return cargos.get(0);
        }
    }
}
