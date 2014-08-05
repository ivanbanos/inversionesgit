/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.EntityFacade;

import com.invbf.adminclientesapi.Categorias;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ideacentre
 */
@Local
public interface CategoriasFacadeLocal {

    void create(Categorias categorias);

    void edit(Categorias categorias);

    void remove(Categorias categorias);

    Categorias find(Object id);

    List<Categorias> findAll();

    List<Categorias> findRange(int[] range);

    int count();
    
}
