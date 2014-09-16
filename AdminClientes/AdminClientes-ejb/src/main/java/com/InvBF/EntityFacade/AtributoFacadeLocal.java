/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.EntityFacade;

import com.invbf.adminclientesapi.entity.Atributo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ideacentre
 */
@Local
public interface AtributoFacadeLocal {

    void create(Atributo atributo);

    void edit(Atributo atributo);

    void remove(Atributo atributo);

    Atributo find(Object id);

    List<Atributo> findAll();

    List<Atributo> findRange(int[] range);

    int count();
    
}
