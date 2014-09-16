/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.EntityFacade;

import com.invbf.adminclientesapi.entity.Accion;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ideacentre
 */
@Stateless
public class AccionFacade extends AbstractFacade<Accion> implements AccionFacadeLocal {
    @PersistenceContext(unitName = "AdminClientesPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccionFacade() {
        super(Accion.class);
    }

    @Override
    public Accion findByNombreAccion(String nombre) {
         List<Accion> acciones = (List<Accion>)em.createNamedQuery("Acciones.findByNombre")
            .setParameter("nombre", nombre)
            .getResultList();
        if(acciones==null||acciones.isEmpty()){
            return null;
        }else{
            return acciones.get(0);
        }
    }
    
}
