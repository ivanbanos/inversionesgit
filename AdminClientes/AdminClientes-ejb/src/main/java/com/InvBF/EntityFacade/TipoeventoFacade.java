/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.EntityFacade;

import com.invbf.adminclientesapi.entity.Tipoevento;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ideacentre
 */
@Stateless
public class TipoeventoFacade extends AbstractFacade<Tipoevento> implements TipoeventoFacadeLocal {
    @PersistenceContext(unitName = "AdminClientesPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoeventoFacade() {
        super(Tipoevento.class);
    }

    @Override
    public Tipoevento findBynombre(String nombre) {
        List<Tipoevento> tiposeventos = (List<Tipoevento>)em.createNamedQuery("Tiposeventos.findByNombre")
            .setParameter("nombre", nombre)
            .getResultList();
        if(tiposeventos==null||tiposeventos.isEmpty()){
            return null;
        }else{
            return tiposeventos.get(0);
        }
    }
    
}
