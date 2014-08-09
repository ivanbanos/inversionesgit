/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.controladores;

import com.invbf.adminclientesapi.Formularios;
import com.invbf.adminclientesapi.Perfiles;
import com.invbf.adminclientesapi.Vistas;
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
public class CrudPerfilesBean {

    private static final Logger LOGGER =
            Logger.getLogger(SessionBean.class);
    @EJB
    AdminFacade adminFacade;
    private List<Perfiles> lista;
    private List<Formularios> listaformularios;
    private List<Vistas> listavistas;
    private Perfiles elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    
    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public CrudPerfilesBean() {
    }

    @PostConstruct
    public void init() {
        if(!sessionBean.perfilViewMatch("CrudPerfilesView")){
            try {
                sessionBean.Desconectar();
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
                LOGGER.error(ex);
            }
        }
        elemento = new Perfiles();
        lista = adminFacade.findAllPerfiles();
    }

    public List<Perfiles> getLista() {
        return lista;
    }

    public void setLista(List<Perfiles> lista) {
        this.lista = lista;
    }

    public Perfiles getElemento() {
        return elemento;
    }

    public void setElemento(Perfiles elemento) {
        this.elemento = elemento;
    }


    public AdminFacade getAdminFacade() {
        return adminFacade;
    }


    public void setAdminFacade(AdminFacade adminFacade) {
        this.adminFacade = adminFacade;
    }

    public List<Formularios> getListaformularios() {
        return listaformularios;
    }

    public void setListaformularios(List<Formularios> listaformularios) {
        this.listaformularios = listaformularios;
    }

    public List<Vistas> getListavistas() {
        return listavistas;
    }

    public void setListavistas(List<Vistas> listavistas) {
        this.listavistas = listavistas;
    }

    public void delete(){
        adminFacade.deletePerfiles(elemento);
        lista = adminFacade.findAllPerfiles();
        elemento = new Perfiles();
    }
    
    public void guardar(){
        adminFacade.guardarPerfiles(elemento);
        lista = adminFacade.findAllPerfiles();
        elemento = new Perfiles();
    }
    
}
