/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.controladores;

import com.invbf.adminclientesapi.Casinos;
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
public class CrudCasinosBean {

    private static final Logger LOGGER =
            Logger.getLogger(SessionBean.class);
    @EJB
    MarketingUserFacade marketingUserFacade;
    private List<Casinos> lista;
    private Casinos elemento;

    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public CrudCasinosBean() {
    }

    @PostConstruct
    public void init() {
        elemento = new Casinos();
        lista = marketingUserFacade.findAllCasinos();
    }

    public List<Casinos> getLista() {
        return lista;
    }

    public void setLista(List<Casinos> lista) {
        this.lista = lista;
    }

    public Casinos getElemento() {
        return elemento;
    }

    public void setElemento(Casinos elemento) {
        this.elemento = elemento;
    }

    public MarketingUserFacade getMarketingUserFacade() {
        return marketingUserFacade;
    }

    public void setMarketingUserFacade(MarketingUserFacade marketingUserFacade) {
        this.marketingUserFacade = marketingUserFacade;
    }

    
    public void delete(){
        marketingUserFacade.deleteCasinos(elemento);
        lista = marketingUserFacade.findAllCasinos();
        elemento = new Casinos();
    }
    
    public void guardar(){
        marketingUserFacade.guardarCasinos(elemento);
        lista = marketingUserFacade.findAllCasinos();
        elemento = new Casinos();
    }
    
}
