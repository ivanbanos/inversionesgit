/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.EntityFacade;

import com.invbf.adminclientesapi.entity.Perfil;
import com.invbf.adminclientesapi.entity.Permiso;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ideacentre
 */
@Local
public interface PermisosFacadeLocal {

    void create(Permiso perfil);

    void refresh(Permiso perfil);

    void edit(Permiso perfil);

    void remove(Permiso perfil);

    Permiso find(Object id);

    List<Permiso> findAll();

    List<Permiso> findRange(int[] range);

    int count();

    public Object findByNombre(String nombre);
    
}
