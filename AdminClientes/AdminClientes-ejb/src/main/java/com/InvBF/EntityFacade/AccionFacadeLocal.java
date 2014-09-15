/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.EntityFacade;

import com.invbf.adminclientesapi.entity.Accion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ideacentre
 */
@Local
public interface AccionFacadeLocal {

    void create(Accion accion);

    void edit(Accion accion);

    void remove(Accion accion);

    Accion find(Object id);

    List<Accion> findAll();

    List<Accion> findRange(int[] range);

    int count();
    
}
