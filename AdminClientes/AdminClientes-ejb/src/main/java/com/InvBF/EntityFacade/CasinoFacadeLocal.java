/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.EntityFacade;

import com.invbf.adminclientesapi.entity.Casino;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ideacentre
 */
@Local
public interface CasinoFacadeLocal {

    void create(Casino casino);

    void refresh(Casino casino);

    void edit(Casino casino);

    void remove(Casino casino);

    Casino find(Object id);

    List<Casino> findAll();

    List<Casino> findRange(int[] range);

    int count();
    
}
