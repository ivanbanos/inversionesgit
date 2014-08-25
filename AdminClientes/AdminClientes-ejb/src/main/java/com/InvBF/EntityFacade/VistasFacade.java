/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.EntityFacade;

import com.invbf.adminclientesapi.entity.Vistas;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ideacentre
 */
@Stateless
public class VistasFacade extends AbstractFacade<Vistas> implements VistasFacadeLocal {
    @PersistenceContext(unitName = "AdminClientesPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VistasFacade() {
        super(Vistas.class);
    }

    @Override
    public Vistas findByNombre(String nombre) {
        return (Vistas)em.createNamedQuery("Vistas.findByNombreVista")
            .setParameter("nombreVista", nombre)
            .getResultList().get(0);
    }
    
}
