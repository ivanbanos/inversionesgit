/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.EntityFacade;

import com.invbf.adminclientesapi.entity.Vista;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ideacentre
 */
@Stateless
public class VistaFacade extends AbstractFacade<Vista> implements VistaFacadeLocal {
    @PersistenceContext(unitName = "AdminClientesPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VistaFacade() {
        super(Vista.class);
    }

    @Override
    public Vista findByNombre(String nombre) {
        return (Vista)em.createNamedQuery("Vistas.findByNombreVista")
            .setParameter("nombreVista", nombre)
            .getResultList().get(0);
    }
    
}
