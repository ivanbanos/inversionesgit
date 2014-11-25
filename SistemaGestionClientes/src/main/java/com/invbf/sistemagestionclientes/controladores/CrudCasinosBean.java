/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.controladores;

import com.invbf.sistemagestionclientes.entity.Casino;
import com.invbf.sistemagestionclientes.entitySGB.Casinosdetalles;
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
public class CrudCasinosBean {

    private List<Casino> lista;
    private Casino elemento;
    private Casinosdetalles detalleElemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    private List<Casino> flista;

    public List<Casino> getFlista() {
        return flista;
    }

    public void setFlista(List<Casino> flista) {
        this.flista = flista;
    }

    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public CrudCasinosBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.marketingUserFacade = new MarketingUserFacadeImpl();
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("configuracion");
        elemento = new Casino();
        detalleElemento = new Casinosdetalles();
        lista = sessionBean.marketingUserFacade.findAllCasinos();
    }

    public List<Casino> getLista() {
        return lista;
    }

    public void setLista(List<Casino> lista) {
        this.lista = lista;
    }

    public Casino getElemento() {
        return elemento;
    }

    public void setElemento(Casino elemento) {
        if (elemento.getIdCasino() != null) {
            detalleElemento = sessionBean.marketingUserFacade.getDetalleCasinoById(elemento.getIdCasino());
            if (detalleElemento == null) {
                detalleElemento = new Casinosdetalles(elemento.getIdCasino());
                detalleElemento = sessionBean.marketingUserFacade.guardarDetalleCasino(detalleElemento);
            }
        }
        this.elemento = elemento;
    }

    public void delete() {
        sessionBean.marketingUserFacade.deleteDetalleCasino(detalleElemento);
        sessionBean.marketingUserFacade.deleteCasinos(elemento);
        lista = sessionBean.marketingUserFacade.findAllCasinos();
        FacesUtil.addInfoMessage("Casino eliminado", elemento.getNombre());
        elemento = new Casino();
        detalleElemento = new Casinosdetalles();
    }

    public void guardar() {
        elemento = sessionBean.marketingUserFacade.guardarCasinos(elemento);
        detalleElemento.setIdCasino(elemento.getIdCasino());
        detalleElemento = sessionBean.marketingUserFacade.guardarDetalleCasino(detalleElemento);
        lista = sessionBean.marketingUserFacade.findAllCasinos();
        FacesUtil.addInfoMessage("Casino guardado", elemento.getNombre());
        elemento = new Casino();
        detalleElemento = new Casinosdetalles();
    }

    public Casinosdetalles getDetalleElemento() {
        return detalleElemento;
    }

    public void setDetalleElemento(Casinosdetalles detalleElemento) {
        this.detalleElemento = detalleElemento;
    }

}
