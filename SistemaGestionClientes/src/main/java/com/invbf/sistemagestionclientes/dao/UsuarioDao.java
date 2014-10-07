/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.dao;

import com.invbf.sistemagestionclientes.entity.Configuracion;
import com.invbf.sistemagestionclientes.entity.Usuario;
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
public class UsuarioDao {
     public static void create(Usuario usuario) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            em.persist(usuario);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.close();
        emf.close();
    }

    public static void edit(Usuario usuario) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            em.merge(usuario);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.close();
        emf.close();
    }

    public static void remove(Usuario usuario) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            em.remove(em.merge(usuario));
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.close();
        emf.close();
    }

    public static Usuario find(Integer id) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Usuario usuario = null;

        tx.begin();
        try {
            usuario = em.find(Usuario.class, id);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.close();
        emf.close();
        return usuario;
    }

    public static List<Usuario> findAll() {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Usuario> lista = new ArrayList<Usuario>();

        tx.begin();
        try {
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
            lista = em.createQuery(cq).getResultList();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.close();
        emf.close();
        return lista;
    }

    public static List<Usuario> findRange(int[] range) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Usuario> lista = new ArrayList<Usuario>();

        tx.begin();
        try {
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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
            javax.persistence.criteria.Root<Usuario> rt = cq.from(Usuario.class);
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
    
    public static Usuario findByNombreUsuario(String nombreUsuario) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Usuario> usuarios = null;
        tx.begin();
        try {
            usuarios = em.createNamedQuery("Usuarios.findByNombreUsuario")
                .setParameter("nombreUsuario", nombreUsuario)
                .getResultList();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.close();
        emf.close();
        if (usuarios==null||usuarios.isEmpty()) {
            return null;
        } else {
            return usuarios.get(0);
        }
    }
   
    public static List<Usuario> findAllHostess() {EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("AdminClientesPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Usuario> usuarios = null;
        tx.begin();
        try {
            usuarios = em.createNamedQuery("Usuarios.findByTipoPerfil")
                .setParameter("nombrePerfil", "Hostess")
                .getResultList();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        em.close();
        emf.close();
        return usuarios;
    }
    
}
