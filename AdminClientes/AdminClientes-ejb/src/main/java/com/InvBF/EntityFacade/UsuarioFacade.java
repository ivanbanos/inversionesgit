/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.EntityFacade;

import com.invbf.adminclientesapi.entity.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ideacentre
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> implements UsuarioFacadeLocal {
    @PersistenceContext(unitName = "AdminClientesPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }

   @Override
    public Usuario findByNombreUsuario(String nombreUsuario) {
        List<Usuario> usuarios = (List<Usuario>) em.createNamedQuery("Usuarios.findByNombreUsuario")
                .setParameter("nombreUsuario", nombreUsuario)
                .getResultList();
        if (usuarios.isEmpty()) {
            return null;
        } else {
            return usuarios.get(0);
        }
    }

    @Override
    public List<Usuario> findAllHostess() {
        return em.createNamedQuery("Usuarios.findByTipoPerfil")
                .setParameter("nombrePerfil", "Hostess")
                .getResultList();
    }
    
}
