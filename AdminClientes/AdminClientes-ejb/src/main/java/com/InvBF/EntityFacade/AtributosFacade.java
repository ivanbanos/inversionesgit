/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.EntityFacade;

import com.invbf.adminclientesapi.entity.Atributos;
import com.invbf.adminclientesapi.entity.Formularios;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ideacentre
 */
@Stateless
public class AtributosFacade extends AbstractFacade<Atributos> implements AtributosFacadeLocal {
    @PersistenceContext(unitName = "AdminClientesPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AtributosFacade() {
        super(Atributos.class);
    }

    @Override
    public Atributos findByName(String nombre) {
        return (Atributos)em.createNamedQuery("Atributos.findByNombre")
            .setParameter("nombre", nombre)
            .getResultList().get(0);
    }
    
}
