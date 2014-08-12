/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.EntityFacade;

import com.invbf.adminclientesapi.entity.Casinos;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ideacentre
 */
@Local
public interface CasinosFacadeLocal {

    void create(Casinos casinos);

    void edit(Casinos casinos);

    void remove(Casinos casinos);

    Casinos find(Object id);

    List<Casinos> findAll();

    List<Casinos> findRange(int[] range);

    int count();
    
}
