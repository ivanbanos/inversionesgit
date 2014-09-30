/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.EntityFacade;

import com.invbf.adminclientesapi.entity.Configuracion;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ideacentre
 */
@Stateless
public class ConfiguracionFacade extends AbstractFacade<Configuracion> implements ConfiguracionFacadeLocal {
    @PersistenceContext(unitName = "AdminClientesPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ConfiguracionFacade() {
        super(Configuracion.class);
    }

    @Override
    public Configuracion findByNombre(String nombre) {
        List<Configuracion> configuraciones = em.createNamedQuery("Configuraciones.findByNombre")
                .setParameter("nombre", nombre)
                .getResultList();
        if (configuraciones == null || configuraciones.isEmpty()) {
            return null;
        } else {
            return configuraciones.get(0);
        }
    }

    @Override
    public void refresh(Configuracion configuracion) {
        em.refresh(configuracion);
    }
    
}
