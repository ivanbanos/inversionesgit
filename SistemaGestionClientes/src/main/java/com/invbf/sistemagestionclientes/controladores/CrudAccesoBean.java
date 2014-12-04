/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.controladores;

import com.invbf.sistemagestionclientes.entitySGB.Accesos;
import com.invbf.sistemagestionclientes.util.FacesUtil;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author ivan
 */

@ManagedBean
@ViewScoped
public class CrudAccesoBean {
     private List<Accesos> lista;
    private Accesos elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public CrudAccesoBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("configuracion");
        elemento = new Accesos();
        lista = new ArrayList<Accesos>();
        lista = sessionBean.adminFacade.findAllAccesos();
    }

    public List<Accesos> getLista() {
        return lista;
    }
   
    public void setLista(List<Accesos> lista) {
        this.lista = lista;
}

    public Accesos getElemento() {
        return elemento;
    }

    public void setElemento(Accesos elemento) {
        this.elemento = elemento;
    }

    public void delete() {
        sessionBean.adminFacade.deleteAccesos(elemento);
        lista = sessionBean.adminFacade.findAllAccesos();
        FacesUtil.addInfoMessage("Casino eliminado", elemento.getNombre());
        elemento = new Accesos();
    }

    public void guardar() {
        boolean opcion = sessionBean.adminFacade.guardarAccesos(elemento);
        lista = sessionBean.adminFacade.findAllAccesos();
        if (opcion) {
            FacesUtil.addInfoMessage("Acceso actualizado", elemento.getNombre());
        } else {
            FacesUtil.addInfoMessage("Acceso creado", elemento.getNombre());
        }
        elemento = new Accesos();
    }
}
