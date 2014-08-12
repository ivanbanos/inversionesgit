/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.controladores;

import com.invbf.adminclientesapi.entity.Atributos;
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
public class CrudAtributosBean {

    private static final Logger LOGGER =
            Logger.getLogger(SessionBean.class);
    @EJB
    MarketingUserFacade marketingUserFacade;
    private List<Atributos> lista;
    private Atributos elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }
    
private List<Atributos> flista;

    public List<Atributos> getFlista() {
        return flista;
    }

    public void setFlista(List<Atributos> flista) {
        this.flista = flista;
    }
    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public CrudAtributosBean() {
    }

    @PostConstruct
    public void init() {
        if(!sessionBean.perfilViewMatch("CrudAtributosView")){
            try {
                sessionBean.Desconectar();
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
                LOGGER.error(ex);
            }
        }
        elemento = new Atributos();
        lista = marketingUserFacade.findAllAtributos();
        
    }

    public List<Atributos> getLista() {
        return lista;
    }

    public void setLista(List<Atributos> lista) {
        this.lista = lista;
    }

    public Atributos getElemento() {
        return elemento;
    }

    public void setElemento(Atributos elemento) {
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
        elemento = new Atributos();
    }
    
    public void guardar(){
        marketingUserFacade.guardarAtributos(elemento);
        lista = marketingUserFacade.findAllAtributos();
        elemento = new Atributos();
    }
    
}
