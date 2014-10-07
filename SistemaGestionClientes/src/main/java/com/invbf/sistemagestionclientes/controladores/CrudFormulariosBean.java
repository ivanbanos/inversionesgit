/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.controladores;

import com.invbf.sistemagestionclientes.entity.Formulario;
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
public class CrudFormulariosBean {
    AdminFacade adminFacade;
    private List<Formulario> lista;
    private Formulario elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }
    
    private List<Formulario> flista;

    public List<Formulario> getFlista() {
        return flista;
    }

    public void setFlista(List<Formulario> flista) {
        this.flista = flista;
    }
    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public CrudFormulariosBean() {
    }

    @PostConstruct
    public void init() {
        adminFacade = new  AdminFacadeImpl();
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("configuracion");
        elemento = new Formulario();
        lista = adminFacade.findAllFormularios();
    }

    public List<Formulario> getLista() {
        return lista;
    }

    public void setLista(List<Formulario> lista) {
        this.lista = lista;
    }

    public Formulario getElemento() {
        return elemento;
    }

    public void setElemento(Formulario elemento) {
        this.elemento = elemento;
    }


    public AdminFacade getAdminFacade() {
        return adminFacade;
    }


    public void setAdminFacade(AdminFacade adminFacade) {
        this.adminFacade = adminFacade;
    }

    
    public void delete(){
        adminFacade.deleteFormularios(elemento);
        lista = adminFacade.findAllFormularios();
        sessionBean.registrarlog("eliminar", "Formularios", elemento.toString());
            FacesUtil.addInfoMessage("Formulario eliminado", elemento.getAccion()+" "+elemento.getTabla());
        elemento = new Formulario();
    }
    
    public void guardar(){
        boolean opcion = adminFacade.guardarFormularios(elemento);
        lista = adminFacade.findAllFormularios();
        if (opcion) {
            sessionBean.registrarlog("actualizar", "Formularios", elemento.toString());
            FacesUtil.addInfoMessage("Formulario actualizado", elemento.getAccion()+" "+elemento.getTabla());
        } else {
            sessionBean.registrarlog("crear", "Formularios", elemento.toString());
            FacesUtil.addInfoMessage("Formulario creado", elemento.getAccion()+" "+elemento.getTabla());
        }
        elemento = new Formulario();
    }
    
}
