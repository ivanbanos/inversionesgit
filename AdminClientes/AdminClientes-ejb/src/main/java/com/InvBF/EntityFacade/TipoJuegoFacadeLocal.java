/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.EntityFacade;

import com.invbf.adminclientesapi.entity.TipoJuego;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ideacentre
 */
@Local
public interface TipoJuegoFacadeLocal {

    void create(TipoJuego tipoJuego);

    void refresh(TipoJuego tipoJuego);

    void edit(TipoJuego tipoJuego);

    void remove(TipoJuego tipoJuego);

    TipoJuego find(Object id);

    List<TipoJuego> findAll();

    List<TipoJuego> findRange(int[] range);

    int count();
    
}
