/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.controladores;

import com.invbf.adminclientesapi.Estadoscliente;
import com.invbf.adminclientesapi.facade.MarketingUserFacade;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
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
        elemento = new Estadoscliente();
    }
    
    public void guardar(){
        marketingUserFacade.guardarEstadoCliente(elemento);
        lista = marketingUserFacade.findAllEstadosClietes();
        elemento = new Estadoscliente();
    }
    
}