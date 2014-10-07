/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.controladores;


import com.invbf.sistemagestionclientes.entity.Accion;
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
public class CrudAccionesBean {
    MarketingUserFacade marketingUserFacade;
    private List<Accion> lista;
    private Accion elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    private List<Accion> flista;

    public List<Accion> getFlista() {
        return flista;
    }

    public void setFlista(List<Accion> flista) {
        this.flista = flista;
    }
    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public CrudAccionesBean() {
    }

    @PostConstruct
    public void init() {
        marketingUserFacade = new MarketingUserFacadeImpl();
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("configuracion");
        elemento = new Accion();
        lista = marketingUserFacade.findAllAcciones();
    }

    public List<Accion> getLista() {
        return lista;
    }

    public void setLista(List<Accion> lista) {
        this.lista = lista;
    }

    public Accion getElemento() {
        return elemento;
    }

    public void setElemento(Accion elemento) {
        this.elemento = elemento;
    }

    public MarketingUserFacade getMarketingUserFacade() {
        return marketingUserFacade;
    }

    public void setMarketingUserFacade(MarketingUserFacade marketingUserFacade) {
        this.marketingUserFacade = marketingUserFacade;
    }

    
    public void delete(){
        marketingUserFacade.deleteAccion(elemento);
        lista = marketingUserFacade.findAllAcciones();
        sessionBean.registrarlog("eliminar", "Acciones", elemento.toString());
        FacesUtil.addInfoMessage("Accion eliminada", elemento.getNombre());
        elemento = new Accion();
    }
    
    public void guardar(){
        boolean opcion = marketingUserFacade.guardarAccion(elemento);
        lista = marketingUserFacade.findAllAcciones();
        if (opcion) {
            sessionBean.registrarlog("actualizar", "Acciones", elemento.toString());
        FacesUtil.addInfoMessage("Estado actualizado", elemento.getNombre());
        } else {
            sessionBean.registrarlog("crear", "Acciones", elemento.toString());
        FacesUtil.addInfoMessage("Estado creado", elemento.getNombre());
        }
        elemento = new Accion();
    }
    
}
