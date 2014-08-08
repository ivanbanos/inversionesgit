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
public class CrudFormulariosBean {

    private static final Logger LOGGER =
            Logger.getLogger(SessionBean.class);
    @EJB
    AdminFacade adminFacade;
    private List<Formularios> lista;
    private Formularios elemento;

    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public CrudFormulariosBean() {
    }

    @PostConstruct
    public void init() {
        elemento = new Formularios();
        lista = adminFacade.findAllFormularios();
    }

    public List<Formularios> getLista() {
        return lista;
    }

    public void setLista(List<Formularios> lista) {
        this.lista = lista;
    }

    public Formularios getElemento() {
        return elemento;
    }

    public void setElemento(Formularios elemento) {
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
        elemento = new Formularios();
    }
    
    public void guardar(){
        adminFacade.guardarFormularios(elemento);
        lista = adminFacade.findAllFormularios();
        elemento = new Formularios();
    }
    
}