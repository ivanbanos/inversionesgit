/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.EntityFacade;

import com.invbf.adminclientesapi.entity.Atributos;
import com.invbf.adminclientesapi.entity.Clientes;
import com.invbf.adminclientesapi.entity.Clientesatributos;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ideacentre
 */
@Stateless
public class ClientesatributosFacade extends AbstractFacade<Clientesatributos> implements ClientesatributosFacadeLocal {
    @PersistenceContext(unitName = "AdminClientesPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClientesatributosFacade() {
        super(Clientesatributos.class);
    }

    @Override
    public List<Clientesatributos> findByCliente(Clientes elemento) {
        List<Clientesatributos> clientesatributos = (List<Clientesatributos>)em.createNamedQuery("Clientesatributos.findByIdCliente")
            .setParameter("idCliente", elemento.getIdCliente())
            .getResultList();
        if(clientesatributos==null||clientesatributos.isEmpty()){
            return null;
        }else{
            return clientesatributos;
        }
    }

    @Override
    public List<Clientesatributos> findByAtributo(Atributos elemento) {
        List<Clientesatributos> clientesatributos = (List<Clientesatributos>)em.createNamedQuery("Clientesatributos.findByIdAtributo")
            .setParameter("idAtributo", elemento.getIdAtributo())
            .getResultList();
        if(clientesatributos==null||clientesatributos.isEmpty()){
            return null;
        }else{
            return clientesatributos;
        }
    }
    
}
