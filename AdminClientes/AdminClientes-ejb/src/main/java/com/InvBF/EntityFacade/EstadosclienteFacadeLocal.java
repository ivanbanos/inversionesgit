/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.EntityFacade;

import com.invbf.adminclientesapi.entity.Estadoscliente;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ideacentre
 */
@Local
public interface EstadosclienteFacadeLocal {

    void create(Estadoscliente estadoscliente);

    void edit(Estadoscliente estadoscliente);

    void remove(Estadoscliente estadoscliente);

    Estadoscliente find(Object id);

    List<Estadoscliente> findAll();

    List<Estadoscliente> findRange(int[] range);

    int count();

    public Estadoscliente findByNombreEstadoCliente(String iniciado);
    
}
