/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.EntityFacade;

import com.invbf.adminclientesapi.entity.Estadoscliente;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ideacentre
 */
@Stateless
public class EstadosclienteFacade extends AbstractFacade<Estadoscliente> implements EstadosclienteFacadeLocal {
    @PersistenceContext(unitName = "AdminClientesPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EstadosclienteFacade() {
        super(Estadoscliente.class);
    }

    @Override
    public Estadoscliente findByNombreEstadoCliente(String iniciado) {
        List<Estadoscliente> resultList = em.createNamedQuery("Estadoscliente.findByNombre")
                .setParameter("nombre", iniciado)
                .getResultList();
        return resultList.get(0);
    }
    
}
