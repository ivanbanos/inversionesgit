/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.EntityFacade;

import com.invbf.adminclientesapi.entity.Listasclientestareas;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ideacentre
 */
@Local
public interface ListasclientestareasFacadeLocal {

    void create(Listasclientestareas listasclientestareas);

    void edit(Listasclientestareas listasclientestareas);

    void remove(Listasclientestareas listasclientestareas);

    Listasclientestareas find(Object id);

    List<Listasclientestareas> findAll();

    List<Listasclientestareas> findRange(int[] range);

    int count();
    
}
