/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.controladores;

import com.invbf.adminclientesapi.entity.Casinos;
import com.invbf.adminclientesapi.entity.Eventos;
import com.invbf.adminclientesapi.facade.MarketingUserFacade;
import com.invbf.adminclientesapi.facade.SystemFacade;
import com.invbf.adminclientesweb.util.FacesUtil;
import java.io.FileNotFoundException;
import java.io.IOException;
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
public class CrudEventoBean {

    private static final Logger LOGGER =
            Logger.getLogger(SessionBean.class);
    @EJB
    MarketingUserFacade marketingUserFacade;
    @EJB
    SystemFacade systemFacade;
    private List<Eventos> lista;
    private Eventos elemento;
    private List<Casinos> listacasinos;
    private UploadedFile file;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private boolean editar;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }
    private List<Eventos> flista;

    public List<Eventos> getFlista() {
        return flista;
    }

    public void setFlista(List<Eventos> flista) {
        this.flista = flista;
    }

    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public CrudEventoBean() {
    }

    @PostConstruct
    public void init() {

        if (!sessionBean.perfilViewMatch("Eventos")) {
            try {
                sessionBean.Desconectar();
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
                LOGGER.error(ex);
            }
        }
        elemento = new Eventos();
        lista = marketingUserFacade.findAllEventos();
        listacasinos = marketingUserFacade.findAllCasinos();
        editar = false;
    }

    public List<Eventos> getLista() {
        return lista;
    }

    public void setLista(List<Eventos> lista) {
        this.lista = lista;
    }

    public Eventos getElemento() {
        return elemento;
    }

    public void setElemento(Eventos elemento) {
        this.elemento = elemento;
    }

    public List<Casinos> getListacasinos() {
        return listacasinos;
    }

    public void setListacasinos(List<Casinos> listacasinos) {
        this.listacasinos = listacasinos;
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
        marketingUserFacade.deleteEventos(elemento);
        lista = marketingUserFacade.findAllEventos();
        sessionBean.registrarlog("eliminar", "Eventos", elemento.getNombre());
        FacesUtil.addInfoMessage("Evento eliminado", elemento.getNombre());
        elemento = new Eventos();
    }

    public void handleFileUpload(FileUploadEvent event) {
        file = event.getFile();
        FacesUtil.addInfoMessage(event.getFile().getFileName());
    }

    public void guardar() {
        elemento = marketingUserFacade.guardarEventos(elemento);
        lista = marketingUserFacade.findAllEventos();
        sessionBean.registrarlog("actualizar", "Eventos", elemento.getNombre());
        FacesUtil.addInfoMessage("Evento actualizado", elemento.getNombre());
        if (file != null) {
                elemento.setImagen(file.getContents());
                elemento.setMime(file.getContentType());
                elemento.setFormato(file.getFileName().substring(file.getFileName().lastIndexOf("."), file.getFileName().length()));
            
        } 
        marketingUserFacade.guardarEventos(elemento);
        elemento = new Eventos();
    }

    public void goEventoMarketing(int id) {
        try {
            sessionBean.getAttributes().put("idEvento", new Integer(id));
            FacesContext.getCurrentInstance().getExternalContext().redirect("MarketingEventoManejadorView.xhtml");
        } catch (IOException ex) {
            LOGGER.error(ex);
        }
    }

    public void goEventoHostess(int id) {
        try {
            sessionBean.getAttributes().put("idEvento", new Integer(id));
            FacesContext.getCurrentInstance().getExternalContext().redirect("HostessEventoManejadorView.xhtml");
        } catch (IOException ex) {
            LOGGER.error(ex);
        }
    }

    public boolean isEditar() {
        return editar;
    }

    public void setEditar(boolean editar) {
        this.editar = editar;
    }
}
