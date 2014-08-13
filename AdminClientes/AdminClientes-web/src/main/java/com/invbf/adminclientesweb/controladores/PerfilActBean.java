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
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
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
public class PerfilActBean {

    private static final Logger LOGGER =
            Logger.getLogger(SessionBean.class);
    @EJB
    AdminFacade adminFacade;
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

    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public PerfilActBean() {
    }

    @PostConstruct
    public void init() {
        if (!sessionBean.perfilViewMatch("PerfilAct")) {
            try {
                sessionBean.Desconectar();
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
                LOGGER.error(ex);
            }
        }
        
        if (sessionBean.getAttributes()==null||!sessionBean.getAttributes().containsKey("idPerfil")) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("CrudPerfilesView.xhtml");
            } catch (IOException ex) {
                LOGGER.error(ex);
            }
        }
        elemento = adminFacade.findPerfil((Integer)sessionBean.getAttributes().get("idPerfil"));
        listaformularios = adminFacade.findAllFormularios();
        listavistas = adminFacade.findAllVistas();
        for (Formularios f : elemento.getFormulariosList()) {
            if (listaformularios.contains(f)) {
                listaformularios.remove(f);
            }
        }
        for (Vistas v : elemento.getVistasList()) {
            if (listavistas.contains(v)) {
                listavistas.remove(v);
            }
        }
        todasVistas = new DualListModel<Vistas>(listavistas, elemento.getVistasList());
        todosForm = new DualListModel<Formularios>(listaformularios, elemento.getFormulariosList());
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


    public void guardar() {
        try {
            elemento.setFormulariosList(todosForm.getTarget());
            elemento.setVistasList(todasVistas.getTarget());
            adminFacade.guardarPerfiles(elemento);
            sessionBean.actualizarUsuario();
            sessionBean.getAttributes().remove("idPerfil");
            FacesContext.getCurrentInstance().getExternalContext().redirect("CrudPerfilesView.xhtml");
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
