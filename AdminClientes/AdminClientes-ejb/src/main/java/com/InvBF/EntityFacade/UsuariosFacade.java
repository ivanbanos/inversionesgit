/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.EntityFacade;

import com.invbf.adminclientesapi.entity.Usuarios;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ideacentre
 */
@Stateless
public class UsuariosFacade extends AbstractFacade<Usuarios> implements UsuariosFacadeLocal {

    @PersistenceContext(unitName = "AdminClientesPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuariosFacade() {
        super(Usuarios.class);
    }

    @Override
    public Usuarios findByNombreUsuario(String nombreUsuario) {
        List<Usuarios> usuarios = (List<Usuarios>) em.createNamedQuery("Usuarios.findByNombreUsuario")
                .setParameter("nombreUsuario", nombreUsuario)
                .getResultList();
        if (usuarios.isEmpty()) {
            return null;
        } else {
            return usuarios.get(0);
        }
    }

    @Override
    public List<Usuarios> findAllHostess() {
        return em.createNamedQuery("Usuarios.findByTipoPerfil")
                .setParameter("nombrePerfil", "Hostess")
                .getResultList();
    }
}
