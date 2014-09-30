/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.EntityFacade;

import com.invbf.adminclientesapi.entity.Atributo;
import com.invbf.adminclientesapi.entity.Cliente;
import com.invbf.adminclientesapi.entity.Clienteatributo;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ideacentre
 */
@Stateless
public class ClienteatributoFacade extends AbstractFacade<Clienteatributo> implements ClienteatributoFacadeLocal {
    @PersistenceContext(unitName = "AdminClientesPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClienteatributoFacade() {
        super(Clienteatributo.class);
    }

    @Override
    public List<Clienteatributo> findByCliente(Cliente elemento) {
       List<Clienteatributo> clientesatributos = (List<Clienteatributo>)em.createNamedQuery("Clientesatributos.findByIdCliente")
            .setParameter("idCliente", elemento.getIdCliente())
            .getResultList();
        if(clientesatributos==null||clientesatributos.isEmpty()){
            return null;
        }else{
            return clientesatributos;
        }
    }

    @Override
    public List<Clienteatributo> findByAtributo(Atributo elemento) {
       List<Clienteatributo> clientesatributos = (List<Clienteatributo>)em.createNamedQuery("Clientesatributos.findByIdAtributo")
            .setParameter("idAtributo", elemento.getIdAtributo())
            .getResultList();
        if(clientesatributos==null||clientesatributos.isEmpty()){
            return null;
        }else{
            return clientesatributos;
        }
    }

    @Override
    public void refresh(Clienteatributo clienteatributo) {
        em.refresh(clienteatributo);
    }
    
}
