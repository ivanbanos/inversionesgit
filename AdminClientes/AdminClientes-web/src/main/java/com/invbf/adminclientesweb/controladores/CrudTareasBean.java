/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.controladores;

import com.invbf.adminclientesapi.entity.Casino;
import com.invbf.adminclientesapi.entity.Evento;
import com.invbf.adminclientesapi.entity.Tarea;
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
public class CrudTareasBean {

    private static final Logger LOGGER = Logger.getLogger(SessionBean.class);
    @EJB
    MarketingUserFacade marketingUserFacade;
    @EJB
    SystemFacade systemFacade;
    private List<Tarea> lista;
    private Tarea elemento;
    private UploadedFile file;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }
    private List<Tarea> flista;

    public List<Tarea> getFlista() {
        return flista;
    }

    public void setFlista(List<Tarea> flista) {
        this.flista = flista;
    }

    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public CrudTareasBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("tareas");
        if (!sessionBean.perfilViewMatch("Tareas")) {
            try {
                sessionBean.Desconectar();
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
                LOGGER.error(ex);
            }
        }
        elemento = new Tarea();
        lista = marketingUserFacade.findAllTareas();
        for(Tarea t :lista){
            if(!t.getEstado().equals("VENCIDO")) {
                sessionBean.checkEstadoTarea(t);
            }
        }
    }

    public List<Tarea> getLista() {
        return lista;
    }

    public void setLista(List<Tarea> lista) {
        this.lista = lista;
    }

    public MarketingUserFacade getMarketingUserFacade() {
        return marketingUserFacade;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void setMarketingUserFacade(MarketingUserFacade marketingUserFacade) {
        this.marketingUserFacade = marketingUserFacade;
    }

    public void delete() {
        marketingUserFacade.deleteTarea(elemento);
        sessionBean.registrarlog("eliminar", "Tareas", elemento.getNombre());
        FacesUtil.addInfoMessage("Tarea eliminada", elemento.getNombre());
        elemento = new Tarea();
        lista = marketingUserFacade.findAllTareas();
    }

    public void handleFileUpload(FileUploadEvent event) {
        if (event != null) {
            file = event.getFile();
            FacesUtil.addInfoMessage(event.getFile().getFileName());
        }
    }

    public void goTareaMarketing(int id) {
        try {
            sessionBean.getAttributes().put("idTarea", id);
            sessionBean.getAttributes().remove("idEvento");
            FacesContext.getCurrentInstance().getExternalContext().redirect("tareaAccion.xhtml");
        } catch (IOException ex) {
            LOGGER.error(ex);
        }
    }

    public void goTareaReporte(int id) {
        try {
            sessionBean.getAttributes().put("idTarea", id);
            FacesContext.getCurrentInstance().getExternalContext().redirect("tareaAccion.xhtml");
        } catch (IOException ex) {
            LOGGER.error(ex);
        }
    }

    public Tarea getElemento() {
        return elemento;
    }

    public void setElemento(Tarea elemento) {
        this.elemento = elemento;
    }

}
