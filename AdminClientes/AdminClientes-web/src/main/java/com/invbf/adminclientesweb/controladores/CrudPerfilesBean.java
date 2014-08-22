/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.controladores;

import com.invbf.adminclientesapi.entity.Formularios;
import com.invbf.adminclientesapi.entity.Perfiles;
import com.invbf.adminclientesapi.entity.Vistas;
import com.invbf.adminclientesapi.facade.AdminFacade;
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
    private List<Perfiles> lista;
    private List<Formularios> listaformularios;
    private List<Vistas> listavistas;
    private Perfiles elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private DualListModel<Formularios> todosForm;
    private DualListModel<Vistas> todasVistas;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }
    private List<Perfiles> flista;

    public List<Perfiles> getFlista() {
        return flista;
    }

    public void setFlista(List<Perfiles> flista) {
        this.flista = flista;
    }

    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public CrudPerfilesBean() {
    }

    @PostConstruct
    public void init() {
        elemento = new Perfiles();
        lista = adminFacade.findAllPerfiles();
        listaformularios = new ArrayList<Formularios>();
        listavistas = new ArrayList<Vistas>();
        todasVistas = new DualListModel<Vistas>(listavistas, elemento.getVistasList());
        todosForm = new DualListModel<Formularios>(listaformularios, elemento.getFormulariosList());
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

    public void delete() {
        adminFacade.deletePerfiles(elemento);
        lista = adminFacade.findAllPerfiles();
        elemento = new Perfiles();
        sessionBean.notifyObserver("Perfiles");
    }

    public void guardar() {
        adminFacade.guardarPerfiles(elemento);
        lista = adminFacade.findAllPerfiles();
        elemento = new Perfiles();
        sessionBean.notifyObserver("Perfiles");
    }

    public void goPerfil(int id) {
        try {
            sessionBean.getAttributes().put("idPerfil", new Integer(id));
            FacesContext.getCurrentInstance().getExternalContext().redirect("PerfilAct.xhtml");
        } catch (IOException ex) {
                LOGGER.error(ex);
        }
    }

    public DualListModel<Formularios> getTodosForm() {
        return todosForm;
    }

    public void setTodosForm(DualListModel<Formularios> todosForm) {
        this.todosForm = todosForm;
    }

    public DualListModel<Vistas> getTodasVistas() {
        return todasVistas;
    }

    public void setTodasVistas(DualListModel<Vistas> todasVistas) {
        this.todasVistas = todasVistas;
    }
    
    
}
