/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.controladores;

import com.invbf.sistemagestionclientes.entity.Casino;
import com.invbf.sistemagestionclientes.entitySGB.Cargos;
import com.invbf.sistemagestionclientes.facade.impl.MarketingUserFacadeImpl;
import com.invbf.sistemagestionclientes.util.FacesUtil;
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
public class CrudCargosBean {
    private List<Cargos> lista;
    private Cargos elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public CrudCargosBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("configuracion");
        elemento = new Cargos();
        lista = sessionBean.adminFacade.findAllCargos();
    }

    public List<Cargos> getLista() {
        return lista;
    }

    public void setLista(List<Cargos> lista) {
        this.lista = lista;
    }

    public Cargos getElemento() {
        return elemento;
    }

    public void setElemento(Cargos elemento) {
        this.elemento = elemento;
    }

    public void delete() {
        sessionBean.adminFacade.deleteCargos(elemento);
        lista = sessionBean.adminFacade.findAllCargos();
        FacesUtil.addInfoMessage("Casino eliminado", elemento.getNombre());
        elemento = new Cargos();
    }

    public void guardar() {
        boolean opcion = sessionBean.adminFacade.guardarCargos(elemento);
        lista = sessionBean.adminFacade.findAllCargos();
        if (opcion) {
            FacesUtil.addInfoMessage("Casino actualizado", elemento.getNombre());
        } else {
            FacesUtil.addInfoMessage("Casino creado", elemento.getNombre());
        }
        elemento = new Cargos();
    }
}
