/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.controladores;

import com.invbf.sistemagestionclientes.entity.Casino;
import com.invbf.sistemagestionclientes.entity.Evento;
import com.invbf.sistemagestionclientes.entity.Tarea;
import com.invbf.sistemagestionclientes.facade.AdminFacade;
import com.invbf.sistemagestionclientes.facade.MarketingUserFacade;
import com.invbf.sistemagestionclientes.facade.SystemFacade;
import com.invbf.sistemagestionclientes.facade.impl.AdminFacadeImpl;
import com.invbf.sistemagestionclientes.facade.impl.MarketingUserFacadeImpl;
import com.invbf.sistemagestionclientes.facade.impl.SystemFacadeImpl;
import com.invbf.sistemagestionclientes.util.FacesUtil;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author ideacentre
 */
@ManagedBean
@ViewScoped
public class MarketingEventoManejadorBean {
    private Evento elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private UploadedFile file;
    private List<Casino> listacasinos;
    private Tarea tarea;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public MarketingEventoManejadorBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("eventos");
        if (!sessionBean.perfilViewMatch("ManejadorEventosMarketing")
                && !sessionBean.perfilFormMatch("Eventos", "crear")
                && !sessionBean.perfilFormMatch("Eventos", "actualizar")) {
            try {
                sessionBean.Desconectar();
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        }

        if (sessionBean.getAttributes() == null || !sessionBean.getAttributes().containsKey("idEvento")) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("eventos.xhtml");
            } catch (IOException ex) {
            }
        }
        if ((Integer) sessionBean.getAttributes().get("idEvento") != 0) {
            elemento = sessionBean.marketingUserFacade.findEvento((Integer) sessionBean.getAttributes().get("idEvento"));
        } else {
            elemento = new Evento();
            elemento.setIdCasino(new Casino(0));
        }
        listacasinos = sessionBean.marketingUserFacade.findAllCasinos();
    }

    public Evento getElemento() {
        return elemento;
    }

    public void setElemento(Evento elemento) {
        this.elemento = elemento;
    }

    public String guardar() {
        guardar:
        {
            Calendar fechainicio = Calendar.getInstance();
            Calendar fechafinal = Calendar.getInstance();
            Calendar nowDate = Calendar.getInstance();
            fechainicio.setTime(elemento.getFechaInicio());
            fechafinal.setTime(elemento.getFechaFinal());
            if (elemento.getIdEvento() == null || elemento.getIdEvento() == 0) {
                if (fechainicio.before(nowDate)) {
                    FacesUtil.addErrorMessage("Fehas incorrectas", "Fecha inicial antes de la fecha actual");
                    break guardar;
                } else if (fechafinal.before(fechainicio)) {
                    FacesUtil.addErrorMessage("Fehas incorrectas", "Fecha final antes de la fecha inicial");
                    break guardar;
                }
            }
            elemento = sessionBean.marketingUserFacade.guardarEventos(elemento);
            sessionBean.registrarlog("actualizar", "Eventos", elemento.getNombre());
            if (file != null && file.getContents() != null) {
                sessionBean.marketingUserFacade.guardarImagen(file.getContents(), file.getFileName());
                elemento.setImagen(file.getFileName());
            }
            sessionBean.marketingUserFacade.guardarEventos(elemento);
            sessionBean.actualizarUsuario();
            elemento = sessionBean.marketingUserFacade.guardarEventos(elemento);
            FacesUtil.addInfoMessage("Evento guardado con exito", elemento.getNombre());

            return "/pages/eventos.xhtml";
        }
        return "";
    }

    public List<Casino> getListacasinos() {
        return listacasinos;
    }

    public void setListacasinos(List<Casino> listacasinos) {
        this.listacasinos = listacasinos;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void deleteTarea() {
        elemento.getTareasList().remove(tarea);
        sessionBean.marketingUserFacade.deleteTarea(tarea);
        sessionBean.registrarlog("eliminar", "Tareas", elemento.toString());
        FacesUtil.addInfoMessage("Tarea eliminada", tarea.getNombre());
        tarea = new Tarea();
    }

    public Tarea getTarea() {
        return tarea;
    }

    public void setTarea(Tarea tarea) {
        this.tarea = tarea;
    }

    public void handleFileUpload(FileUploadEvent event) {
        if (event != null) {
            file = event.getFile();
            if (elemento.getIdEvento() != null) {
                sessionBean.marketingUserFacade.guardarImagen(file.getContents(), file.getFileName());
                elemento.setImagen(file.getFileName());
            }
        }
    }

    public void goTareaMarketing(Integer idTarea) {
        try {
            sessionBean.getAttributes().put("idTarea", idTarea);
            
            sessionBean.getAttributes().put("from", "evento");
            FacesContext.getCurrentInstance().getExternalContext().redirect("tareaAccion.xhtml");
        } catch (IOException ex) {
        }
    }
}
