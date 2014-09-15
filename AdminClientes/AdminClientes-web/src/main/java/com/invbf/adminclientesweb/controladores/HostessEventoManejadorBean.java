/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.controladores;

import com.invbf.adminclientesapi.entity.Clienteevento;
import com.invbf.adminclientesapi.entity.Estadocliente;
import com.invbf.adminclientesapi.entity.Evento;
import com.invbf.adminclientesapi.exceptions.EventoSinClientesPorRevisarException;
import com.invbf.adminclientesapi.facade.AdminFacade;
import com.invbf.adminclientesapi.facade.HostessFacade;
import com.invbf.adminclientesapi.facade.MarketingUserFacade;
import com.invbf.adminclientesweb.util.FacesUtil;
import com.invbf.adminclientesweb.util.ListasclienteseventoPojo;
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
    private Evento elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private StreamedContent file;
    private List<ListasclienteseventoPojo> clientes;
    private List<Estadocliente> listaestadosclientes;

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

        if (sessionBean.getAttributes() == null || !sessionBean.getAttributes().containsKey("idEvento")) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("eventos.xhtml");
            } catch (IOException ex) {
                LOGGER.error(ex);
            }
        }
        elemento = marketingUserFacade.findEvento((Integer) sessionBean.getAttributes().get("idEvento"));
        file = new DefaultStreamedContent();
        clientes = new ArrayList<ListasclienteseventoPojo>(0);
        try {
            List<Clienteevento> clientes2 = hostessFacade.findClienteEventosHostess((Integer) sessionBean.getAttributes().get("idEvento"));
            for (Clienteevento lce : clientes2) {
                clientes.add(new ListasclienteseventoPojo(lce));
            }
        } catch (EventoSinClientesPorRevisarException ex) {
            LOGGER.info(ex);
        }
        listaestadosclientes = marketingUserFacade.findAllEstadosClietes();
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

    public List<ListasclienteseventoPojo> getClientes() {
        return clientes;
    }

    public void setClientes(List<ListasclienteseventoPojo> clientes) {
        this.clientes = clientes;
    }

    public List<Estadocliente> getListaestadosclientes() {
        return listaestadosclientes;
    }

    public void setListaestadosclientes(List<Estadocliente> listaestadosclientes) {
        this.listaestadosclientes = listaestadosclientes;
    }

    public void guardar(Integer idCliente) {
        
        int index=-1;
        for(int i = 0; i < clientes.size(); i++){
            if(clientes.get(i).getEventos().getIdEvento()==elemento.getIdEvento()&&clientes.get(i).getClientes().getIdCliente()==idCliente){
                index = i;
            }
        }
        ListasclienteseventoPojo l = clientes.remove(index);
        Estadocliente ec = hostessFacade.findEstadoClientesByName(l.getIdEstadoCliente());
        Clienteevento lce = l.getListasclientesevento(ec, sessionBean.getUsuario());
        hostessFacade.guardarLCE(l.getListasclientesevento(ec, sessionBean.getUsuario()));
        List<Clienteevento> clientes2 = new ArrayList<Clienteevento>();
        for(ListasclienteseventoPojo pojo : clientes){
            clientes2.add(pojo.getListasclientesevento(ec, sessionBean.getUsuario()));
        }
        try {
            Clienteevento nuevo = hostessFacade.nuevoLCE((Integer) sessionBean.getAttributes().get("idEvento"), clientes2, lce);
            clientes2.add(nuevo);
            clientes.clear();
            for (Clienteevento lce2 : clientes2) {
                clientes.add(new ListasclienteseventoPojo(lce2));
            }
        } catch (EventoSinClientesPorRevisarException ex) {
            LOGGER.info(ex);
            FacesUtil.addInfoMessage("No hay mas clientes por revisar", "");
        }
    }

    public void nuevo(Integer idCliente) {
       int index=-1;
        for(int i = 0; i < clientes.size(); i++){
            if(clientes.get(i).getEventos().getIdEvento()==elemento.getIdEvento()&&clientes.get(i).getClientes().getIdCliente()==idCliente){
                index = i;
            }
        }
        ListasclienteseventoPojo l = clientes.remove(index);
        Estadocliente ec = hostessFacade.findEstadoClientesByName(l.getIdEstadoCliente());
        Clienteevento lce = l.getListasclientesevento(ec, sessionBean.getUsuario());
        List<Clienteevento> clientes2 = new ArrayList<Clienteevento>();
        for(ListasclienteseventoPojo pojo : clientes){
            clientes2.add(pojo.getListasclientesevento(ec, sessionBean.getUsuario()));
        }
        try {
            Clienteevento nuevo = hostessFacade.nuevoLCE((Integer) sessionBean.getAttributes().get("idEvento"), clientes2, lce);
            clientes2.add(nuevo);
            clientes.clear();
            for (Clienteevento lce2 : clientes2) {
                clientes.add(new ListasclienteseventoPojo(lce2));
            }
        } catch (EventoSinClientesPorRevisarException ex) {
            LOGGER.info(ex);
            FacesUtil.addInfoMessage("No hay mas clientes por revisar", "");
        }
    }
}
