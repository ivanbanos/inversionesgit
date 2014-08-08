/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.controladores;

import com.invbf.adminclientesapi.Casinos;
import com.invbf.adminclientesapi.Categorias;
import com.invbf.adminclientesapi.Eventos;
import com.invbf.adminclientesapi.Formularios;
import com.invbf.adminclientesapi.Perfiles;
import com.invbf.adminclientesapi.Vistas;
import com.invbf.adminclientesapi.facade.AdminFacade;
import com.invbf.adminclientesapi.facade.MarketingUserFacade;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;
import org.primefaces.model.UploadedFile;

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

    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public CrudPerfilesBean() {
    }

    @PostConstruct
    public void init() {
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
