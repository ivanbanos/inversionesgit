/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.controladores;

import com.invbf.adminclientesapi.Casinos;
import com.invbf.adminclientesapi.Categorias;
import com.invbf.adminclientesapi.Eventos;
import com.invbf.adminclientesapi.facade.MarketingUserFacade;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author ideacentre
 */
@ManagedBean
@ViewScoped
public class CrudEventoBean {

    private static final Logger LOGGER =
            Logger.getLogger(SessionBean.class);
    @EJB
    MarketingUserFacade marketingUserFacade;
    private List<Eventos> lista;
    private Eventos elemento;
    private List<Casinos> listacasinos;
    private UploadedFile file;

    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public CrudEventoBean() {
    }

    @PostConstruct
    public void init() {
        elemento = new Eventos();
        lista = marketingUserFacade.findAllEventos();
        listacasinos = marketingUserFacade.findAllCasinos();
    }

    public List<Eventos> getLista() {
        return lista;
    }

    public void setLista(List<Eventos> lista) {
        this.lista = lista;
    }

    public Eventos getElemento() {
        return elemento;
    }

    public void setElemento(Eventos elemento) {
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

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void setMarketingUserFacade(MarketingUserFacade marketingUserFacade) {
        this.marketingUserFacade = marketingUserFacade;
    }

    
    public void delete(){
        marketingUserFacade.deleteEventos(elemento);
        lista = marketingUserFacade.findAllEventos();
        elemento = new Eventos();
    }
    
    public void guardar(){
        if(file != null) {
            elemento.setImagen(file.getContents());
            elemento.setFormatoImagen(file.getContentType());
        }
        marketingUserFacade.guardarEventos(elemento);
        lista = marketingUserFacade.findAllEventos();
        elemento = new Eventos();
    }
    
}
