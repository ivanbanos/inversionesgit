/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.controladores;

import com.invbf.adminclientesapi.entity.Formulario;
import com.invbf.adminclientesapi.entity.Perfil;
import com.invbf.adminclientesapi.entity.Vista;
import com.invbf.adminclientesapi.exceptions.PerfilExistenteException;
import com.invbf.adminclientesapi.facade.AdminFacade;
import com.invbf.adminclientesweb.util.FacesUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;
import org.primefaces.model.DualListModel;

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
    private List<Perfil> lista;
    private List<Formulario> listaformularios;
    private List<Vista> listavistas;
    private Perfil elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private DualListModel<Formulario> todosForm;
    private DualListModel<Vista> todasVistas;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }
    private List<Perfil> flista;

    public List<Perfil> getFlista() {
        return flista;
    }

    public void setFlista(List<Perfil> flista) {
        this.flista = flista;
    }

    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public CrudPerfilesBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("configuracion");
        elemento = new Perfil();
        lista = adminFacade.findAllPerfiles();
        listaformularios = new ArrayList<Formulario>();
        listavistas = new ArrayList<Vista>();
        todasVistas = new DualListModel<Vista>(listavistas, elemento.getVistasList());
        todosForm = new DualListModel<Formulario>(listaformularios, elemento.getFormulariosList());
    }

    public List<Perfil> getLista() {
        return lista;
    }

    public void setLista(List<Perfil> lista) {
        this.lista = lista;
    }

    public Perfil getElemento() {
        return elemento;
    }

    public void setElemento(Perfil elemento) {
        this.elemento = elemento;
    }

    public AdminFacade getAdminFacade() {
        return adminFacade;
    }

    public void setAdminFacade(AdminFacade adminFacade) {
        this.adminFacade = adminFacade;
    }

    public List<Formulario> getListaformularios() {
        return listaformularios;
    }

    public void setListaformularios(List<Formulario> listaformularios) {
        this.listaformularios = listaformularios;
    }

    public List<Vista> getListavistas() {
        return listavistas;
    }

    public void setListavistas(List<Vista> listavistas) {
        this.listavistas = listavistas;
    }

    public void delete() {
        adminFacade.deletePerfiles(elemento);
        lista = adminFacade.findAllPerfiles();
        sessionBean.registrarlog("eliminar", "Perfiles", elemento.getNombre());
        FacesUtil.addInfoMessage("Perfil eliminado", elemento.getNombre());
        elemento = new Perfil();
        sessionBean.notifyObserver("Perfiles");

    }

    public void guardar() {
        try {
            boolean opcion = adminFacade.guardarPerfiles(elemento);
            lista = adminFacade.findAllPerfiles();
            sessionBean.notifyObserver("Perfiles");
            if (opcion) {
                sessionBean.registrarlog("actualizar", "Perfiles", elemento.getNombre());
                FacesUtil.addInfoMessage("Perfil actualizado", elemento.getNombre());
            } else {
                sessionBean.registrarlog("crear", "Perfiles", elemento.getNombre());
                FacesUtil.addInfoMessage("Perfil creado", elemento.getNombre());
            }
            elemento = new Perfil();
        } catch (PerfilExistenteException ex) {
            FacesUtil.addErrorMessage("Perfil no creado", "Nombre de perfil existente");
        }
    }

    public void goPerfil(int id) {
        try {
            sessionBean.getAttributes().put("idPerfil", new Integer(id));
            FacesContext.getCurrentInstance().getExternalContext().redirect("PerfilAct.xhtml");
        } catch (IOException ex) {
            LOGGER.error(ex);
        }
    }

    public DualListModel<Formulario> getTodosForm() {
        return todosForm;
    }

    public void setTodosForm(DualListModel<Formulario> todosForm) {
        this.todosForm = todosForm;
    }

    public DualListModel<Vista> getTodasVistas() {
        return todasVistas;
    }

    public void setTodasVistas(DualListModel<Vista> todasVistas) {
        this.todasVistas = todasVistas;
    }
}
