/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.EntityFacade;

import com.invbf.adminclientesapi.entity.Accion;
import com.invbf.adminclientesapi.entity.Tarea;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ideacentre
 */
@Stateless
public class TareasFacade extends AbstractFacade<Tarea> implements TareasFacadeLocal {
    @PersistenceContext(unitName = "AdminClientesPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TareasFacade() {
        super(Tarea.class);
    }

    @Override
    public void refresh(Tarea tareas) {
        em.refresh(tareas);
    }
    
}
