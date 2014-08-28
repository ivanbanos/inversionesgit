/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.EntityFacade;

import com.invbf.adminclientesapi.entity.Perfiles;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ideacentre
 */
@Stateless
public class PerfilesFacade extends AbstractFacade<Perfiles> implements PerfilesFacadeLocal {
    @PersistenceContext(unitName = "AdminClientesPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PerfilesFacade() {
        super(Perfiles.class);
    }

    @Override
    public Perfiles findByNombre(String nombre) {
        List<Perfiles> perfiles = (List<Perfiles>)em.createNamedQuery("Perfiles.findByNombre")
            .setParameter("nombre", nombre)
            .getResultList();
        if(perfiles==null||perfiles.isEmpty()){
            return null;
        }else{
            return perfiles.get(0);
        }
    }
    
}
