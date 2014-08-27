/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesapi.facade;

import com.invbf.adminclientesapi.entity.Configuraciones;
import com.invbf.adminclientesapi.entity.Usuarios;
import com.invbf.adminclientesapi.exceptions.ClavesNoConcuerdanException;
import com.invbf.adminclientesapi.exceptions.NoCambioContrasenaException;
import com.invbf.adminclientesapi.exceptions.UsuarioNoConectadoException;
import com.invbf.adminclientesapi.exceptions.UsuarioNoExisteException;
import java.util.List;

/**
 *
 * @author ideacentre
 */
public interface SystemFacade {

    public Usuarios iniciarSession(Usuarios usuario)throws ClavesNoConcuerdanException, UsuarioNoExisteException, UsuarioNoConectadoException ;

    public Usuarios actualizarUsuario(Usuarios usuario);

    public Usuarios cambiarContrasena(String contrasena, String nueva, Usuarios usuario)throws ClavesNoConcuerdanException, NoCambioContrasenaException;

    public List<Configuraciones> getAllConfiguraciones();

    public void setAllConfiguraciones(List<Configuraciones> configuraciones);

    public void registrarlog(String accion, String tabla, String mensaje, Usuarios usuairo);
}
