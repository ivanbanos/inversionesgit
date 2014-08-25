/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.EntityFacade;

import com.invbf.adminclientesapi.entity.Vistas;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ideacentre
 */
@Local
public interface VistasFacadeLocal {

    void create(Vistas vistas);

    void edit(Vistas vistas);

    void remove(Vistas vistas);

    Vistas find(Object id);

    List<Vistas> findAll();

    List<Vistas> findRange(int[] range);

    int count();

    public Vistas findByNombre(String nombre);
    
}
