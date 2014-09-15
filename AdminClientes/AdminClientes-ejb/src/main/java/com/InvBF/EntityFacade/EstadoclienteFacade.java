/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.EntityFacade;

import com.invbf.adminclientesapi.entity.Estadocliente;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ideacentre
 */
@Stateless
public class EstadoclienteFacade extends AbstractFacade<Estadocliente> implements EstadoclienteFacadeLocal {
    @PersistenceContext(unitName = "AdminClientesPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EstadoclienteFacade() {
        super(Estadocliente.class);
    }

    @Override
    public Estadocliente findByNombreEstadoCliente(String inicial) {
        List<Estadocliente> resultList = em.createNamedQuery("Estadoscliente.findByNombre")
                .setParameter("nombre", inicial)
                .getResultList();
        if (resultList == null || resultList.isEmpty()) {
            return null;
        } else {
            return resultList.get(0);
        }
    }
    
}
