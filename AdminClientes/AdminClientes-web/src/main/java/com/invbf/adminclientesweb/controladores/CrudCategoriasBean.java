/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.controladores;

import com.invbf.adminclientesapi.entity.Categoria;
import com.invbf.adminclientesapi.facade.MarketingUserFacade;
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
public class CrudCategoriasBean {

    private static final Logger LOGGER =
            Logger.getLogger(SessionBean.class);
    @EJB
    MarketingUserFacade marketingUserFacade;
    private List<Categoria> lista;
    private Categoria elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    
    private List<Categoria> flista;

    public List<Categoria> getFlista() {
        return flista;
    }

    public void setFlista(List<Categoria> flista) {
        this.flista = flista;
    }
    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public CrudCategoriasBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("configuracion");
        elemento = new Categoria();
        lista = marketingUserFacade.findAllCategorias();
    }

    public List<Categoria> getLista() {
        return lista;
    }

    public void setLista(List<Categoria> lista) {
        this.lista = lista;
    }

    public Categoria getElemento() {
        return elemento;
    }

    public void setElemento(Categoria elemento) {
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
        sessionBean.registrarlog("eliminar", "Categorias", elemento.getNombre());
        
            FacesUtil.addInfoMessage("Categoria eliminada", elemento.getNombre());
        elemento = new Categoria();
    }

    public void guardar() {
        boolean opcion = marketingUserFacade.guardarCategorias(elemento);
        lista = marketingUserFacade.findAllCategorias();
        if (opcion) {
            sessionBean.registrarlog("actualizar", "Categorias", elemento.getNombre());
            FacesUtil.addInfoMessage("Categoria actualizada", elemento.getNombre());
        } else {
            sessionBean.registrarlog("crear", "Categorias", elemento.getNombre());
            FacesUtil.addInfoMessage("Categoria creada", elemento.getNombre());
        }
        elemento = new Categoria();
    }
}
