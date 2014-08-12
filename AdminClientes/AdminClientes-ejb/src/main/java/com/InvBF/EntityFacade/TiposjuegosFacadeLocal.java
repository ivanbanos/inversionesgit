/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.EntityFacade;

import com.invbf.adminclientesapi.entity.Tiposjuegos;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ideacentre
 */
@Local
public interface TiposjuegosFacadeLocal {

    void create(Tiposjuegos tiposjuegos);

    void edit(Tiposjuegos tiposjuegos);

    void remove(Tiposjuegos tiposjuegos);

    Tiposjuegos find(Object id);

    List<Tiposjuegos> findAll();

    List<Tiposjuegos> findRange(int[] range);

    int count();
    
}
