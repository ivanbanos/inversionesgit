/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.controladores;

import com.invbf.adminclientesapi.entity.Formulario;
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
public class CrudFormulariosBean {

    private static final Logger LOGGER =
            Logger.getLogger(SessionBean.class);
    @EJB
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
