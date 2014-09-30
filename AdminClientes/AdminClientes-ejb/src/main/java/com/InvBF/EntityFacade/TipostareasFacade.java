/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.EntityFacade;

import com.invbf.adminclientesapi.entity.Tipotarea;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ideacentre
 */
@Stateless
public class TipostareasFacade extends AbstractFacade<Tipotarea> implements TipostareasFacadeLocal {
    @PersistenceContext(unitName = "AdminClientesPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipostareasFacade() {
        super(Tipotarea.class);
    }

    @Override
    public Tipotarea findBynombre(String nombre) {
        List<Tipotarea> tipotareas = (List<Tipotarea>)em.createNamedQuery("Tipostareas.findByNombre")
            .setParameter("nombre", nombre)
            .getResultList();
        if(tipotareas==null||tipotareas.isEmpty()){
            return null;
        }else{
            return tipotareas.get(0);
        }
    }

    @Override
    public void refresh(Tipotarea tipostareas) {
        em.refresh(tipostareas);
    }
    
}
