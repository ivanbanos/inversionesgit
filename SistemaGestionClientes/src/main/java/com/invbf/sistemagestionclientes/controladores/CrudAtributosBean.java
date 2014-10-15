/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.controladores;

import com.invbf.sistemagestionclientes.entity.Atributo;
import com.invbf.sistemagestionclientes.facade.MarketingUserFacade;
import com.invbf.sistemagestionclientes.facade.impl.MarketingUserFacadeImpl;
import com.invbf.sistemagestionclientes.util.FacesUtil;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author ideacentre
 */
@ManagedBean
@ViewScoped
public class CrudAtributosBean {
    private List<Atributo> lista;
    private Atributo elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }
    
private List<Atributo> flista;

    public List<Atributo> getFlista() {
        return flista;
    }

    public void setFlista(List<Atributo> flista) {
        this.flista = flista;
    }
    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public CrudAtributosBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("configuracion");
        elemento = new Atributo();
        lista = sessionBean.marketingUserFacade.findAllAtributos();
        
    }

    public List<Atributo> getLista() {
        return lista;
    }

    public void setLista(List<Atributo> lista) {
        this.lista = lista;
    }

    public Atributo getElemento() {
        return elemento;
    }

    public void setElemento(Atributo elemento) {
        this.elemento = elemento;
    }
    
    public void delete(){
        sessionBean.marketingUserFacade.deleteAtributos(elemento);
        lista = sessionBean.marketingUserFacade.findAllAtributos();
        sessionBean.registrarlog("eliminar", "Atributos", elemento.getNombre());
            FacesUtil.addInfoMessage("Atributo eliminado", elemento.getNombre());
        elemento = new Atributo();
    }
    
    public void guardar(){
        elemento.setTipoDato("Text");
        boolean opcion = sessionBean.marketingUserFacade.guardarAtributos(elemento);
        lista = sessionBean.marketingUserFacade.findAllAtributos();
        if (opcion) {
            sessionBean.registrarlog("actualizar", "Atributos", elemento.getNombre());
            FacesUtil.addInfoMessage("Atributo actualizado", elemento.getNombre());
        } else {
            sessionBean.registrarlog("crear", "Atributos", elemento.getNombre());
            FacesUtil.addInfoMessage("Atributo creado", elemento.getNombre());
        }
        elemento = new Atributo();
    }
    
}
