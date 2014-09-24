/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.controladores;

import com.invbf.adminclientesapi.entity.Casino;
import com.invbf.adminclientesapi.entity.TipoDocumento;
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
public class CrudTipoDocumentoBean {

    private static final Logger LOGGER =
            Logger.getLogger(SessionBean.class);
    @EJB
    MarketingUserFacade marketingUserFacade;
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
        lista = marketingUserFacade.findAllTipoDocumentos();
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

    public MarketingUserFacade getMarketingUserFacade() {
        return marketingUserFacade;
    }

    public void setMarketingUserFacade(MarketingUserFacade marketingUserFacade) {
        this.marketingUserFacade = marketingUserFacade;
    }

    
    public void delete(){
        marketingUserFacade.deleteTipoDocumentos(elemento);
        lista = marketingUserFacade.findAllTipoDocumentos();
        sessionBean.registrarlog("eliminar", "tipodocumento", elemento.getNombre());
            FacesUtil.addInfoMessage("Tipo documento eliminado", elemento.getNombre());
        elemento = new TipoDocumento();
    }
    
    public void guardar(){
        boolean opcion = marketingUserFacade.guardarTipoDocumentos(elemento);
        lista = marketingUserFacade.findAllTipoDocumentos();
        if (opcion) {
            sessionBean.registrarlog("actualizar", "tipodocumento", elemento.getNombre());
            FacesUtil.addInfoMessage("Tipo documento actualizado", elemento.getNombre());
        } else {
            sessionBean.registrarlog("crear", "tipodocumento", elemento.getNombre());
            FacesUtil.addInfoMessage("Tipo documento creado", elemento.getNombre());
        }
        elemento = new TipoDocumento();
    }
    
}
