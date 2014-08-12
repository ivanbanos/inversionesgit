/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.EntityFacade;

import com.invbf.adminclientesapi.entity.Listasclientesevento;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ideacentre
 */
@Local
public interface ListasclienteseventoFacadeLocal {

    void create(Listasclientesevento listasclientesevento);

    void edit(Listasclientesevento listasclientesevento);

    void remove(Listasclientesevento listasclientesevento);

    Listasclientesevento find(Object id);

    List<Listasclientesevento> findAll();

    List<Listasclientesevento> findRange(int[] range);

    int count();
    
}
