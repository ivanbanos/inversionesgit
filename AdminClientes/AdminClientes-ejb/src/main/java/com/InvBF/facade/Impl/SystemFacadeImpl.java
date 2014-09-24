/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.facade.Impl;

import com.InvBF.EntityFacade.AccionFacadeLocal;
import com.InvBF.EntityFacade.AtributoFacadeLocal;
import com.InvBF.EntityFacade.ClienteatributoFacadeLocal;
import com.InvBF.EntityFacade.ConfiguracionFacadeLocal;
import com.InvBF.EntityFacade.FormularioFacadeLocal;
import com.InvBF.EntityFacade.ListasclientestareasFacadeLocal;
import com.InvBF.EntityFacade.LogFacadeLocal;
import com.InvBF.EntityFacade.UsuarioFacadeLocal;
import com.InvBF.util.EmailSender;
import com.InvBF.util.EncryptUtil;
import com.invbf.adminclientesapi.entity.Accion;
import com.invbf.adminclientesapi.entity.Configuracion;
import com.invbf.adminclientesapi.entity.Formulario;
import com.invbf.adminclientesapi.entity.Listasclientestareas;
import com.invbf.adminclientesapi.entity.Log;
import com.invbf.adminclientesapi.entity.Tarea;
import com.invbf.adminclientesapi.entity.Usuario;
import com.invbf.adminclientesapi.exceptions.ClavesNoConcuerdanException;
import com.invbf.adminclientesapi.exceptions.NoCambioContrasenaException;
import com.invbf.adminclientesapi.exceptions.UsuarioNoConectadoException;
import com.invbf.adminclientesapi.exceptions.UsuarioNoExisteException;
import com.invbf.adminclientesapi.facade.SystemFacade;
import com.invbf.adminclientesapi.util.InfoCorreoCliente;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.imageio.ImageIO;
import javax.mail.MessagingException;

/**
 *
 * @author ideacentre
 */
@Stateless
public class SystemFacadeImpl implements SystemFacade {

    @EJB
    UsuarioFacadeLocal usuarioFacadeLocal;
    @EJB
    ConfiguracionFacadeLocal configuracionFacadeLocal;
    @EJB
    LogFacadeLocal logFacadeLocal;
    @EJB
    FormularioFacadeLocal formularioFacadeLocal;
    @EJB
    AtributoFacadeLocal atributoFacadeLocal;
    @EJB
    ListasclientestareasFacadeLocal  listasclientestareasFacadeLocal;
    @EJB
    ClienteatributoFacadeLocal clienteatributoFacadeLocal;
    @EJB
    AccionFacadeLocal accionFacadeLocal;

    @Override
    public Usuario iniciarSession(Usuario usuario) throws ClavesNoConcuerdanException, UsuarioNoExisteException, UsuarioNoConectadoException {
        try {
            Usuario usuarios = usuarioFacadeLocal.findByNombreUsuario(usuario.getNombreUsuario());
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
        return usuarioFacadeLocal.find(usuario.getIdUsuario());
    }

    @Override
    public Usuario cambiarContrasena(String contrasena, String nueva, Usuario usuario) throws ClavesNoConcuerdanException, NoCambioContrasenaException {
        try {
            if (!EncryptUtil.comparePassword(contrasena, usuario.getContrasena())) {
                throw new ClavesNoConcuerdanException();
            } else {
                usuario.setContrasena(EncryptUtil.encryptPassword(nueva));
                usuarioFacadeLocal.edit(usuario);
                return usuario;
            }
        } catch (NoSuchAlgorithmException ex) {
            throw new NoCambioContrasenaException();
        }
    }

    @Override
    public List<Configuracion> getAllConfiguraciones() {
        return configuracionFacadeLocal.findAll();
    }

    @Override
    public void setAllConfiguraciones(List<Configuracion> configuraciones) {
        for (Configuracion c : configuraciones) {
            configuracionFacadeLocal.edit(c);
        }
    }

    @Override
    public void registrarlog(String accion, String tabla, String mensaje, Usuario usuairo) {
        Formulario f = formularioFacadeLocal.findByAccionAndTabla(accion, tabla);
        Log log = new Log();
        if (f != null) {
            log.setIdFormulario(f);
        }
        log.setIdUsuario(usuairo);
        log.setMensaje(mensaje);
        logFacadeLocal.create(log);
    }

    @Override
    public Configuracion getConfiguracionByName(String nombre) {
        return configuracionFacadeLocal.findByNombre(nombre);
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

    @Override
    public List<InfoCorreoCliente> enviarCorreo(Tarea elemento) {
        EmailSender es = new EmailSender();
        es.setAuth(true);
        es.setDebug(true);
        es.setFrom(configuracionFacadeLocal.findByNombre("correo").getValor());
        es.setHost(configuracionFacadeLocal.findByNombre("host").getValor());
        es.setPort(Integer.parseInt(configuracionFacadeLocal.findByNombre("port").getValor()));
        es.setProtocol(configuracionFacadeLocal.findByNombre("protocol").getValor());
        es.setUsername(configuracionFacadeLocal.findByNombre("username").getValor());
        es.setPassword(configuracionFacadeLocal.findByNombre("contrasena").getValor());
        Accion enviado = accionFacadeLocal.findByNombreAccion("ENVIADO");
        Accion noenviado = accionFacadeLocal.findByNombreAccion("NO ENVIADO");
        for (Listasclientestareas lce : elemento.getListasclientestareasList()) {
            try {
                String correoString = lce.getCliente().getCorreo();
                if (correoString.equals("")) {
                    
                    lce.setIdAccion(noenviado);
                    lce.setObservaciones("Correo vacio");
                }
                es.sendEmail(correoString, elemento.getNombre(), elemento.getDescripcion(), elemento.getIdEvento() + elemento.getIdEvento().getImagen());

                lce.setIdAccion(enviado);
            } catch (MessagingException ex) {
                lce.setIdAccion(noenviado);
                    lce.setObservaciones("Correo invalido");
            } catch (IOException ex) {
                lce.setIdAccion(noenviado);
                    lce.setObservaciones("problemas externos llamas administrador");
            }
        }
        return null;
    }

    @Override
    public Usuario getUsuario(Integer idUsuario) {
        return usuarioFacadeLocal.find(idUsuario);
    }
}
