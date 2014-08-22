/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.controladores;

import com.invbf.adminclientesapi.entity.Vistas;
import com.invbf.adminclientesapi.facade.AdminFacade;
import java.io.IOException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
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
    private List<Vistas> lista;
    private Vistas elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    
    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    private List<Vistas> flista;

    public List<Vistas> getFlista() {
        return flista;
    }

    public void setFlista(List<Vistas> flista) {
        this.flista = flista;
    }
    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public CrudVistasBean() {
    }

    @PostConstruct
    public void init() {
        elemento = new Vistas();
        lista = adminFacade.findAllVistas();
    }

    public List<Vistas> getLista() {
        return lista;
    }

    public void setLista(List<Vistas> lista) {
        this.lista = lista;
    }

    public Vistas getElemento() {
        return elemento;
    }

    public void setElemento(Vistas elemento) {
        this.elemento = elemento;
    }


    public AdminFacade getAdminFacade() {
        return adminFacade;
    }


    public void setAdminFacade(AdminFacade adminFacade) {
        this.adminFacade = adminFacade;
    }

    
    public void delete(){
        adminFacade.deleteVistas(elemento);
        lista = adminFacade.findAllVistas();
        elemento = new Vistas();
    }
    
    public void guardar(){
        adminFacade.guardarVistas(elemento);
        lista = adminFacade.findAllVistas();
        elemento = new Vistas();
    }
    
}
