/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.facade;

import com.invbf.sistemagestionclientes.entity.Configuracion;
import com.invbf.sistemagestionclientes.entity.Tarea;
import com.invbf.sistemagestionclientes.entity.Usuario;
import com.invbf.sistemagestionclientes.exceptions.ClavesNoConcuerdanException;
import com.invbf.sistemagestionclientes.exceptions.NoCambioContrasenaException;
import com.invbf.sistemagestionclientes.exceptions.UsuarioNoConectadoException;
import com.invbf.sistemagestionclientes.exceptions.UsuarioNoExisteException;
import com.invbf.sistemagestionclientes.util.InfoCorreoCliente;
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

    public Usuario getUsuario(Integer idUsuario);
}
