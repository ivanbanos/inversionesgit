/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesapi.facade;

import com.invbf.adminclientesapi.Formularios;
import com.invbf.adminclientesapi.Perfiles;
import com.invbf.adminclientesapi.Usuarios;
import com.invbf.adminclientesapi.Vistas;
import java.util.List;

/**
 *
 * @author ideacentre
 */
public interface AdminFacade {

    public List<Usuarios> findAllUsuarios();
    
    public void deleteUsuarios(Usuarios elemento);

    public void guardarUsuarios(Usuarios elemento);

    public void deletePerfiles(Perfiles elemento);

    public List<Perfiles> findAllPerfiles();

    public void guardarPerfiles(Perfiles elemento);

    public List<Formularios> findAllFormularios();

    public void deleteFormularios(Formularios elemento);

    public void guardarFormularios(Formularios elemento);

    public List<Vistas> findAllVistas();

    public void deleteVistas(Vistas elemento);

    public void guardarVistas(Vistas elemento);
}
