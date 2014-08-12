/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.controladores;

import com.invbf.adminclientesapi.entity.Categorias;
import com.invbf.adminclientesapi.facade.MarketingUserFacade;
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
public class CrudCategoriasBean {

    private static final Logger LOGGER =
            Logger.getLogger(SessionBean.class);
    @EJB
    MarketingUserFacade marketingUserFacade;
    private List<Categorias> lista;
    private Categorias elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    
    private List<Categorias> flista;

    public List<Categorias> getFlista() {
        return flista;
    }

    public void setFlista(List<Categorias> flista) {
        this.flista = flista;
    }
    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public CrudCategoriasBean() {
    }

    @PostConstruct
    public void init() {
        if(!sessionBean.perfilViewMatch("CrudCategoriasView")){
            try {
                sessionBean.Desconectar();
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
                LOGGER.error(ex);
            }
        }
        elemento = new Categorias();
        lista = marketingUserFacade.findAllCategorias();
    }

    public List<Categorias> getLista() {
        return lista;
    }

    public void setLista(List<Categorias> lista) {
        this.lista = lista;
    }

    public Categorias getElemento() {
        return elemento;
    }

    public void setElemento(Categorias elemento) {
        this.elemento = elemento;
    }

    public MarketingUserFacade getMarketingUserFacade() {
        return marketingUserFacade;
    }

    public void setMarketingUserFacade(MarketingUserFacade marketingUserFacade) {
        this.marketingUserFacade = marketingUserFacade;
    }

    public void delete() {
        marketingUserFacade.deleteCategorias(elemento);
        lista = marketingUserFacade.findAllCategorias();
        elemento = new Categorias();
    }

    public void guardar() {
        marketingUserFacade.guardarCategorias(elemento);
        lista = marketingUserFacade.findAllCategorias();
        elemento = new Categorias();
    }
}
