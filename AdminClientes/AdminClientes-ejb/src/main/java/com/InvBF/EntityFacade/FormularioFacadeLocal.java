/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.EntityFacade;

import com.invbf.adminclientesapi.entity.Formulario;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ideacentre
 */
@Local
public interface FormularioFacadeLocal {

    void create(Formulario formulario);

    void refresh(Formulario formulario);

    void edit(Formulario formulario);

    void remove(Formulario formulario);

    Formulario find(Object id);

    List<Formulario> findAll();

    List<Formulario> findRange(int[] range);

    int count();

    public Formulario findByAccionAndTabla(String accion, String tabla);
    
}
