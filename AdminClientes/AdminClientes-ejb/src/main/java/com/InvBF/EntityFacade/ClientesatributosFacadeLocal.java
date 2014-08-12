/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.EntityFacade;

import com.invbf.adminclientesapi.entity.Clientesatributos;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ideacentre
 */
@Local
public interface ClientesatributosFacadeLocal {

    void create(Clientesatributos clientesatributos);

    void edit(Clientesatributos clientesatributos);

    void remove(Clientesatributos clientesatributos);

    Clientesatributos find(Object id);

    List<Clientesatributos> findAll();

    List<Clientesatributos> findRange(int[] range);

    int count();
    
}
