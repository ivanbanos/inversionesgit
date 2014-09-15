/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.controladores;

import com.invbf.adminclientesapi.entity.Atributo;
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
public class CrudAtributosBean {

    private static final Logger LOGGER =
            Logger.getLogger(SessionBean.class);
    @EJB
    MarketingUserFacade marketingUserFacade;
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
        lista = marketingUserFacade.findAllAtributos();
        
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

    public MarketingUserFacade getMarketingUserFacade() {
        return marketingUserFacade;
    }

    public void setMarketingUserFacade(MarketingUserFacade marketingUserFacade) {
        this.marketingUserFacade = marketingUserFacade;
    }

    
    public void delete(){
        marketingUserFacade.deleteAtributos(elemento);
        lista = marketingUserFacade.findAllAtributos();
        sessionBean.registrarlog("eliminar", "Atributos", elemento.getNombre());
            FacesUtil.addInfoMessage("Atributo eliminado", elemento.getNombre());
        elemento = new Atributo();
    }
    
    public void guardar(){
        elemento.setTipoDato("Text");
        boolean opcion = marketingUserFacade.guardarAtributos(elemento);
        lista = marketingUserFacade.findAllAtributos();
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
