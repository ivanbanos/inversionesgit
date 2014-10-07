/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.controladores;

import com.invbf.sistemagestionclientes.entity.Casino;
import com.invbf.sistemagestionclientes.facade.MarketingUserFacade;
import com.invbf.sistemagestionclientes.facade.impl.MarketingUserFacadeImpl;
import com.invbf.sistemagestionclientes.util.FacesUtil;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author ideacentre
 */
@ManagedBean
@ViewScoped
public class CrudCasinosBean {
    MarketingUserFacade marketingUserFacade;
    private List<Casino> lista;
    private Casino elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;

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
    public CrudCasinosBean() {
    }

    @PostConstruct
    public void init() {
        marketingUserFacade = new MarketingUserFacadeImpl();
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("configuracion");
        elemento = new Casino();
        lista = marketingUserFacade.findAllCasinos();
    }

    public List<Casino> getLista() {
        return lista;
    }

    public void setLista(List<Casino> lista) {
        this.lista = lista;
    }

    public Casino getElemento() {
        return elemento;
    }

    public void setElemento(Casino elemento) {
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
            FacesUtil.addInfoMessage("Casino eliminado", elemento.getNombre());
        elemento = new Casino();
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
        elemento = new Casino();
    }
    
}
