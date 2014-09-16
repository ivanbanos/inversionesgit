/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesapi.facade;

import com.invbf.adminclientesapi.entity.Configuracion;
import com.invbf.adminclientesapi.entity.Tarea;
import com.invbf.adminclientesapi.entity.Usuario;
import com.invbf.adminclientesapi.exceptions.ClavesNoConcuerdanException;
import com.invbf.adminclientesapi.exceptions.NoCambioContrasenaException;
import com.invbf.adminclientesapi.exceptions.UsuarioNoConectadoException;
import com.invbf.adminclientesapi.exceptions.UsuarioNoExisteException;
import com.invbf.adminclientesapi.util.InfoCorreoCliente;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author ideacentre
 */
public interface SystemFacade {

    public Usuario iniciarSession(Usuario usuario)throws ClavesNoConcuerdanException, UsuarioNoExisteException, UsuarioNoConectadoException ;

    public Usuario actualizarUsuario(Usuario usuario);

    public Usuario cambiarContrasena(String contrasena, String nueva, Usuario usuario)throws ClavesNoConcuerdanException, NoCambioContrasenaException;

    public List<Configuracion> getAllConfiguraciones();

    public void setAllConfiguraciones(List<Configuracion> configuraciones);

    public void registrarlog(String accion, String tabla, String mensaje, Usuario usuairo);

    public Configuracion getConfiguracionByName(String nombre);

    public ByteArrayOutputStream getOutputStreamImage(byte[] imagen, String mime)throws IOException ;
    
    public String getPathImage(byte[] imagen, String mime, String nombre)throws IOException ;

    public List<InfoCorreoCliente> enviarCorreo(Tarea elemento) ;
}
