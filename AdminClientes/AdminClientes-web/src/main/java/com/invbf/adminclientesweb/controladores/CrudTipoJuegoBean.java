/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.controladores;

import com.invbf.adminclientesapi.entity.Tiposjuegos;
import com.invbf.adminclientesapi.facade.MarketingUserFacade;
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
public class CrudTipoJuegoBean {

    private static final Logger LOGGER =
            Logger.getLogger(SessionBean.class);
    @EJB
    MarketingUserFacade marketingUserFacade;
    private List<Tiposjuegos> lista;
    private Tiposjuegos elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    
    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }
    
    private List<Tiposjuegos> flista;

    public List<Tiposjuegos> getFlista() {
        return flista;
    }

    public void setFlista(List<Tiposjuegos> flista) {
        this.flista = flista;
    }

    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public CrudTipoJuegoBean() {
    }

    @PostConstruct
    public void init() {
        elemento = new Tiposjuegos();
        lista = marketingUserFacade.findAllTiposjuegos();
    }

    public List<Tiposjuegos> getLista() {
        return lista;
    }

    public void setLista(List<Tiposjuegos> lista) {
        this.lista = lista;
    }

    public Tiposjuegos getElemento() {
        return elemento;
    }

    public void setElemento(Tiposjuegos elemento) {
        this.elemento = elemento;
    }

    public MarketingUserFacade getMarketingUserFacade() {
        return marketingUserFacade;
    }

    public void setMarketingUserFacade(MarketingUserFacade marketingUserFacade) {
        this.marketingUserFacade = marketingUserFacade;
    }

    
    public void delete(){
        marketingUserFacade.deleteTiposjuegos(elemento);
        lista = marketingUserFacade.findAllTiposjuegos();
        elemento = new Tiposjuegos();
    }
    
    public void guardar(){
        marketingUserFacade.guardarTiposjuegos(elemento);
        lista = marketingUserFacade.findAllTiposjuegos();
        elemento = new Tiposjuegos();
    }
    
}
