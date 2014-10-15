/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.controladores;

import com.invbf.sistemagestionclientes.entity.Casino;
import com.invbf.sistemagestionclientes.entity.Evento;
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
public class ReportesEventosBean {
    private List<Evento> lista;
    private Evento elemento;
    private List<Casino> listacasinos;
    private UploadedFile file;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private boolean editar;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }
    private List<Evento> flista;

    public List<Evento> getFlista() {
        return flista;
    }

    public void setFlista(List<Evento> flista) {
        this.flista = flista;
    }

    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public ReportesEventosBean() {
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
        elemento = new Evento();
        lista = sessionBean.marketingUserFacade.findAllEventos();
        listacasinos = sessionBean.marketingUserFacade.findAllCasinos();
        editar = false;
    }

    public List<Evento> getLista() {
        return lista;
    }

    public void setLista(List<Evento> lista) {
        this.lista = lista;
    }

    public Evento getElemento() {
        return elemento;
    }

    public void setElemento(Evento elemento) {
        this.elemento = elemento;
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

    public void delete() {
        sessionBean.marketingUserFacade.deleteEventos(elemento);
        lista = sessionBean.marketingUserFacade.findAllEventos();
        sessionBean.registrarlog("eliminar", "Eventos", elemento.getNombre());
        FacesUtil.addInfoMessage("Evento eliminado", elemento.getNombre());
        elemento = new Evento();
    }

    public void handleFileUpload(FileUploadEvent event) {
        file = event.getFile();
        FacesUtil.addInfoMessage(event.getFile().getFileName());
    }

    

    public void goEventoMarketing(int id) {
        try {
            sessionBean.getAttributes().put("idEvento", new Integer(id));
            FacesContext.getCurrentInstance().getExternalContext().redirect("MarketingEventoManejadorView.xhtml");
        } catch (IOException ex) {
        }
    }

    public void goEventoHostess(int id) {
        try {
            sessionBean.getAttributes().put("idEvento", new Integer(id));
            FacesContext.getCurrentInstance().getExternalContext().redirect("HostessEventoManejadorView.xhtml");
        } catch (IOException ex) {
        }
    }

    public void goEventoReporte(int id) {
        try {
            sessionBean.getAttributes().put("idEvento", new Integer(id));
            FacesContext.getCurrentInstance().getExternalContext().redirect("ReporteEventoEspesifico.xhtml");
        } catch (IOException ex) {
        }
    }

    public boolean isEditar() {
        return editar;
    }

    public void setEditar(boolean editar) {
        this.editar = editar;
    }
}
