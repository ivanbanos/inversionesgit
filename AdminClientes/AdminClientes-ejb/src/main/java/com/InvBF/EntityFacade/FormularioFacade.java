/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.EntityFacade;

import com.invbf.adminclientesapi.entity.Formulario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ideacentre
 */
@Stateless
public class FormularioFacade extends AbstractFacade<Formulario> implements FormularioFacadeLocal {
    @PersistenceContext(unitName = "AdminClientesPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FormularioFacade() {
        super(Formulario.class);
    }

    @Override
    public Formulario findByAccionAndTabla(String accion, String tabla) {
        return (Formulario)em.createNamedQuery("Formularios.findByAccionAndTabla")
            .setParameter("accion", accion)
            .setParameter("tabla", tabla)
            .getResultList().get(0);
    }

    @Override
    public void refresh(Formulario formulario) {
        em.refresh(formulario);
    }
    
}
