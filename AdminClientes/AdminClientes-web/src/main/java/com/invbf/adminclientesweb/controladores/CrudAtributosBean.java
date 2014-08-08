/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.controladores;

import com.invbf.adminclientesapi.Atributos;
import com.invbf.adminclientesapi.Categorias;
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
public class CrudAtributosBean {

    private static final Logger LOGGER =
            Logger.getLogger(SessionBean.class);
    @EJB
    MarketingUserFacade marketingUserFacade;
    private List<Atributos> lista;
    private Atributos elemento;

    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public CrudAtributosBean() {
    }

    @PostConstruct
    public void init() {
        elemento = new Atributos();
        lista = marketingUserFacade.findAllAtributos();
    }

    public List<Atributos> getLista() {
        return lista;
    }

    public void setLista(List<Atributos> lista) {
        this.lista = lista;
    }

    public Atributos getElemento() {
        return elemento;
    }

    public void setElemento(Atributos elemento) {
        this.elemento = elemento;
    }

    public MarketingUserFacade getMarketingUserFacade() {
        return marketingUserFacade;
    }

    public void setMarketingUserFacade(MarketingUserFacade marketingUserFacade) {
        this.marketingUserFacade = marketingUserFacade;
    }

    
    public void delete(){
        marketingUserFacade.deleteAtributos(elemento);
        lista = marketingUserFacade.findAllAtributos();
        elemento = new Atributos();
    }
    
    public void guardar(){
        marketingUserFacade.guardarAtributos(elemento);
        lista = marketingUserFacade.findAllAtributos();
        elemento = new Atributos();
    }
    
}
