/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.controladores;

import com.invbf.adminclientesapi.Categorias;
import com.invbf.adminclientesapi.Estadoscliente;
import com.invbf.adminclientesapi.Tiposjuegos;
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
public class CrudTipoJuegoBean {

    private static final Logger LOGGER =
            Logger.getLogger(SessionBean.class);
    @EJB
    MarketingUserFacade marketingUserFacade;
    private List<Tiposjuegos> lista;
    private Tiposjuegos elemento;

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
