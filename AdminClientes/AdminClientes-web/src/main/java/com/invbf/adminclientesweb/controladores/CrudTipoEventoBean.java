/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.controladores;

import com.invbf.adminclientesapi.entity.Casino;
import com.invbf.adminclientesapi.entity.Estadocliente;
import com.invbf.adminclientesapi.entity.TipoJuego;
import com.invbf.adminclientesapi.entity.Tipoevento;
import com.invbf.adminclientesapi.facade.MarketingUserFacade;
import com.invbf.adminclientesweb.util.FacesUtil;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;
import org.primefaces.model.DualListModel;

/**
 *
 * @author ideacentre
 */
@ManagedBean
@ViewScoped
public class CrudTipoEventoBean {

    private static final Logger LOGGER =
            Logger.getLogger(SessionBean.class);
    @EJB
    MarketingUserFacade marketingUserFacade;
    private List<Tipoevento> lista;
    private Tipoevento elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private DualListModel<Estadocliente> estadosClientesTodos;
    private List<Estadocliente> estadosCliente;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }
    private List<Casino> flista;

    public List<Casino> getFlista() {
        return flista;
    }

    public void setFlista(List<Casino> flista) {
        this.flista = flista;
    }

    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public CrudTipoEventoBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("configuracion");
        elemento = new Tipoevento();
        lista = marketingUserFacade.findAllTipoevento();
        elemento.setEstadosclienteList(new ArrayList<Estadocliente>());
        estadosCliente = marketingUserFacade.findAllEstadosClietes();
        for (Estadocliente tj : elemento.getEstadosclienteList()) {
            if (estadosCliente.contains(tj)) {
                estadosCliente.remove(tj);
            }
        }
        estadosClientesTodos = new DualListModel<Estadocliente>(estadosCliente, elemento.getEstadosclienteList());
    }

    public List<Tipoevento> getLista() {
        return lista;
    }

    public void setLista(List<Tipoevento> lista) {
        this.lista = lista;
    }

    public Tipoevento getElemento() {
        return elemento;
    }

    public void setElemento(Tipoevento elemento) {
        this.elemento = elemento;
        estadosCliente = marketingUserFacade.findAllEstadosClietes();
        for (Estadocliente tj : elemento.getEstadosclienteList()) {
            if (estadosCliente.contains(tj)) {
                estadosCliente.remove(tj);
            }
        }
        estadosClientesTodos = new DualListModel<Estadocliente>(estadosCliente, elemento.getEstadosclienteList());
    }

    public MarketingUserFacade getMarketingUserFacade() {
        return marketingUserFacade;
    }

    public void setMarketingUserFacade(MarketingUserFacade marketingUserFacade) {
        this.marketingUserFacade = marketingUserFacade;
    }

    public void delete() {
        marketingUserFacade.deleteTipoevento(elemento);
        lista = marketingUserFacade.findAllTipoevento();
        sessionBean.registrarlog("eliminar", "Tipoevento", elemento.getNombre());
        FacesUtil.addInfoMessage("Tipo de evento eliminado", elemento.getNombre());
        elemento = new Tipoevento();
        elemento.setEstadosclienteList(new ArrayList<Estadocliente>());
        estadosCliente = marketingUserFacade.findAllEstadosClietes();
        for (Estadocliente tj : elemento.getEstadosclienteList()) {
            if (estadosCliente.contains(tj)) {
                estadosCliente.remove(tj);
            }
        }
        estadosClientesTodos = new DualListModel<Estadocliente>(estadosCliente, elemento.getEstadosclienteList());
    }

    public void guardar() {
        elemento.setEstadosclienteList(estadosClientesTodos.getTarget());
        boolean opcion = marketingUserFacade.guardarTipoevento(elemento);

        lista = marketingUserFacade.findAllTipoevento();
        if (opcion) {
            sessionBean.registrarlog("actualizar", "Tipoevento", elemento.getNombre());
            FacesUtil.addInfoMessage("Tipo de evento actualizado", elemento.getNombre());
        } else {
            sessionBean.registrarlog("crear", "Casinos", elemento.getNombre());
            FacesUtil.addInfoMessage("Tipo de evento creado", elemento.getNombre());
        }
        elemento = new Tipoevento();
        elemento.setEstadosclienteList(new ArrayList<Estadocliente>());
        estadosCliente = marketingUserFacade.findAllEstadosClietes();
        for (Estadocliente tj : elemento.getEstadosclienteList()) {
            if (estadosCliente.contains(tj)) {
                estadosCliente.remove(tj);
            }
        }
        estadosClientesTodos = new DualListModel<Estadocliente>(estadosCliente, elemento.getEstadosclienteList());
    }

    public DualListModel<Estadocliente> getEstadosClientesTodos() {
        return estadosClientesTodos;
    }

    public void setEstadosClientesTodos(DualListModel<Estadocliente> estadosClientesTodos) {
        this.estadosClientesTodos = estadosClientesTodos;
    }
    
}
