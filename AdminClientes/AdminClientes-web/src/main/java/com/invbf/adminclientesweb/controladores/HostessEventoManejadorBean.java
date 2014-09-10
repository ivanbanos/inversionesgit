/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.controladores;

import com.invbf.adminclientesapi.entity.Estadoscliente;
import com.invbf.adminclientesapi.entity.Eventos;
import com.invbf.adminclientesapi.entity.Listasclientesevento;
import com.invbf.adminclientesapi.exceptions.EventoSinClientesPorRevisarException;
import com.invbf.adminclientesapi.facade.AdminFacade;
import com.invbf.adminclientesapi.facade.HostessFacade;
import com.invbf.adminclientesapi.facade.MarketingUserFacade;
import com.invbf.adminclientesweb.util.FacesUtil;
import com.invbf.adminclientesweb.util.ListasclienteseventoPojo;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
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
    private List<ListasclienteseventoPojo> clientes;
    private List<Estadoscliente> listaestadosclientes;

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
            List<Listasclientesevento> clientes2 = hostessFacade.findClienteEventosHostess((Integer) sessionBean.getAttributes().get("idEvento"));
            for (Listasclientesevento lce : clientes2) {
                clientes.add(new ListasclienteseventoPojo(lce));
            }
        } catch (EventoSinClientesPorRevisarException ex) {
            LOGGER.info(ex);
        }
        listaestadosclientes = marketingUserFacade.findAllEstadosClietes();
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

    public List<ListasclienteseventoPojo> getClientes() {
        return clientes;
    }

    public void setClientes(List<ListasclienteseventoPojo> clientes) {
        this.clientes = clientes;
    }

    public List<Estadoscliente> getListaestadosclientes() {
        return listaestadosclientes;
    }

    public void setListaestadosclientes(List<Estadoscliente> listaestadosclientes) {
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
        Estadoscliente ec = hostessFacade.findEstadoClientesByName(l.getIdEstadoCliente());
        Listasclientesevento lce = l.getListasclientesevento(ec);
        hostessFacade.guardarLCE(l.getListasclientesevento(ec));
        List<Listasclientesevento> clientes2 = new ArrayList<Listasclientesevento>();
        for(ListasclienteseventoPojo pojo : clientes){
            clientes2.add(pojo.getListasclientesevento(ec));
        }
        try {
            Listasclientesevento nuevo = hostessFacade.nuevoLCE((Integer) sessionBean.getAttributes().get("idEvento"), clientes2, lce);
            clientes2.add(nuevo);
            clientes.clear();
            for (Listasclientesevento lce2 : clientes2) {
                clientes.add(new ListasclienteseventoPojo(lce2));
            }
        } catch (EventoSinClientesPorRevisarException ex) {
            LOGGER.info(ex);
            FacesUtil.addInfoMessage("No hay mas clientes por revisar", "");
        }
    }

    public void nuevo(Integer idCliente) {
        int index = clientes.indexOf(new Listasclientesevento(elemento.getIdEvento(), idCliente));
        ListasclienteseventoPojo l = clientes.remove(index);
        Estadoscliente ec = hostessFacade.findEstadoClientesByName(l.getIdEstadoCliente());
        Listasclientesevento lce = l.getListasclientesevento(ec);
        List<Listasclientesevento> clientes2 = new ArrayList<Listasclientesevento>();
        for(ListasclienteseventoPojo pojo : clientes){
            clientes2.add(pojo.getListasclientesevento(ec));
        }
        try {
            Listasclientesevento nuevo = hostessFacade.nuevoLCE((Integer) sessionBean.getAttributes().get("idEvento"), clientes2, lce);
            clientes2.add(nuevo);
            clientes.clear();
            for (Listasclientesevento lce2 : clientes2) {
                clientes.add(new ListasclienteseventoPojo(lce2));
            }
        } catch (EventoSinClientesPorRevisarException ex) {
            LOGGER.info(ex);
            FacesUtil.addInfoMessage("No hay mas clientes por revisar", "");
        }
    }
}
