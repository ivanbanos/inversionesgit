/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.EntityFacade;

import com.invbf.adminclientesapi.entity.Accion;
import com.invbf.adminclientesapi.entity.Tarea;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ideacentre
 */
@Local
public interface TareasFacadeLocal {

    void create(Tarea tareas);

    void refresh(Tarea tareas);

    void edit(Tarea tareas);

    void remove(Tarea tareas);

    Tarea find(Object id);

    List<Tarea> findAll();

    List<Tarea> findRange(int[] range);

    int count();
    
}
