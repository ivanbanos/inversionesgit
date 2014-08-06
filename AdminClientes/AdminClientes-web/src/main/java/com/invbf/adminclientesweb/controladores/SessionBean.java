/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.controladores;

import com.invbf.adminclientesapi.Usuarios;
import com.invbf.adminclientesapi.exceptions.ClavesNoConcuerdanException;
import com.invbf.adminclientesapi.exceptions.UsuarioNoConectadoException;
import com.invbf.adminclientesapi.exceptions.UsuarioNoExisteException;
import com.invbf.adminclientesapi.facade.SystemFacade;
import com.invbf.adminclientesweb.util.FacesUtil;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.apache.log4j.Logger;

/**
 *
 * @author ideacentre
 */
@Named(value = "sessionBean")
@SessionScoped
public class SessionBean implements Serializable {

    private static final Logger LOGGER =
            Logger.getLogger(SessionBean.class);
    @EJB
    SystemFacade sessionFacade;
    private Usuarios usuario;//Almacena el objeto usuario de la session

    /**
     * Creates a new instance of SessionFlowumiUtil
     */
    public SessionBean() {
    }

    @PostConstruct
    public void init() {
        usuario = new Usuarios();
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public String Conectar() {
        try {
            LOGGER.info("Conectando");
            usuario = sessionFacade.iniciarSession(usuario);
            LOGGER.info("Conectado");
            if (usuario.getIdPerfil().getNombre().equals("Administrador")) {
                LOGGER.info("Administrador - cargando vista principal admin");
                return "/pages/AtributosSistemaView.xhtml";
            }
        } catch (ClavesNoConcuerdanException ex) {
            LOGGER.error(ex.getMessage());
            FacesUtil.addErrorMessage("Usuario no conectado", ex.getMessage());
            usuario = new Usuarios();
        } catch (UsuarioNoExisteException ex) {
            LOGGER.error(ex.getMessage());
            FacesUtil.addErrorMessage("Usuario no conectado", ex.getMessage());
            usuario = new Usuarios();
        } catch (UsuarioNoConectadoException ex) {
            LOGGER.error(ex.getMessage());
            FacesUtil.addErrorMessage("Usuario no conectado", ex.getMessage());
            usuario = new Usuarios();
        }
        return "";
    }

    public String Desconectar() {
        LOGGER.info("Desconectando...");
        usuario = new Usuarios();
        return "/pages/InicioSession.xhtml";
    }
}
