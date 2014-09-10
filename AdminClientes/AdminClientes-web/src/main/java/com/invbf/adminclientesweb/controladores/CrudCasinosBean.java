/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.controladores;

import com.invbf.adminclientesapi.entity.Casinos;
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
public class CrudCasinosBean {

    private static final Logger LOGGER =
            Logger.getLogger(SessionBean.class);
    @EJB
    MarketingUserFacade marketingUserFacade;
    private List<Casinos> lista;
    private Casinos elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    
    private List<Casinos> flista;

    public List<Casinos> getFlista() {
        return flista;
    }

    public void setFlista(List<Casinos> flista) {
        this.flista = flista;
    }
    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public CrudCasinosBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
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
        sessionBean.registrarlog("eliminar", "Casinos", elemento.getNombre());
            FacesUtil.addInfoMessage("Casino creado", elemento.getNombre());
        elemento = new Casinos();
    }
    
    public void guardar(){
        boolean opcion = marketingUserFacade.guardarCasinos(elemento);
        lista = marketingUserFacade.findAllCasinos();
        if (opcion) {
            sessionBean.registrarlog("actualizar", "Casinos", elemento.getNombre());
            FacesUtil.addInfoMessage("Casino actualizado", elemento.getNombre());
        } else {
            sessionBean.registrarlog("crear", "Casinos", elemento.getNombre());
            FacesUtil.addInfoMessage("Casino creado", elemento.getNombre());
        }
        elemento = new Casinos();
    }
    
}
