/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.controladores;

import com.invbf.adminclientesapi.entity.Casino;
import com.invbf.adminclientesapi.entity.Evento;
import com.invbf.adminclientesapi.entity.Tarea;
import com.invbf.adminclientesapi.facade.AdminFacade;
import com.invbf.adminclientesapi.facade.MarketingUserFacade;
import com.invbf.adminclientesapi.facade.SystemFacade;
import com.invbf.adminclientesweb.util.FacesUtil;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author ideacentre
 */
@ManagedBean
@ViewScoped
public class MarketingEventoManejadorBean {

    private static final Logger LOGGER = Logger.getLogger(SessionBean.class);
    @EJB
    MarketingUserFacade marketingUserFacade;
    @EJB
    SystemFacade systemFacade;
    @EJB
    AdminFacade adminFacade;
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
                LOGGER.error(ex);
            }
        }

        if (sessionBean.getAttributes() == null || !sessionBean.getAttributes().containsKey("idEvento")) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("eventos.xhtml");
            } catch (IOException ex) {
                LOGGER.error(ex);
            }
        }
        if ((Integer) sessionBean.getAttributes().get("idEvento") != 0) {
            elemento = marketingUserFacade.findEvento((Integer) sessionBean.getAttributes().get("idEvento"));
        } else {
            elemento = new Evento();
            elemento.setIdCasino(new Casino(0));
        }
        listacasinos = marketingUserFacade.findAllCasinos();
    }

    public Evento getElemento() {
        return elemento;
    }

    public void setElemento(Evento elemento) {
        this.elemento = elemento;
    }

    public MarketingUserFacade getMarketingUserFacade() {
        return marketingUserFacade;
    }

    public void setMarketingUserFacade(MarketingUserFacade marketingUserFacade) {
        this.marketingUserFacade = marketingUserFacade;
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
            elemento = marketingUserFacade.guardarEventos(elemento);
            sessionBean.registrarlog("actualizar", "Eventos", elemento.getNombre());
            if (file != null && file.getContents() != null) {
                marketingUserFacade.guardarImagen(file.getContents(),
                        elemento.getIdEvento(),
                        file.getFileName().substring(file.getFileName().lastIndexOf("."),
                        file.getFileName().length()));
                elemento.setImagen(elemento.getIdEvento() + file.getFileName().substring(file.getFileName().lastIndexOf("."),
                        file.getFileName().length()));
            }
            marketingUserFacade.guardarEventos(elemento);
            sessionBean.actualizarUsuario();
            elemento = marketingUserFacade.guardarEventos(elemento);
            FacesUtil.addInfoMessage("Evento guardado con exito", elemento.getNombre());

            return "/pages/eventos.xhtml";
        }
        return "";
    }

    public AdminFacade getAdminFacade() {
        return adminFacade;
    }

    public void setAdminFacade(AdminFacade adminFacade) {
        this.adminFacade = adminFacade;
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
        marketingUserFacade.deleteTarea(tarea);
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
                marketingUserFacade.guardarImagen(file.getContents(),
                        elemento.getIdEvento(),
                        file.getFileName().substring(file.getFileName().lastIndexOf("."),
                        file.getFileName().length()));
                elemento.setImagen(elemento.getIdEvento() + file.getFileName().substring(file.getFileName().lastIndexOf("."),
                        file.getFileName().length()));
            }
            FacesUtil.addInfoMessage(event.getFile().getFileName());
        }
    }

    public void goTareaMarketing(Integer idTarea) {
        try {
            sessionBean.getAttributes().put("idTarea", idTarea);
            
            sessionBean.getAttributes().put("from", "evento");
            FacesContext.getCurrentInstance().getExternalContext().redirect("tareaAccion.xhtml");
        } catch (IOException ex) {
            LOGGER.error(ex);
        }
    }
}
