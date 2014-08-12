/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.controladores;

import com.invbf.adminclientesapi.entity.Atributos;
import com.invbf.adminclientesapi.entity.Casinos;
import com.invbf.adminclientesapi.entity.Categorias;
import com.invbf.adminclientesapi.entity.Clientes;
import com.invbf.adminclientesapi.entity.Eventos;
import com.invbf.adminclientesapi.entity.Tiposjuegos;
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
import org.primefaces.model.UploadedFile;

/**
 *
 * @author ideacentre
 */
@ManagedBean
@ViewScoped
public class CrudClientesBean {

    private static final Logger LOGGER =
            Logger.getLogger(SessionBean.class);
    @EJB
    MarketingUserFacade marketingUserFacade;
    private List<Clientes> lista;
    private Clientes elemento;
    private List<Casinos> listacasinos;
    private List<Atributos> listaatributos;
    private List<Categorias> listacategorias;
    private List<Tiposjuegos> listatiposjuegos;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    private List<Clientes> flista;

    public List<Clientes> getFlista() {
        return flista;
    }

    public void setFlista(List<Clientes> flista) {
        this.flista = flista;
    }
    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public CrudClientesBean() {
    }

    @PostConstruct
    public void init() {
        if(!sessionBean.perfilViewMatch("CrudClientesView")){
            try {
                sessionBean.Desconectar();
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
                LOGGER.error(ex);
            }
        }
        elemento = new Clientes();
        lista = marketingUserFacade.findAllClientes();
        listacasinos = marketingUserFacade.findAllCasinos();
        listaatributos = marketingUserFacade.findAllAtributos();
        listacategorias = marketingUserFacade.findAllCategorias();
        listatiposjuegos = marketingUserFacade.findAllTiposjuegos();
    }

    public List<Clientes> getLista() {
        return lista;
    }

    public void setLista(List<Clientes> lista) {
        this.lista = lista;
    }

    public Clientes getElemento() {
        return elemento;
    }

    public void setElemento(Clientes elemento) {
        this.elemento = elemento;
    }

    public List<Casinos> getListacasinos() {
        return listacasinos;
    }

    public void setListacasinos(List<Casinos> listacasinos) {
        this.listacasinos = listacasinos;
    }

    public MarketingUserFacade getMarketingUserFacade() {
        return marketingUserFacade;
    }

    public List<Atributos> getListaatributos() {
        return listaatributos;
    }

    public void setListaatributos(List<Atributos> listaatributos) {
        this.listaatributos = listaatributos;
    }

    public List<Categorias> getListacategorias() {
        return listacategorias;
    }

    public void setListacategorias(List<Categorias> listacategorias) {
        this.listacategorias = listacategorias;
    }

    public List<Tiposjuegos> getListatiposjuegos() {
        return listatiposjuegos;
    }

    public void setListatiposjuegos(List<Tiposjuegos> listatiposjuegos) {
        this.listatiposjuegos = listatiposjuegos;
    }


    public void setMarketingUserFacade(MarketingUserFacade marketingUserFacade) {
        this.marketingUserFacade = marketingUserFacade;
    }

    
    public void delete(){
        marketingUserFacade.deleteClientes(elemento);
        lista = marketingUserFacade.findAllClientes();
        elemento = new Clientes();
    }
    
    public void guardar(){
        marketingUserFacade.guardarClientes(elemento);
        lista = marketingUserFacade.findAllClientes();
        elemento = new Clientes();
    }
    
}
