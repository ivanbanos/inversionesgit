/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.controladores;


import com.invbf.adminclientesapi.entity.Perfiles;
import com.invbf.adminclientesapi.entity.Usuarios;
import com.invbf.adminclientesapi.facade.AdminFacade;
import com.invbf.adminclientesweb.interfaces.Observer;
import java.io.IOException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
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
public class CrudUsuariosBean implements Observer{

    private static final Logger LOGGER =
            Logger.getLogger(SessionBean.class);
    @EJB
    AdminFacade adminFacade;
    private List<Usuarios> lista;
    private Usuarios elemento;
    private List<Perfiles> listaperfiles;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    
    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    private List<Usuarios> flista;

    public List<Usuarios> getFlista() {
        return flista;
    }

    public void setFlista(List<Usuarios> flista) {
        this.flista = flista;
    }
    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public CrudUsuariosBean() {
    }

    @PostConstruct
    public void init() {
        elemento = new Usuarios();
        lista = adminFacade.findAllUsuarios();
        listaperfiles = adminFacade.findAllPerfiles();
        sessionBean.registerObserver(this);
    }
    
    @PreDestroy
    public void preDestroy() {
        sessionBean.removeObserver(this);
    }

    public List<Usuarios> getLista() {
        return lista;
    }

    public void setLista(List<Usuarios> lista) {
        this.lista = lista;
    }

    public Usuarios getElemento() {
        return elemento;
    }

    public void setElemento(Usuarios elemento) {
        this.elemento = elemento;
    }

    public AdminFacade getAdminFacade() {
        return adminFacade;
    }

    public void setAdminFacade(AdminFacade adminFacade) {
        this.adminFacade = adminFacade;
    }

    public List<Perfiles> getListaperfiles() {
        return listaperfiles;
    }

    public void setListaperfiles(List<Perfiles> listaperfiles) {
        this.listaperfiles = listaperfiles;
    }

    public void delete(){
        adminFacade.deleteUsuarios(elemento);
        lista = adminFacade.findAllUsuarios();
        elemento = new Usuarios();
    }
    
    public void guardar(){
        adminFacade.guardarUsuarios(elemento);
        lista = adminFacade.findAllUsuarios();
        elemento = new Usuarios();
    }

    @Override
    public void update() {
        listaperfiles = adminFacade.findAllPerfiles();
    }
    
    
}
