/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesapi.facade;

import com.invbf.adminclientesapi.entity.Eventos;
import com.invbf.adminclientesapi.entity.Formularios;
import com.invbf.adminclientesapi.entity.Perfiles;
import com.invbf.adminclientesapi.entity.Usuarios;
import com.invbf.adminclientesapi.entity.Vistas;
import com.invbf.adminclientesapi.exceptions.NombreUsuarioExistenteException;
import com.invbf.adminclientesapi.exceptions.PerfilExistenteException;
import java.util.List;

/**
 *
 * @author ideacentre
 */
public interface AdminFacade {

    public List<Usuarios> findAllUsuarios();
    
    public void deleteUsuarios(Usuarios elemento);

    public boolean guardarUsuarios(Usuarios elemento)throws NombreUsuarioExistenteException;

    public void deletePerfiles(Perfiles elemento);

    public List<Perfiles> findAllPerfiles();

    public boolean guardarPerfiles(Perfiles elemento)throws PerfilExistenteException;

    public List<Formularios> findAllFormularios();

    public void deleteFormularios(Formularios elemento);

    public boolean guardarFormularios(Formularios elemento);

    public List<Vistas> findAllVistas();

    public void deleteVistas(Vistas elemento);

    public boolean guardarVistas(Vistas elemento);

    public Perfiles findPerfil(Integer idPerfil);

    public List<Usuarios> findAllUsuariosHostess();

    public Usuarios findUsuarios(Integer idUsuario);

    public void agregarEventoUsuarios(Usuarios s, Eventos elemento);

    public Vistas findVistasByNombre(String usuarios);

    public Formularios findFormularioByAccionAndTabla(String crear, String usuarios);
    
}
