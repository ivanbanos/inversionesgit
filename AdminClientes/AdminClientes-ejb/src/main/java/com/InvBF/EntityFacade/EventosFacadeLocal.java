/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.EntityFacade;

import com.invbf.adminclientesapi.Eventos;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ideacentre
 */
@Local
public interface EventosFacadeLocal {

    void create(Eventos eventos);

    void edit(Eventos eventos);

    void remove(Eventos eventos);

    Eventos find(Object id);

    List<Eventos> findAll();

    List<Eventos> findRange(int[] range);

    int count();
    
}
