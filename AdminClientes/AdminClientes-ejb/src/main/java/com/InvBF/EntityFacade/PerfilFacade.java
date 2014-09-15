/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.EntityFacade;

import com.invbf.adminclientesapi.entity.Perfil;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ideacentre
 */
@Stateless
public class PerfilFacade extends AbstractFacade<Perfil> implements PerfilFacadeLocal {
    @PersistenceContext(unitName = "AdminClientesPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PerfilFacade() {
        super(Perfil.class);
    }

    @Override
    public Perfil findByNombre(String nombre) {
        List<Perfil> perfiles = (List<Perfil>)em.createNamedQuery("Perfiles.findByNombre")
            .setParameter("nombre", nombre)
            .getResultList();
        if(perfiles==null||perfiles.isEmpty()){
            return null;
        }else{
            return perfiles.get(0);
        }
    }
    
}
