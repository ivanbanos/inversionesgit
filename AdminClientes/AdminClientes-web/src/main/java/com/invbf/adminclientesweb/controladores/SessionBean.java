/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.controladores;

import com.invbf.adminclientesapi.entity.Configuracion;
import com.invbf.adminclientesapi.entity.Formulario;
import com.invbf.adminclientesapi.entity.Tarea;
import com.invbf.adminclientesapi.entity.Usuario;
import com.invbf.adminclientesapi.entity.Vista;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
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
    private Usuario usuario;//Almacena el objeto usuario de la session
    private HashMap<String, Object> Attributes;
    private List<Observer> observers;
    private int paginacion;
    private String active;

    /**
     * Creates a new instance of SessionFlowumiUtil
     */
    public SessionBean() {
    }

    @PostConstruct
    public void init() {
        usuario = new Usuario();
        Attributes = new HashMap<String, Object>();
        observers = new ArrayList<Observer>();
        Configuracion configuracion = sessionFacade.getConfiguracionByName("paginacion");
        if (configuracion == null) {
            paginacion = 10;
        } else {
            paginacion = Integer.parseInt(configuracion.getValor());
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String Conectar() {
        try {
            LOGGER.info("Conectando...");
            usuario = sessionFacade.iniciarSession(usuario);
            LOGGER.info("Conectado.");
            active = "inicio";
            return "/pages/index.xhtml";
        } catch (ClavesNoConcuerdanException ex) {
            LOGGER.error(ex.getMessage());
            FacesUtil.addErrorMessage("Usuario no conectado", ex.getMessage());
            usuario = new Usuario();
        } catch (UsuarioNoExisteException ex) {
            LOGGER.error(ex.getMessage());
            FacesUtil.addErrorMessage("Usuario no conectado", ex.getMessage());
            usuario = new Usuario();
        } catch (UsuarioNoConectadoException ex) {
            LOGGER.error(ex.getMessage());
            FacesUtil.addErrorMessage("Usuario no conectado", ex.getMessage());
            usuario = new Usuario();
        }
        return "";
    }

    public String Desconectar() {
        LOGGER.info("Desconectando...");
        usuario = new Usuario();
        return "/pages/InicioSession.xhtml";
    }

    public boolean perfilViewMatch(String vista) {
        if (usuario == null || usuario.getIdPerfil() == null || usuario.getIdPerfil().getVistasList() == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        } else {
            List<Vista> vistasUsuario = usuario.getIdPerfil().getVistasList();
            for (Vista v : vistasUsuario) {
                if (v.getNombreVista().equals(vista)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean perfilFormMatch(String tabla, String accion) {
        if (usuario == null || usuario.getIdPerfil() == null || usuario.getIdPerfil().getFormulariosList() == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        } else {
            for (Formulario f : usuario.getIdPerfil().getFormulariosList()) {
                if (f.es(tabla + accion)) {
                    return true;
                }
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

    public void checkUsuarioConectado() {
        if (usuario == null
                || usuario.getIdUsuario() == null
                || usuario.getIdUsuario() <= 0) {
            try {
                System.out.println("No lo coje");
                Desconectar();
                FacesUtil.addErrorMessage("Session finalizada", "No existe usuario conectado");
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
                LOGGER.error(ex);
            }
        }
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public boolean isActive(String pestaña) {
        if (active == null) {
            return false;
        }
        return active.equals(pestaña);
    }

    public boolean isNotActive(String pestaña) {
        if (active == null) {
            return true;
        }
        return !active.equals(pestaña);
    }

    public String go(String page) {
        if (page.equals("inicio")) {
            active = "inicio";
            return "/pages/index.xhtml";
        } else if (page.equals("AtributosSistema")) {
            active = "configuracion";
            return "/pages/AdministradorAtributosSistema.xhtml";
        } else if (page.equals("AtributosMarketing")) {
            active = "configuracion";
            return "/pages/AdministradorAtributosMarketing.xhtml";
        } else if (page.equals("ConfiguracionesGenerales")) {
            active = "configuracion";
            return "/pages/ConfiguracionesGenerales.xhtml";
        } else if (page.equals("clientes")) {
            active = "clientes";
            return "/pages/clientes.xhtml";
        } else if (page.equals("eventos")) {
            active = "eventos";
            return "/pages/eventos.xhtml";
        } else if (page.equals("eventoshostess")) {
            active = "eventoshostess";
            return "/pages/tareasHostess.xhtml";
        } else if (page.equals("reportes")) {
            active = "reportes";
            return "/pages/Reportes.xhtml";
        } else if (page.equals("cuenta")) {
            active = "cuenta";
            return "/pages/CuentaUsuarios.xhtml";
        } else if (page.equals("tareas")) {
            active = "tareas";
            return "/pages/tareas.xhtml";
        }
        return "/pages/InicioSession.xhtml";
    }

    public void checkEstadoTarea(Tarea tarea) {
        Calendar fechainicio = Calendar.getInstance();
        Calendar fechafinal = Calendar.getInstance();
        Calendar nowDate = Calendar.getInstance();
        fechainicio.setTime(tarea.getFechaInicio());
        fechafinal.setTime(tarea.getFechaFinalizacion());
        tarea.setEstado("POR INICIAR");
        if (fechainicio.before(nowDate)) {
            tarea.setEstado("ACTIVO");
        }
        if (fechafinal.before(nowDate)) {
            tarea.setEstado("VENCIDO");
        }
    }

    void obtenerUsuario(Integer idUsuario) {
        usuario = sessionFacade.getUsuario(idUsuario);
    }
}
