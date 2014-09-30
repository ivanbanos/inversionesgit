/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.EntityFacade;

import com.invbf.adminclientesapi.entity.Configuracion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ideacentre
 */
@Local
public interface ConfiguracionFacadeLocal {

    void create(Configuracion configuracion);

    void refresh(Configuracion configuracion);

    void edit(Configuracion configuracion);

    void remove(Configuracion configuracion);

    Configuracion find(Object id);

    List<Configuracion> findAll();

    List<Configuracion> findRange(int[] range);

    int count();

    public Configuracion findByNombre(String nombre);
    
}
