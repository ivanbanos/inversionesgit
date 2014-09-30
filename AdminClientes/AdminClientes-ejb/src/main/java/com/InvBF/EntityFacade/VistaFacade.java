/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.EntityFacade;

import com.invbf.adminclientesapi.entity.Accion;
import com.invbf.adminclientesapi.entity.Vista;
import java.util.List;
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
        List<Vista> vistas = (List<Vista>)em.createNamedQuery("Vistas.findByNombreVista")
            .setParameter("nombreVista", nombre)
            .getResultList();
        if(vistas==null||vistas.isEmpty()){
            return null;
        }else{
            return vistas.get(0);
        }
    }

    @Override
    public void refresh(Vista vista) {
        em.refresh(vista);
    }
    
}
