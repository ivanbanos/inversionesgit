/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.facade.Impl;

import com.InvBF.EntityFacade.ConfiguracionesFacadeLocal;
import com.InvBF.EntityFacade.FormulariosFacadeLocal;
import com.InvBF.EntityFacade.LogsFacadeLocal;
import com.InvBF.EntityFacade.UsuariosFacadeLocal;
import com.InvBF.util.EncryptUtil;
import com.invbf.adminclientesapi.entity.Configuraciones;
import com.invbf.adminclientesapi.entity.Formularios;
import com.invbf.adminclientesapi.entity.Logs;
import com.invbf.adminclientesapi.entity.Usuarios;
import com.invbf.adminclientesapi.exceptions.ClavesNoConcuerdanException;
import com.invbf.adminclientesapi.exceptions.NoCambioContrasenaException;
import com.invbf.adminclientesapi.exceptions.UsuarioNoConectadoException;
import com.invbf.adminclientesapi.exceptions.UsuarioNoExisteException;
import com.invbf.adminclientesapi.facade.SystemFacade;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.imageio.ImageIO;

/**
 *
 * @author ideacentre
 */
@Stateless
public class SystemFacadeImpl implements SystemFacade {

    @EJB
    UsuariosFacadeLocal usuariosFacadeLocal;
    @EJB
    ConfiguracionesFacadeLocal configuracionesFacadeLocal;
    @EJB
    LogsFacadeLocal logsFacadeLocal;
    @EJB
    FormulariosFacadeLocal formulariosFacadeLocal;

    @Override
    public Usuarios iniciarSession(Usuarios usuario) throws ClavesNoConcuerdanException, UsuarioNoExisteException, UsuarioNoConectadoException {
        try {
            Usuarios usuarios = usuariosFacadeLocal.findByNombreUsuario(usuario.getNombreUsuario());
            if (usuarios != null) {
                Usuarios usuarioConectado = usuarios;
                if (!EncryptUtil.comparePassword(usuario.getContrasena(), usuarioConectado.getContrasena())) {
                    throw new ClavesNoConcuerdanException();
                }
                return usuarioConectado;
            } else {
                throw new UsuarioNoExisteException();
            }
        } catch (NoSuchAlgorithmException ex) {
            throw new UsuarioNoConectadoException();
        }
    }

    @Override
    public Usuarios actualizarUsuario(Usuarios usuario) {
        return usuariosFacadeLocal.find(usuario.getIdUsuario());
    }

    @Override
    public Usuarios cambiarContrasena(String contrasena, String nueva, Usuarios usuario) throws ClavesNoConcuerdanException, NoCambioContrasenaException {
        try {
            if (!EncryptUtil.comparePassword(contrasena, usuario.getContrasena())) {
                throw new ClavesNoConcuerdanException();
            } else {
                usuario.setContrasena(EncryptUtil.encryptPassword(nueva));
                usuariosFacadeLocal.edit(usuario);
                return usuario;
            }
        } catch (NoSuchAlgorithmException ex) {
            throw new NoCambioContrasenaException();
        }
    }

    @Override
    public List<Configuraciones> getAllConfiguraciones() {
        return configuracionesFacadeLocal.findAll();
    }

    @Override
    public void setAllConfiguraciones(List<Configuraciones> configuraciones) {
        for (Configuraciones c : configuraciones) {
            configuracionesFacadeLocal.edit(c);
        }
    }

    @Override
    public void registrarlog(String accion, String tabla, String mensaje, Usuarios usuairo) {
        Formularios f = formulariosFacadeLocal.findByAccionAndTabla(accion, tabla);
        Logs log = new Logs();
        if (f != null) {
            log.setIdFormulario(f);
        }
        log.setIdUsuario(usuairo);
        log.setMensaje(mensaje);
        logsFacadeLocal.create(log);
    }

    @Override
    public Configuraciones getConfiguracionByName(String nombre) {
        return configuracionesFacadeLocal.findByNombre(nombre);
    }

    @Override
    public ByteArrayOutputStream getOutputStreamImage(byte[] imagen, String mime) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BufferedImage bi = ImageIO.read(new ByteArrayInputStream(imagen));
        if (mime.endsWith("png")) {
            ImageIO.write(bi, "png", out);
        }
        return out;
    }

    @Override
    public String getPathImage(byte[] imagen, String mime, String nombre) throws IOException {
        FileInputStream fileInputStream = null;

        File file = new File("nombre.png");
        try (FileOutputStream fileOuputStream = new FileOutputStream("nombre.png")) {
            fileOuputStream.write(imagen);
        }

        return file.getAbsolutePath();
    }
}
