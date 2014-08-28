/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.controladores;

import com.invbf.adminclientesapi.entity.Eventos;
import com.invbf.adminclientesapi.facade.HostessFacade;
import java.io.IOException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;

/**
 *
 * @author ideacentre
 */
@ManagedBean
@ViewScoped
public class EventosHostessBean {

    private static final Logger LOGGER =
            Logger.getLogger(SessionBean.class);
    @EJB
    HostessFacade hostessFacade;
    private List<Eventos> lista;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;

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
    public EventosHostessBean() {
    }

    @PostConstruct
    public void init() {
        if (!sessionBean.perfilViewMatch("ManejadorEventosHostess")) {
            try {
                sessionBean.Desconectar();
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
                LOGGER.error(ex);
            }
        }
        lista = hostessFacade.findEventosHostess(sessionBean.getUsuario());
    }

    public List<Eventos> getLista() {
        return lista;
    }

    public void setLista(List<Eventos> lista) {
        this.lista = lista;
    }

    public HostessFacade getHostessFacade() {
        return hostessFacade;
    }

    public void setHostessFacade(HostessFacade hostessFacade) {
        this.hostessFacade = hostessFacade;
    }
    
    public void goEvento(int id) {
        try {
            sessionBean.getAttributes().put("idEvento", new Integer(id));
            FacesContext.getCurrentInstance().getExternalContext().redirect("HostessEventoManejadorView.xhtml");
        } catch (IOException ex) {
                LOGGER.error(ex);
        }
    }
}