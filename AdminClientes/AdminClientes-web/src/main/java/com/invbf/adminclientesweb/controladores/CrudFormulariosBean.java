/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.controladores;

import com.invbf.adminclientesapi.entity.Formularios;
import com.invbf.adminclientesapi.facade.AdminFacade;
import com.invbf.adminclientesweb.util.FacesUtil;
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
public class CrudFormulariosBean {

    private static final Logger LOGGER =
            Logger.getLogger(SessionBean.class);
    @EJB
    AdminFacade adminFacade;
    private List<Formularios> lista;
    private Formularios elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }
    
    private List<Formularios> flista;

    public List<Formularios> getFlista() {
        return flista;
    }

    public void setFlista(List<Formularios> flista) {
        this.flista = flista;
    }
    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public CrudFormulariosBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
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
        sessionBean.registrarlog("eliminar", "Formularios", elemento.toString());
            FacesUtil.addInfoMessage("Formulario eliminado", elemento.getAccion()+" "+elemento.getTabla());
        elemento = new Formularios();
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
        elemento = new Formularios();
    }
    
}
