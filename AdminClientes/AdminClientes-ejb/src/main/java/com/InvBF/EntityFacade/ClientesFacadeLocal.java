/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.EntityFacade;

import com.invbf.adminclientesapi.Clientes;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ideacentre
 */
@Local
public interface ClientesFacadeLocal {

    void create(Clientes clientes);

    void edit(Clientes clientes);

    void remove(Clientes clientes);

    Clientes find(Object id);

    List<Clientes> findAll();

    List<Clientes> findRange(int[] range);

    int count();
    
}
