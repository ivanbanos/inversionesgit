/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.controladores;

import com.invbf.adminclientesapi.entity.Evento;
import com.invbf.adminclientesapi.entity.Listasclientestareas;
import com.invbf.adminclientesapi.exceptions.EventoSinClientesPorRevisarException;
import com.invbf.adminclientesapi.facade.AdminFacade;
import com.invbf.adminclientesapi.facade.HostessFacade;
import com.invbf.adminclientesapi.facade.MarketingUserFacade;
import com.invbf.adminclientesweb.util.FacesUtil;
import java.io.IOException;
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
public class HostessTareaManejadorBean {

    private static final Logger LOGGER =
            Logger.getLogger(SessionBean.class);
    @EJB
    MarketingUserFacade marketingUserFacade;
    @EJB
    AdminFacade adminFacade;
    @EJB
    HostessFacade hostessFacade;
    private Evento elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private StreamedContent file;
    private List<Listasclientestareas> clientes;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public HostessTareaManejadorBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("eventoshostess");
        if (!sessionBean.perfilViewMatch("ManejadorEventosHostess")) {
            try {
                sessionBean.Desconectar();
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
                LOGGER.error(ex);
            }
        }

        if (sessionBean.getAttributes() == null || !sessionBean.getAttributes().containsKey("idTarea")) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("tareasHostess.xhtml");
            } catch (IOException ex) {
                LOGGER.error(ex);
            }
        }
        elemento = marketingUserFacade.findEvento((Integer) sessionBean.getAttributes().get("idTarea"));
        file = new DefaultStreamedContent();
        clientes = hostessFacade.findClienteTareaHostess((Integer) sessionBean.getAttributes().get("idTarea"));
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

    public void guardar(Integer idCliente) {
        int index= clientes.indexOf(new Listasclientestareas((Integer) sessionBean.getAttributes().get("idTarea"), idCliente));
        Listasclientestareas l = clientes.remove(index);
        l.setUsuario(sessionBean.getUsuario());
        hostessFacade.guardarLCE(l);
        try {
            Listasclientestareas nuevo = hostessFacade.nuevoLCE((Integer) sessionBean.getAttributes().get("idTarea"), clientes, l);
            clientes.add(nuevo);
        } catch (EventoSinClientesPorRevisarException ex) {
            LOGGER.info(ex);
            FacesUtil.addInfoMessage("No hay mas clientes por revisar", "");
        }
    }

    public void nuevo(Integer idCliente) {
       int index= clientes.indexOf(new Listasclientestareas((Integer) sessionBean.getAttributes().get("idTarea"), idCliente));
        Listasclientestareas l = clientes.remove(index);
        l.setUsuario(sessionBean.getUsuario());
        try {
            Listasclientestareas nuevo = hostessFacade.nuevoLCE((Integer) sessionBean.getAttributes().get("idTarea"), clientes, l);
            clientes.add(nuevo);
        } catch (EventoSinClientesPorRevisarException ex) {
            LOGGER.info(ex);
            FacesUtil.addInfoMessage("No hay mas clientes por revisar", "");
        }
    }
}
