/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.facade.Impl;

import com.InvBF.EntityFacade.ClientesFacadeLocal;
import com.invbf.adminclientesapi.Clientes;
import com.invbf.adminclientesapi.facade.MarketingUserFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author ideacentre
 */
@Stateless
public class MarketingUserFacadeImpl implements MarketingUserFacade{

    @EJB
    ClientesFacadeLocal clientesFacadeLocal;
    
    @Override
    public List<Clientes> findAllClientes() {
        return clientesFacadeLocal.findAll();
    }
    
}
