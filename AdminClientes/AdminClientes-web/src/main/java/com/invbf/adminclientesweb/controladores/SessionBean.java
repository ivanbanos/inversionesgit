/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.controladores;

import com.invbf.adminclientesapi.entity.Configuraciones;
import com.invbf.adminclientesapi.entity.Formularios;
import com.invbf.adminclientesapi.entity.Usuarios;
import com.invbf.adminclientesapi.entity.Vistas;
import com.invbf.adminclientesapi.exceptions.ClavesNoConcuerdanException;
import com.invbf.adminclientesapi.exceptions.UsuarioNoConectadoException;
import com.invbf.adminclientesapi.exceptions.UsuarioNoExisteException;
import com.invbf.adminclientesapi.facade.SystemFacade;
import com.invbf.adminclientesweb.interfaces.Observer;
import com.invbf.adminclientesweb.interfaces.Subject;
import com.invbf.adminclientesweb.util.FacesUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.apache.log4j.Logger;

/**
 *
 * @author ideacentre
 */
@Named(value = "sessionBean")
@SessionScoped
public class SessionBean implements Serializable, Subject {

    private static final Logger LOGGER =
            Logger.getLogger(SessionBean.class);
    @EJB
    SystemFacade sessionFacade;
    private Usuarios usuario;//Almacena el objeto usuario de la session
    private HashMap<String, Object> Attributes;
    private List<Observer> observers;
    private int paginacion;

    /**
     * Creates a new instance of SessionFlowumiUtil
     */
    public SessionBean() {
    }

    @PostConstruct
    public void init() {
        usuario = new Usuarios();
        Attributes = new HashMap<String, Object>();
        observers = new ArrayList<Observer>();
        Configuraciones configuracion = sessionFacade.getConfiguracionByName("paginacion");
        if (configuracion == null) {
            paginacion = 10;
        } else {
            paginacion = Integer.parseInt(configuracion.getValor());
        }
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
            return "/pages/index.xhtml";
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

    public boolean perfilViewMatch(String vista) {
        List<Vistas> vistasUsuario = usuario.getIdPerfil().getVistasList();
        for (Vistas v : vistasUsuario) {
            if (v.getNombreVista().equals(vista)) {
                return true;
            }
        }
        return false;
    }

    public boolean perfilFormMatch(String tabla, String accion) {
        for (Formularios f : usuario.getIdPerfil().getFormulariosList()) {
            if (f.es(tabla + accion)) {
                return true;
            }
        }
        return false;
    }

    public void actualizarUsuario() {
        usuario = sessionFacade.actualizarUsuario(usuario);
    }

    public HashMap<String, Object> getAttributes() {
        return Attributes;
    }

    public void setAttributes(HashMap<String, Object> Attributes) {
        this.Attributes = Attributes;
    }

    public void goInicio() {
        try {
            if (usuario.getIdPerfil().getNombre().equals("Administrador")) {

                LOGGER.info("Administrador - cargando vista principal admin");
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioAdministrador.xhtml");
            } else if (usuario.getIdPerfil().getNombre().equals("Marketing")) {
                LOGGER.info("Marketing - cargando vista principal admin");
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioMarketing.xhtml");
            } else if (usuario.getIdPerfil().getNombre().equals("Hostess")) {
                LOGGER.info("Hostess - cargando vista principal admin");
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioHostess.xhtml");
            } else if (usuario.getIdPerfil().getNombre().equals("Gerente")) {
                LOGGER.info("Gerente - cargando vista principal admin");
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioGerente.xhtml");
            }
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void notifyObserver(String tabla) {
        Iterator<Observer> i = observers.iterator();
        while (i.hasNext()) {
            Observer o = i.next();
            if (o == null) {
                i.remove();
            } else {
                if (tabla.equals("Perfiles") && o instanceof CrudUsuariosBean) {
                    o.update();
                }
            }

        }
    }

    public void registrarlog(String accion, String tabla, String mensaje) {
        sessionFacade.registrarlog(accion, tabla, mensaje, usuario);
    }

    public int getPaginacion() {
        return paginacion;
    }

    public void setPaginacion(int paginacion) {
        this.paginacion = paginacion;
    }
    
}
