/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.facade;

import com.invbf.sistemagestionclientes.entity.Permiso;
import com.invbf.sistemagestionclientes.exceptions.clienteInexistenteException;
import java.util.List;

/**
 *
 * @author ideacentre
 */
public interface ManagerUserFacade {

    public List<Permiso> getAllPermisos();

    public void eliminarPermiso(Permiso permiso);

    public String ejecutarPermiso(Permiso permiso)throws clienteInexistenteException;

    public void addPermiso(Permiso permiso);

    
}
