/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.EntityFacade;

import com.invbf.adminclientesapi.entity.Estadocliente;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ideacentre
 */
@Local
public interface EstadoclienteFacadeLocal {

    void create(Estadocliente estadocliente);

    void edit(Estadocliente estadocliente);

    void remove(Estadocliente estadocliente);

    Estadocliente find(Object id);

    List<Estadocliente> findAll();

    List<Estadocliente> findRange(int[] range);

    int count();

    public Estadocliente findByNombreEstadoCliente(String inicial);
    
}
