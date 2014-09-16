/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.controladores;

import com.invbf.adminclientesapi.entity.Accion;
import com.invbf.adminclientesapi.entity.Casino;
import com.invbf.adminclientesapi.entity.Tipotarea;
import com.invbf.adminclientesapi.facade.MarketingUserFacade;
import com.invbf.adminclientesweb.util.FacesUtil;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;
import org.primefaces.model.DualListModel;

/**
 *
 * @author ideacentre
 */
@ManagedBean
@ViewScoped
public class CrudTipotareaBean {

    private static final Logger LOGGER =
            Logger.getLogger(SessionBean.class);
    @EJB
    MarketingUserFacade marketingUserFacade;
    private List<Tipotarea> lista;
    private Tipotarea elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private DualListModel<Accion> AccionesTodos;
    private List<Accion> acciones;

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
    public CrudTipotareaBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("configuracion");
        elemento = new Tipotarea();
        lista = marketingUserFacade.findAllTipotarea();
        elemento.setAccionList(new ArrayList<Accion>());
        acciones = marketingUserFacade.findAllAcciones();
        for (Accion tj : elemento.getAccionList()) {
            if (acciones.contains(tj)) {
                acciones.remove(tj);
            }
        }
        AccionesTodos = new DualListModel<Accion>(acciones, elemento.getAccionList());
    }

    public List<Tipotarea> getLista() {
        return lista;
    }

    public void setLista(List<Tipotarea> lista) {
        this.lista = lista;
    }

    public Tipotarea getElemento() {
        return elemento;
    }

    public void setElemento(Tipotarea elemento) {
        this.elemento = elemento;
        acciones = marketingUserFacade.findAllAcciones();
        for (Accion tj : elemento.getAccionList()) {
            if (acciones.contains(tj)) {
                acciones.remove(tj);
            }
        }
        AccionesTodos = new DualListModel<Accion>(acciones, elemento.getAccionList());
    }

    public MarketingUserFacade getMarketingUserFacade() {
        return marketingUserFacade;
    }

    public void setMarketingUserFacade(MarketingUserFacade marketingUserFacade) {
        this.marketingUserFacade = marketingUserFacade;
    }

    public void delete() {
        marketingUserFacade.deleteTipotarea(elemento);
        lista = marketingUserFacade.findAllTipotarea();
        sessionBean.registrarlog("eliminar", "Tipotareas", elemento.getNombre());
        FacesUtil.addInfoMessage("Tipo de evento eliminado", elemento.getNombre());
        elemento = new Tipotarea();
        elemento.setAccionList(new ArrayList<Accion>());
        acciones = marketingUserFacade.findAllAcciones();
        for (Accion tj : elemento.getAccionList()) {
            if (acciones.contains(tj)) {
                acciones.remove(tj);
            }
        }
        AccionesTodos = new DualListModel<Accion>(acciones, elemento.getAccionList());
    }

    public void guardar() {
        elemento.setAccionList(AccionesTodos.getTarget());
        boolean opcion = marketingUserFacade.guardarTipotarea(elemento);

        lista = marketingUserFacade.findAllTipotarea();
        if (opcion) {
            sessionBean.registrarlog("actualizar", "Tipotareas", elemento.getNombre());
            FacesUtil.addInfoMessage("Tipo de tarea actualizado", elemento.getNombre());
        } else {
            sessionBean.registrarlog("crear", "Tipotareas", elemento.getNombre());
            FacesUtil.addInfoMessage("Tipo de tarea creado", elemento.getNombre());
        }
        elemento = new Tipotarea();
        elemento.setAccionList(new ArrayList<Accion>());
        acciones = marketingUserFacade.findAllAcciones();
        for (Accion tj : elemento.getAccionList()) {
            if (acciones.contains(tj)) {
                acciones.remove(tj);
            }
        }
        AccionesTodos = new DualListModel<Accion>(acciones, elemento.getAccionList());
    }

    public DualListModel<Accion> getAccionesTodos() {
        return AccionesTodos;
    }

    public void setAccionesTodos(DualListModel<Accion> AccionesTodos) {
        this.AccionesTodos = AccionesTodos;
    }
    
}
