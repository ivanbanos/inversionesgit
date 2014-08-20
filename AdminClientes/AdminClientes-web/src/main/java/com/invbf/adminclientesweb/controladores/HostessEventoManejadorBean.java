/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.controladores;

import com.invbf.adminclientesapi.entity.Eventos;
import com.invbf.adminclientesapi.entity.Listasclientesevento;
import com.invbf.adminclientesapi.exceptions.EventoSinClientesException;
import com.invbf.adminclientesapi.facade.AdminFacade;
import com.invbf.adminclientesapi.facade.HostessFacade;
import com.invbf.adminclientesapi.facade.MarketingUserFacade;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author ideacentre
 */
@ManagedBean
@ViewScoped
public class HostessEventoManejadorBean {

    private static final Logger LOGGER =
            Logger.getLogger(SessionBean.class);
    @EJB
    MarketingUserFacade marketingUserFacade;
    @EJB
    AdminFacade adminFacade;
    @EJB
    HostessFacade hostessFacade;
    private Eventos elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private StreamedContent file;
    private List<Listasclientesevento> clientes;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public HostessEventoManejadorBean() {
    }

    @PostConstruct
    public void init() {
        if (!sessionBean.perfilViewMatch("HostessEventoManejadorView")) {
            try {
                sessionBean.Desconectar();
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
                LOGGER.error(ex);
            }
        }

        if (sessionBean.getAttributes() == null || !sessionBean.getAttributes().containsKey("idEvento")) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioHostess.xhtml");
            } catch (IOException ex) {
                LOGGER.error(ex);
            }
        }
        elemento = marketingUserFacade.findEvento((Integer) sessionBean.getAttributes().get("idEvento"));
        file = new DefaultStreamedContent();
        if (elemento.getImagen() != null) {
            file = new DefaultStreamedContent(new ByteArrayInputStream(elemento.getImagen()), "image/" + elemento.getFormatoImagen());
        }
        clientes = new ArrayList<Listasclientesevento>(0);
        int cantidadClientes = hostessFacade.findCantidadClientes();
        for (int i = 0; i<cantidadClientes; i++) {
            try {
                clientes.add(hostessFacade.findClienteEventosHostess((Integer) sessionBean.getAttributes().get("idEvento")));
            } catch (EventoSinClientesException ex) {
                LOGGER.info(ex);
                break;
            }
        }
    }

    public Eventos getElemento() {
        return elemento;
    }

    public void setElemento(Eventos elemento) {
        this.elemento = elemento;
    }

    public MarketingUserFacade getMarketingUserFacade() {
        return marketingUserFacade;
    }

    public void setMarketingUserFacade(MarketingUserFacade marketingUserFacade) {
        this.marketingUserFacade = marketingUserFacade;
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public AdminFacade getAdminFacade() {
        return adminFacade;
    }

    public void setAdminFacade(AdminFacade adminFacade) {
        this.adminFacade = adminFacade;
    }

    public HostessFacade getHostessFacade() {
        return hostessFacade;
    }

    public void setHostessFacade(HostessFacade hostessFacade) {
        this.hostessFacade = hostessFacade;
    }

    public List<Listasclientesevento> getClientes() {
        return clientes;
    }

    public void setClientes(List<Listasclientesevento> clientes) {
        this.clientes = clientes;
    }

}
