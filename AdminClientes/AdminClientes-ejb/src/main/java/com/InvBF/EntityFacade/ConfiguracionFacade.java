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
    public Configuracion findByNombre(String cantidadClientes) {
        List<Configuracion> configuracion = em.createNamedQuery("Configuraciones.findByNombre")
                .setParameter("nombre", cantidadClientes)
                .getResultList();
        if (configuracion == null || configuracion.isEmpty()) {
            return null;
        } else {
            return configuracion.get(0);
        }
    }
    
}
