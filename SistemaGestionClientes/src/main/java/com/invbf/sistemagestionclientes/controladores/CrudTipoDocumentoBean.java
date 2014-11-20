/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.controladores;

import com.invbf.sistemagestionclientes.entity.TipoDocumento;
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
public class CrudTipoDocumentoBean {

    private List<TipoDocumento> lista;
    private TipoDocumento elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    private List<TipoDocumento> flista;

    public List<TipoDocumento> getFlista() {
        return flista;
    }

    public void setFlista(List<TipoDocumento> flista) {
        this.flista = flista;
    }

    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public CrudTipoDocumentoBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("configuracion");
        elemento = new TipoDocumento();
        lista = sessionBean.marketingUserFacade.findAllTipoDocumentos();
    }

    public List<TipoDocumento> getLista() {
        return lista;
    }

    public void setLista(List<TipoDocumento> lista) {
        this.lista = lista;
    }

    public TipoDocumento getElemento() {
        return elemento;
    }

    public void setElemento(TipoDocumento elemento) {
        this.elemento = elemento;
    }

    public void delete() {
        sessionBean.marketingUserFacade.deleteTipoDocumentos(elemento);
        lista = sessionBean.marketingUserFacade.findAllTipoDocumentos();
        FacesUtil.addInfoMessage("Tipo documento eliminado", elemento.getNombre());
        elemento = new TipoDocumento();
    }

    public void guardar() {
        boolean opcion = sessionBean.marketingUserFacade.guardarTipoDocumentos(elemento);
        lista = sessionBean.marketingUserFacade.findAllTipoDocumentos();
        if (opcion) {
            FacesUtil.addInfoMessage("Tipo documento actualizado", elemento.getNombre());
        } else {
            FacesUtil.addInfoMessage("Tipo documento creado", elemento.getNombre());
        }
        elemento = new TipoDocumento();
    }

}
