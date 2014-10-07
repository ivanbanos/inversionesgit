/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.controladores;

import com.invbf.sistemagestionclientes.entity.Vista;
import com.invbf.sistemagestionclientes.facade.AdminFacade;
import com.invbf.sistemagestionclientes.facade.impl.AdminFacadeImpl;
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
    AdminFacade adminFacade;
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
        adminFacade = new AdminFacadeImpl();
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("configuracion");
        elemento = new Vista();
        lista = adminFacade.findAllVistas();
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

    public AdminFacade getAdminFacade() {
        return adminFacade;
    }

    public void setAdminFacade(AdminFacade adminFacade) {
        this.adminFacade = adminFacade;
    }

    public void delete() {
        adminFacade.deleteVistas(elemento);
        lista = adminFacade.findAllVistas();

        sessionBean.registrarlog("eliminar", "Vistas", elemento.toString());
        FacesUtil.addInfoMessage("Vista borrada", elemento.getNombreVista());
        elemento = new Vista();

    }

    public void guardar() {
        boolean opcion = adminFacade.guardarVistas(elemento);
        lista = adminFacade.findAllVistas();

        if (opcion) {
            sessionBean.registrarlog("actualizar", "Vistas", elemento.toString());
            FacesUtil.addInfoMessage("Vista actualizada", elemento.getNombreVista());
        } else {
            sessionBean.registrarlog("crear", "Vistas", elemento.toString());
            FacesUtil.addInfoMessage("Vista creada", elemento.getNombreVista());
        }
        elemento = new Vista();
    }
}
