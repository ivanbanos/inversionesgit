/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.facade;

import com.invbf.sistemagestionclientes.entity.Formulario;
import com.invbf.sistemagestionclientes.entity.Perfil;
import com.invbf.sistemagestionclientes.entity.Tarea;
import com.invbf.sistemagestionclientes.entity.Usuario;
import com.invbf.sistemagestionclientes.entity.Vista;
import com.invbf.sistemagestionclientes.exceptions.NombreUsuarioExistenteException;
import com.invbf.sistemagestionclientes.exceptions.PerfilExistenteException;
import java.util.List;

/**
 *
 * @author ideacentre
 */
public interface AdminFacade {

    public List<Usuario> findAllUsuarios();
   
    public void deleteUsuarios(Usuario elemento);
    
    public boolean guardarUsuarios(Usuario elemento) throws NombreUsuarioExistenteException;
    
    public void deletePerfiles(Perfil elemento);
    
    public List<Perfil> findAllPerfiles();
    
    public boolean guardarPerfiles(Perfil elemento) throws PerfilExistenteException;
    
    public List<Formulario> findAllFormularios();
    
    public void deleteFormularios(Formulario elemento);
    
    public boolean guardarFormularios(Formulario elemento);
    
    public List<Vista> findAllVistas();
    
    public void deleteVistas(Vista elemento);
    
    public boolean guardarVistas(Vista elemento);
    
    public Perfil findPerfil(Integer idPerfil);
    
    public List<Usuario> findAllUsuariosHostess() ;
    
    public Usuario findUsuarios(Integer idUsuario);

    public void agregarTareaUsuarios(Usuario s, Tarea elemento);

    public Vista findVistasByNombre(String nombre);

    public Formulario findFormularioByAccionAndTabla(String accion, String tabla);
    
}
