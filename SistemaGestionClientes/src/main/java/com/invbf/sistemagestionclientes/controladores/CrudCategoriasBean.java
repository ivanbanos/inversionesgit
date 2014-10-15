/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.controladores;

import com.invbf.sistemagestionclientes.entity.Categoria;
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
public class CrudCategoriasBean {
    private List<Categoria> lista;
    private Categoria elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    
    private List<Categoria> flista;

    public List<Categoria> getFlista() {
        return flista;
    }

    public void setFlista(List<Categoria> flista) {
        this.flista = flista;
    }
    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public CrudCategoriasBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("configuracion");
        elemento = new Categoria();
        lista = sessionBean.marketingUserFacade.findAllCategorias();
    }

    public List<Categoria> getLista() {
        return lista;
    }

    public void setLista(List<Categoria> lista) {
        this.lista = lista;
    }

    public Categoria getElemento() {
        return elemento;
    }

    public void setElemento(Categoria elemento) {
        this.elemento = elemento;
    }

    public void delete() {
        sessionBean.marketingUserFacade.deleteCategorias(elemento);
        lista = sessionBean.marketingUserFacade.findAllCategorias();
        sessionBean.registrarlog("eliminar", "Categorias", elemento.getNombre());
        
            FacesUtil.addInfoMessage("Categoria eliminada", elemento.getNombre());
        elemento = new Categoria();
    }

    public void guardar() {
        boolean opcion = sessionBean.marketingUserFacade.guardarCategorias(elemento);
        lista = sessionBean.marketingUserFacade.findAllCategorias();
        if (opcion) {
            sessionBean.registrarlog("actualizar", "Categorias", elemento.getNombre());
            FacesUtil.addInfoMessage("Categoria actualizada", elemento.getNombre());
        } else {
            sessionBean.registrarlog("crear", "Categorias", elemento.getNombre());
            FacesUtil.addInfoMessage("Categoria creada", elemento.getNombre());
        }
        elemento = new Categoria();
    }
}
