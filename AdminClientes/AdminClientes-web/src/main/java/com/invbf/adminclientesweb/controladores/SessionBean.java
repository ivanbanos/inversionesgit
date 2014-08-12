/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.controladores;

import com.invbf.adminclientesapi.entity.Formularios;
import com.invbf.adminclientesapi.entity.Usuarios;
import com.invbf.adminclientesapi.entity.Vistas;
import com.invbf.adminclientesapi.exceptions.ClavesNoConcuerdanException;
import com.invbf.adminclientesapi.exceptions.UsuarioNoConectadoException;
import com.invbf.adminclientesapi.exceptions.UsuarioNoExisteException;
import com.invbf.adminclientesapi.facade.SystemFacade;
import com.invbf.adminclientesweb.util.FacesUtil;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
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
    private HashMap<String, Object> Attributes;
    /**
     * Creates a new instance of SessionFlowumiUtil
     */
    public SessionBean() {
    }

    @PostConstruct
    public void init() {
        usuario = new Usuarios();
        Attributes = new HashMap<String, Object> ();
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public String Conectar() {
        try {
            LOGGER.info("Conectando...");
            usuario = sessionFacade.iniciarSession(usuario);
            LOGGER.info("Conectado.");
            if (usuario.getIdPerfil().getNombre().equals("Administrador")) {
                LOGGER.info("Administrador - cargando vista principal admin");
                return "/pages/InicioAdministrador.xhtml";
            } else if(usuario.getIdPerfil().getNombre().equals("Marketing")){
                LOGGER.info("Marketing - cargando vista principal admin");
                return "/pages/InicioMarketing.xhtml";
            }else if(usuario.getIdPerfil().getNombre().equals("Hostess")){
                LOGGER.info("Hostess - cargando vista principal admin");
                return "/pages/InicioHostess.xhtml";
            }else if(usuario.getIdPerfil().getNombre().equals("Gerente")){
                LOGGER.info("Gerente - cargando vista principal admin");
                return "/pages/InicioGerente.xhtml";
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
    
    public boolean perfilViewMatch(String vista){
        List<Vistas> vistasUsuario = usuario.getIdPerfil().getVistasList();
        for(Vistas v : vistasUsuario){
            if(v.getNombreVista().equals(vista)){
                return true;
            }
        }
        return false;
    }
    
    public boolean perfilFormMatch(String tabla, String accion){
        for(Formularios f : usuario.getIdPerfil().getFormulariosList()){
            if(f.es(tabla+accion)){
                return true;
            }
        }
        return false;
    }

    public void actualizarUsuario(){
        usuario = sessionFacade.actualizarUsuario(usuario);
    }
    
    public HashMap<String, Object>  getAttributes() {
        return Attributes;
    }

    public void setAttributes(HashMap<String, Object>  Attributes) {
        this.Attributes = Attributes;
    }
    
}
