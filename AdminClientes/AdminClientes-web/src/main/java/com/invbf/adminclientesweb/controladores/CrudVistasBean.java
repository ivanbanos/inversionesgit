/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.controladores;

import com.invbf.adminclientesapi.entity.Vista;
import com.invbf.adminclientesapi.facade.AdminFacade;
import com.invbf.adminclientesweb.util.FacesUtil;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;

/**
 *
 * @author ideacentre
 */
@ManagedBean
@ViewScoped
public class CrudVistasBean {

    private static final Logger LOGGER =
            Logger.getLogger(SessionBean.class);
    @EJB
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
