/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.EntityFacade;

import com.invbf.adminclientesapi.entity.Atributos;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ideacentre
 */
@Local
public interface AtributosFacadeLocal {

    void create(Atributos atributos);

    void edit(Atributos atributos);

    void remove(Atributos atributos);

    Atributos find(Object id);

    List<Atributos> findAll();

    List<Atributos> findRange(int[] range);

    int count();

    public Atributos findByName(String correo);
    
}
