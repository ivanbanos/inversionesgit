/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.controladores;

import com.invbf.adminclientesapi.entity.Atributos;
import com.invbf.adminclientesapi.entity.Casinos;
import com.invbf.adminclientesapi.entity.Categorias;
import com.invbf.adminclientesapi.entity.Clientes;
import com.invbf.adminclientesapi.entity.Clientesatributos;
import com.invbf.adminclientesapi.entity.Tiposjuegos;
import com.invbf.adminclientesapi.facade.MarketingUserFacade;
import com.invbf.adminclientesweb.util.FacesUtil;
import java.io.IOException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;
import org.primefaces.model.DualListModel;

/**
 *
 * @author ideacentre
 */
@ManagedBean
@ViewScoped
public class ClientesActBean {

    private static final Logger LOGGER =
            Logger.getLogger(SessionBean.class);
    @EJB
    MarketingUserFacade marketingUserFacade;
    private List<Tiposjuegos> tiposjuegos;
    private List<Atributos> atributos;
    private Clientes elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private DualListModel<Tiposjuegos> tiposJuegosTodos;
    private List<Casinos> listacasinos;
    private List<Categorias> listacategorias;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public ClientesActBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        if (!sessionBean.perfilViewMatch("Clientes")) {
            try {
                System.out.println("No lo coje");
                sessionBean.Desconectar();
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
                LOGGER.error(ex);
            }
        }

        if (sessionBean.getAttributes() == null || !sessionBean.getAttributes().containsKey("idCliente")) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("AdministradorAtributosMarketing.xhtml");
            } catch (IOException ex) {
                LOGGER.error(ex);
            }
        }
        elemento = marketingUserFacade.findCliente((Integer) sessionBean.getAttributes().get("idCliente"));
        tiposjuegos = marketingUserFacade.findAllTiposjuegos();
        atributos = marketingUserFacade.findAllAtributos();
        for (Tiposjuegos tj : elemento.getTiposjuegosList()) {
            if (tiposjuegos.contains(tj)) {
                tiposjuegos.remove(tj);
            }
        }
        for (Atributos a : atributos) {
            Clientesatributos clientesatributos = new Clientesatributos(elemento.getIdCliente(), a.getIdAtributo());
            if(!elemento.getClientesatributosList().contains(clientesatributos)){
                clientesatributos.setAtributos(a);
                clientesatributos.setClientes(elemento);
                elemento.getClientesatributosList().add(clientesatributos);
            }
        }
        tiposJuegosTodos = new DualListModel<Tiposjuegos>(tiposjuegos, elemento.getTiposjuegosList());

        listacasinos = marketingUserFacade.findAllCasinos();
        listacategorias = marketingUserFacade.findAllCategorias();
    }

    public Clientes getElemento() {
        return elemento;
    }

    public void setElemento(Clientes elemento) {
        this.elemento = elemento;
    }

    public MarketingUserFacade getMarketingUserFacade() {
        return marketingUserFacade;
    }

    public void setMarketingUserFacade(MarketingUserFacade marketingUserFacade) {
        this.marketingUserFacade = marketingUserFacade;
    }

    public List<Atributos> getAtributos() {
        return atributos;
    }

    public void setAtributos(List<Atributos> atributos) {
        this.atributos = atributos;
    }

    public DualListModel<Tiposjuegos> getTiposJuegosTodos() {
        return tiposJuegosTodos;
    }

    public void setTiposJuegosTodos(DualListModel<Tiposjuegos> tiposJuegosTodos) {
        this.tiposJuegosTodos = tiposJuegosTodos;
    }

    public void guardar() {
        try {
            elemento.setTiposjuegosList(tiposJuegosTodos.getTarget());
            marketingUserFacade.guardarClientes(elemento);
            sessionBean.getAttributes().remove("idCliente");
            FacesUtil.addInfoMessage("Cliente actualizado", elemento.toString());
            FacesContext.getCurrentInstance().getExternalContext().redirect("clientes.xhtml");
            sessionBean.registrarlog("actualizar", "Clientes", elemento.toString());
        } catch (IOException ex) {
            LOGGER.error(ex);
        }
    }

    public List<Casinos> getListacasinos() {
        return listacasinos;
    }

    public void setListacasinos(List<Casinos> listacasinos) {
        this.listacasinos = listacasinos;
    }

    public List<Categorias> getListacategorias() {
        return listacategorias;
    }

    public void setListacategorias(List<Categorias> listacategorias) {
        this.listacategorias = listacategorias;
    }
}
