/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.EntityFacade;

import com.invbf.adminclientesapi.entity.Formularios;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ideacentre
 */
@Local
public interface FormulariosFacadeLocal {

    void create(Formularios formularios);

    void edit(Formularios formularios);

    void remove(Formularios formularios);

    Formularios find(Object id);

    List<Formularios> findAll();

    List<Formularios> findRange(int[] range);

    int count();

    public Formularios findByAccionAndTabla(String accion, String tabla);
    
}
