/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.controladores;

import com.invbf.adminclientesapi.entity.Estadoscliente;
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

/**
 *
 * @author ideacentre
 */
@ManagedBean
@ViewScoped
public class CrudEstadosClientesBean {

    private static final Logger LOGGER =
            Logger.getLogger(SessionBean.class);
    @EJB
    MarketingUserFacade marketingUserFacade;
    private List<Estadoscliente> lista;
    private Estadoscliente elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    private List<Estadoscliente> flista;

    public List<Estadoscliente> getFlista() {
        return flista;
    }

    public void setFlista(List<Estadoscliente> flista) {
        this.flista = flista;
    }
    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public CrudEstadosClientesBean() {
    }

    @PostConstruct
    public void init() {
        elemento = new Estadoscliente();
        lista = marketingUserFacade.findAllEstadosClietes();
    }

    public List<Estadoscliente> getLista() {
        return lista;
    }

    public void setLista(List<Estadoscliente> lista) {
        this.lista = lista;
    }

    public Estadoscliente getElemento() {
        return elemento;
    }

    public void setElemento(Estadoscliente elemento) {
        this.elemento = elemento;
    }

    public MarketingUserFacade getMarketingUserFacade() {
        return marketingUserFacade;
    }

    public void setMarketingUserFacade(MarketingUserFacade marketingUserFacade) {
        this.marketingUserFacade = marketingUserFacade;
    }

    
    public void delete(){
        marketingUserFacade.deleteEstadoCliente(elemento);
        lista = marketingUserFacade.findAllEstadosClietes();
        sessionBean.registrarlog("eliminar", "EstadosCliente", elemento.toString());
        FacesUtil.addInfoMessage("Estado eliminado", elemento.getNombre());
        elemento = new Estadoscliente();
    }
    
    public void guardar(){
        boolean opcion = marketingUserFacade.guardarEstadoCliente(elemento);
        lista = marketingUserFacade.findAllEstadosClietes();
        if (opcion) {
            sessionBean.registrarlog("actualizar", "EstadosCliente", elemento.toString());
        FacesUtil.addInfoMessage("Estado actualizado", elemento.getNombre());
        } else {
            sessionBean.registrarlog("crear", "EstadosCliente", elemento.toString());
        FacesUtil.addInfoMessage("Estado creado", elemento.getNombre());
        }
        elemento = new Estadoscliente();
    }
    
}
