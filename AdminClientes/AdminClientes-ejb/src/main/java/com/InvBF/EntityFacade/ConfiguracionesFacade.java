/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.EntityFacade;

import com.invbf.adminclientesapi.entity.Clientes;
import com.invbf.adminclientesapi.entity.Configuraciones;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ideacentre
 */
@Stateless
public class ConfiguracionesFacade extends AbstractFacade<Configuraciones> implements ConfiguracionesFacadeLocal {

    @PersistenceContext(unitName = "AdminClientesPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ConfiguracionesFacade() {
        super(Configuraciones.class);
    }

    @Override
    public Configuraciones findByNombre(String cantidadClientes) {
        List<Configuraciones> configuraciones = em.createNamedQuery("Configuraciones.findByNombre")
                .setParameter("nombre", cantidadClientes)
                .getResultList();
        if (configuraciones == null || configuraciones.isEmpty()) {
            return null;
        } else {
            return configuraciones.get(0);
        }
    }
}

