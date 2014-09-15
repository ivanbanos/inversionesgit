/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.EntityFacade;

import com.invbf.adminclientesapi.entity.Atributo;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ideacentre
 */
@Stateless
public class AtributoFacade extends AbstractFacade<Atributo> implements AtributoFacadeLocal {
    @PersistenceContext(unitName = "AdminClientesPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AtributoFacade() {
        super(Atributo.class);
    }

    @Override
    public Atributo findByName(String nombre) {
        List<Atributo> atributos = (List<Atributo>)em.createNamedQuery("Clientesatributos.findByIdCliente")
            .setParameter("idCliente", nombre)
            .getResultList();
        if(atributos==null||atributos.isEmpty()){
            return null;
        }else{
            return atributos.get(0);
        }
    }
    
}
