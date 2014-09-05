/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesapi.facade;

import com.invbf.adminclientesapi.entity.Configuraciones;
import com.invbf.adminclientesapi.entity.Eventos;
import com.invbf.adminclientesapi.entity.Usuarios;
import com.invbf.adminclientesapi.exceptions.ClavesNoConcuerdanException;
import com.invbf.adminclientesapi.exceptions.MensajeNoEnviadoException;
import com.invbf.adminclientesapi.exceptions.NoCambioContrasenaException;
import com.invbf.adminclientesapi.exceptions.UsuarioNoConectadoException;
import com.invbf.adminclientesapi.exceptions.UsuarioNoExisteException;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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

    public Configuraciones getConfiguracionByName(String nombre);

    public ByteArrayOutputStream getOutputStreamImage(byte[] imagen, String mime)throws IOException ;
    
    public String getPathImage(byte[] imagen, String mime, String nombre)throws IOException ;

    public void enviarCorreo(Eventos elemento)throws MensajeNoEnviadoException, IOException ;
}
