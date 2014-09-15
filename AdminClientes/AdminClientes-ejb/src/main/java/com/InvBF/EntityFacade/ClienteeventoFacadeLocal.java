/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.EntityFacade;

import com.invbf.adminclientesapi.entity.Clienteevento;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ideacentre
 */
@Local
public interface ClienteeventoFacadeLocal {

    void create(Clienteevento clienteevento);

    void edit(Clienteevento clienteevento);

    void remove(Clienteevento clienteevento);

    Clienteevento find(Object id);

    List<Clienteevento> findAll();

    List<Clienteevento> findRange(int[] range);

    int count();
    
}
