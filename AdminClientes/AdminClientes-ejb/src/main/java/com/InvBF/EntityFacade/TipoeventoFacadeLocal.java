/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.EntityFacade;

import com.invbf.adminclientesapi.entity.Tipoevento;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ideacentre
 */
@Local
public interface TipoeventoFacadeLocal {

    void create(Tipoevento tipoevento);

    void edit(Tipoevento tipoevento);

    void remove(Tipoevento tipoevento);

    Tipoevento find(Object id);

    List<Tipoevento> findAll();

    List<Tipoevento> findRange(int[] range);

    int count();

    public Tipoevento findBynombre(String email);
    
}
