/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.controladores;

import com.invbf.sistemagestionclientes.entity.Vista;
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
public class CrudVistasBean {
    private List<Vista> lista;
    private Vista elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }
    private List<Vista> flista;

    public List<Vista> getFlista() {
        return flista;
    }

    public void setFlista(List<Vista> flista) {
        this.flista = flista;
    }

    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public CrudVistasBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("configuracion");
        elemento = new Vista();
        lista = sessionBean.adminFacade.findAllVistas();
    }

    public List<Vista> getLista() {
        return lista;
    }

    public void setLista(List<Vista> lista) {
        this.lista = lista;
    }

    public Vista getElemento() {
        return elemento;
    }

    public void setElemento(Vista elemento) {
        this.elemento = elemento;
    }

    public void delete() {
        sessionBean.adminFacade.deleteVistas(elemento);
        lista = sessionBean.adminFacade.findAllVistas();
        FacesUtil.addInfoMessage("Vista borrada", elemento.getNombreVista());
        elemento = new Vista();

    }

    public void guardar() {
        boolean opcion = sessionBean.adminFacade.guardarVistas(elemento);
        lista = sessionBean.adminFacade.findAllVistas();

        if (opcion) {
            FacesUtil.addInfoMessage("Vista actualizada", elemento.getNombreVista());
        } else {
            FacesUtil.addInfoMessage("Vista creada", elemento.getNombreVista());
        }
        elemento = new Vista();
    }
}
