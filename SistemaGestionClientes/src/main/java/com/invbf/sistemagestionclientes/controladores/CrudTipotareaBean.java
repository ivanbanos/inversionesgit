/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.controladores;


import com.invbf.sistemagestionclientes.entity.Accion;
import com.invbf.sistemagestionclientes.entity.Casino;
import com.invbf.sistemagestionclientes.entity.Tipotarea;
import com.invbf.sistemagestionclientes.facade.MarketingUserFacade;
import com.invbf.sistemagestionclientes.facade.impl.MarketingUserFacadeImpl;
import com.invbf.sistemagestionclientes.util.FacesUtil;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.DualListModel;

/**
 *
 * @author ideacentre
 */
@ManagedBean
@ViewScoped
public class CrudTipotareaBean {
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
        lista = sessionBean.marketingUserFacade.findAllTipotarea();
        elemento.setAccionList(new ArrayList<Accion>());
        acciones = sessionBean.marketingUserFacade.findAllAcciones();
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
        acciones = sessionBean.marketingUserFacade.findAllAcciones();
        for (Accion tj : elemento.getAccionList()) {
            if (acciones.contains(tj)) {
                acciones.remove(tj);
            }
        }
        AccionesTodos = new DualListModel<Accion>(acciones, elemento.getAccionList());
    }

    public void delete() {
        sessionBean.marketingUserFacade.deleteTipotarea(elemento);
        lista = sessionBean.marketingUserFacade.findAllTipotarea();
        sessionBean.registrarlog("eliminar", "Tipotareas", elemento.getNombre());
        FacesUtil.addInfoMessage("Tipo de evento eliminado", elemento.getNombre());
        elemento = new Tipotarea();
        elemento.setAccionList(new ArrayList<Accion>());
        acciones = sessionBean.marketingUserFacade.findAllAcciones();
        for (Accion tj : elemento.getAccionList()) {
            if (acciones.contains(tj)) {
                acciones.remove(tj);
            }
        }
        AccionesTodos = new DualListModel<Accion>(acciones, elemento.getAccionList());
    }

    public void guardar() {
        elemento.setAccionList(AccionesTodos.getTarget());
        boolean opcion = sessionBean.marketingUserFacade.guardarTipotarea(elemento);

        lista = sessionBean.marketingUserFacade.findAllTipotarea();
        if (opcion) {
            sessionBean.registrarlog("actualizar", "Tipotareas", elemento.getNombre());
            FacesUtil.addInfoMessage("Tipo de tarea actualizado", elemento.getNombre());
        } else {
            sessionBean.registrarlog("crear", "Tipotareas", elemento.getNombre());
            FacesUtil.addInfoMessage("Tipo de tarea creado", elemento.getNombre());
        }
        elemento = new Tipotarea();
        elemento.setAccionList(new ArrayList<Accion>());
        acciones = sessionBean.marketingUserFacade.findAllAcciones();
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
