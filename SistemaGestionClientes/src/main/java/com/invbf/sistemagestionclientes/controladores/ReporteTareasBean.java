/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.controladores;

import com.invbf.sistemagestionclientes.entity.Tarea;
import com.invbf.sistemagestionclientes.facade.MarketingUserFacade;
import com.invbf.sistemagestionclientes.facade.SystemFacade;
import com.invbf.sistemagestionclientes.facade.impl.MarketingUserFacadeImpl;
import com.invbf.sistemagestionclientes.facade.impl.SystemFacadeImpl;
import com.invbf.sistemagestionclientes.util.FacesUtil;
import java.io.IOException;
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
public class ReporteTareasBean {
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
    public ReporteTareasBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("reportes");
        if (!sessionBean.perfilViewMatch("Reportes")) {
            try {
                sessionBean.Desconectar();
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        }
        elemento = new Tarea();
        lista = sessionBean.marketingUserFacade.findAllTareas();
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

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void delete() {
        sessionBean.marketingUserFacade.deleteTarea(elemento);
        sessionBean.registrarlog("eliminar", "Tareas", elemento.getNombre());
        FacesUtil.addInfoMessage("Tarea eliminada", elemento.getNombre());
        elemento = new Tarea();
        lista = sessionBean.marketingUserFacade.findAllTareas();
    }

    public void handleFileUpload(FileUploadEvent event) {
        if (event != null) {
            file = event.getFile();
            FacesUtil.addInfoMessage(event.getFile().getFileName());
        }
    }

    public Tarea getElemento() {
        return elemento;
    }

    public void setElemento(Tarea elemento) {
        this.elemento = elemento;
    }

    public void goTareaReporte(int id) {
        try {
            sessionBean.getAttributes().put("idTarea", id);
            FacesContext.getCurrentInstance().getExternalContext().redirect("ReporteTareaEspesifica.xhtml");
        } catch (IOException ex) {
        }
    }
}
