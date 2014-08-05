/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.EntityFacade;

import com.invbf.adminclientesapi.Logs;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ideacentre
 */
@Local
public interface LogsFacadeLocal {

    void create(Logs logs);

    void edit(Logs logs);

    void remove(Logs logs);

    Logs find(Object id);

    List<Logs> findAll();

    List<Logs> findRange(int[] range);

    int count();
    
}
