/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.facade.impl;

import com.invbf.sistemagestionclientes.dao.AccionDao;
import com.invbf.sistemagestionclientes.dao.ConfiguracionDao;
import com.invbf.sistemagestionclientes.dao.FormularioDao;
import com.invbf.sistemagestionclientes.dao.LogDao;
import com.invbf.sistemagestionclientes.dao.UsuarioDao;
import com.invbf.sistemagestionclientes.entity.Accion;
import com.invbf.sistemagestionclientes.entity.Configuracion;
import com.invbf.sistemagestionclientes.entity.Formulario;
import com.invbf.sistemagestionclientes.entity.Listasclientestareas;
import com.invbf.sistemagestionclientes.entity.Log;
import com.invbf.sistemagestionclientes.entity.Tarea;
import com.invbf.sistemagestionclientes.entity.Usuario;
import com.invbf.sistemagestionclientes.exceptions.ClavesNoConcuerdanException;
import com.invbf.sistemagestionclientes.exceptions.NoCambioContrasenaException;
import com.invbf.sistemagestionclientes.exceptions.UsuarioNoConectadoException;
import com.invbf.sistemagestionclientes.exceptions.UsuarioNoExisteException;
import com.invbf.sistemagestionclientes.facade.SystemFacade;
import com.invbf.sistemagestionclientes.util.EmailSender;
import com.invbf.sistemagestionclientes.util.EncryptUtil;
import com.invbf.sistemagestionclientes.util.InfoCorreoCliente;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.imageio.ImageIO;

/**
 *
 * @author ideacentre
 */
public class SystemFacadeImpl implements SystemFacade {

    @Override
    public Usuario iniciarSession(Usuario usuario) throws ClavesNoConcuerdanException, UsuarioNoExisteException, UsuarioNoConectadoException {
        try {
            Usuario usuarios = UsuarioDao.findByNombreUsuario(usuario.getNombreUsuario());
            if (usuarios != null) {
                Usuario usuarioConectado = usuarios;
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
    public Usuario actualizarUsuario(Usuario usuario) {
        return UsuarioDao.find(usuario.getIdUsuario());
    }

    @Override
    public Usuario cambiarContrasena(String contrasena, String nueva, Usuario usuario) throws ClavesNoConcuerdanException, NoCambioContrasenaException {
        try {
            if (!EncryptUtil.comparePassword(contrasena, usuario.getContrasena())) {
                throw new ClavesNoConcuerdanException();
            } else {
                usuario.setContrasena(EncryptUtil.encryptPassword(nueva));
                UsuarioDao.edit(usuario);
                return usuario;
            }
        } catch (NoSuchAlgorithmException ex) {
            throw new NoCambioContrasenaException();
        }
    }

    @Override
    public List<Configuracion> getAllConfiguraciones() {
        return ConfiguracionDao.findAll();
    }

    @Override
    public void setAllConfiguraciones(List<Configuracion> configuraciones) {
        for (Configuracion c : configuraciones) {
            ConfiguracionDao.edit(c);
        }
    }

    @Override
    public void registrarlog(String accion, String tabla, String mensaje, Usuario usuairo) {
        Formulario f = FormularioDao.findByAccionAndTabla(accion, tabla);
        Log log = new Log();
        if (f != null) {
            log.setIdFormulario(f);
        }
        log.setIdUsuario(usuairo);
        log.setMensaje(mensaje);
        LogDao.create(log);
    }

    @Override
    public Configuracion getConfiguracionByName(String nombre) {
        return ConfiguracionDao.findByNombre(nombre);
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
        FileOutputStream fileOuputStream = new FileOutputStream("nombre.png");
        fileOuputStream.write(imagen);

        return file.getAbsolutePath();
    }

    @Override
    public List<InfoCorreoCliente> enviarCorreo(Tarea elemento, String asunto, String cuerpo, boolean enviarimagen) {
        EmailSender es = new EmailSender();
        es.setAuth(true);
        es.setDebug(true);
        es.setFrom(ConfiguracionDao.findByNombre("correo").getValor());
        es.setHost(ConfiguracionDao.findByNombre("host").getValor());
        es.setPort(Integer.parseInt(ConfiguracionDao.findByNombre("port").getValor()));
        es.setProtocol(ConfiguracionDao.findByNombre("protocol").getValor());
        es.setUsername(ConfiguracionDao.findByNombre("username").getValor());
        es.setPassword(ConfiguracionDao.findByNombre("contrasena").getValor());
        Accion enviado = AccionDao.findByNombreAccion("ENVIADO");
        Accion noenviado = AccionDao.findByNombreAccion("NO ENVIADO");
        for (Listasclientestareas lce : elemento.getListasclientestareasList()) {
            try {
                String correoString = lce.getCliente().getCorreo();
                if (correoString.equals("")) {

                    lce.setIdAccion(noenviado);
                    lce.setObservaciones("Correo vacio");
                }
                if (elemento.getIdEvento() != null && elemento.getIdEvento().getImagen() != null && !elemento.getIdEvento().getImagen().equals("") && enviarimagen) {
                    es.sendEmail(correoString, asunto, cuerpo, elemento.getIdEvento().getImagen());
                } else {
                    es.sendEmail(correoString, asunto, cuerpo, "noimage");
                }

                lce.setIdAccion(enviado);
            } catch (Exception ex) {
                lce.setIdAccion(noenviado);
                lce.setObservaciones("problemas externos, llamar administrador");
            }
        }
        return null;
    }

    @Override
    public Usuario getUsuario(Integer idUsuario) {
        return UsuarioDao.find(idUsuario);
    }
}
