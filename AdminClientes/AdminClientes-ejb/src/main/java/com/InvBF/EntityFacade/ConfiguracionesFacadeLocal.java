/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.EntityFacade;

import com.invbf.adminclientesapi.entity.Configuraciones;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ideacentre
 */
@Local
public interface ConfiguracionesFacadeLocal {

    void create(Configuraciones configuraciones);

    void edit(Configuraciones configuraciones);

    void remove(Configuraciones configuraciones);

    Configuraciones find(Object id);

    List<Configuraciones> findAll();

    List<Configuraciones> findRange(int[] range);

    int count();
    
}
