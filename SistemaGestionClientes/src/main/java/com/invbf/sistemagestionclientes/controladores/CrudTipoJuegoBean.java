/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.controladores;

import com.invbf.sistemagestionclientes.entity.TipoJuego;
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
public class CrudTipoJuegoBean {
    MarketingUserFacade marketingUserFacade;
    private List<TipoJuego> lista;
    private TipoJuego elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    
    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }
    
    private List<TipoJuego> flista;

    public List<TipoJuego> getFlista() {
        return flista;
    }

    public void setFlista(List<TipoJuego> flista) {
        this.flista = flista;
    }

    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public CrudTipoJuegoBean() {
    }

    @PostConstruct
    public void init() {
        marketingUserFacade = new MarketingUserFacadeImpl();
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("configuracion");
        elemento = new TipoJuego();
        lista = marketingUserFacade.findAllTiposjuegos();
    }

    public List<TipoJuego> getLista() {
        return lista;
    }

    public void setLista(List<TipoJuego> lista) {
        this.lista = lista;
    }

    public TipoJuego getElemento() {
        return elemento;
    }

    public void setElemento(TipoJuego elemento) {
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
        sessionBean.registrarlog("eliminar", "TiposJuegos", elemento.toString());
            FacesUtil.addInfoMessage("Tipo de juegos eliminado", elemento.getNombre());
        elemento = new TipoJuego();
    }
    
    public void guardar(){
        boolean opcion = marketingUserFacade.guardarTiposjuegos(elemento);
        lista = marketingUserFacade.findAllTiposjuegos();
        
        if (opcion) {
            sessionBean.registrarlog("actualizar", "TiposJuegos", elemento.toString());
            FacesUtil.addInfoMessage("Tipo de juegos actualizado", elemento.getNombre());
        } else {
            sessionBean.registrarlog("crear", "TiposJuegos", elemento.toString());
            FacesUtil.addInfoMessage("Tipo de juegos creado", elemento.getNombre());
        }
        elemento = new TipoJuego();
    }
    
}
