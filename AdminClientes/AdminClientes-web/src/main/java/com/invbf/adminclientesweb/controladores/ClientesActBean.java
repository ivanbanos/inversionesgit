/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.controladores;

import com.invbf.adminclientesapi.entity.Atributo;
import com.invbf.adminclientesapi.entity.Casino;
import com.invbf.adminclientesapi.entity.Categoria;
import com.invbf.adminclientesapi.entity.Cliente;
import com.invbf.adminclientesapi.entity.Clienteatributo;
import com.invbf.adminclientesapi.entity.ClientesatributosPK;
import com.invbf.adminclientesapi.entity.TipoJuego;
import com.invbf.adminclientesapi.facade.MarketingUserFacade;
import com.invbf.adminclientesweb.util.FacesUtil;
import java.io.IOException;
import java.util.ArrayList;
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
    private List<TipoJuego> tiposjuegos;
    private List<Atributo> atributos;
    private Cliente elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private DualListModel<TipoJuego> tiposJuegosTodos;
    private List<Casino> listacasinos;
    private List<Categoria> listacategorias;

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
        sessionBean.setActive("clientes");
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
        if ((Integer) sessionBean.getAttributes().get("idCliente") != 0) {
            elemento = marketingUserFacade.findCliente((Integer) sessionBean.getAttributes().get("idCliente"));
            tiposjuegos = marketingUserFacade.findAllTiposjuegos();
            for (TipoJuego tj : elemento.getTiposjuegosList()) {
                if (tiposjuegos.contains(tj)) {
                    tiposjuegos.remove(tj);
                }
            }
            tiposJuegosTodos = new DualListModel<TipoJuego>(tiposjuegos, elemento.getTiposjuegosList());
        } else {
            elemento = new Cliente();
            elemento.setIdCliente(0);
            elemento.setIdCategorias(new Categoria(0));
            elemento.setIdCasinoPreferencial(new Casino(0));
            elemento.setTiposjuegosList(new ArrayList<TipoJuego>());
            elemento.setClientesatributosList(new ArrayList<Clienteatributo>());
            tiposjuegos = marketingUserFacade.findAllTiposjuegos();
            tiposJuegosTodos = new DualListModel<TipoJuego>(tiposjuegos, elemento.getTiposjuegosList());
        }
        atributos = marketingUserFacade.findAllAtributos();
        for (Atributo a : atributos) {
            Clienteatributo clientesatributos = new Clienteatributo(elemento.getIdCliente(), a.getIdAtributo());
            if (!elemento.getClientesatributosList().contains(clientesatributos)) {
                clientesatributos.setClientesatributosPK(new ClientesatributosPK(elemento.getIdCliente(), a.getIdAtributo()));
                clientesatributos.setAtributos(a);
                clientesatributos.setClientes(elemento);
                elemento.getClientesatributosList().add(clientesatributos);
            }
        }
        listacasinos = marketingUserFacade.findAllCasinos();
        listacategorias = marketingUserFacade.findAllCategorias();
    }

    public Cliente getElemento() {
        return elemento;
    }

    public void setElemento(Cliente elemento) {
        this.elemento = elemento;
    }

    public MarketingUserFacade getMarketingUserFacade() {
        return marketingUserFacade;
    }

    public void setMarketingUserFacade(MarketingUserFacade marketingUserFacade) {
        this.marketingUserFacade = marketingUserFacade;
    }

    public List<Atributo> getAtributos() {
        return atributos;
    }

    public void setAtributos(List<Atributo> atributos) {
        this.atributos = atributos;
    }

    public DualListModel<TipoJuego> getTiposJuegosTodos() {
        return tiposJuegosTodos;
    }

    public void setTiposJuegosTodos(DualListModel<TipoJuego> tiposJuegosTodos) {
        this.tiposJuegosTodos = tiposJuegosTodos;
    }

    public void guardar() {
        guardar:
        {
            try {
                if (elemento.getIdCliente() == 0) {
                    elemento.setIdCliente(null);
                }
                if (elemento.getIdentificacion() == null || elemento.getIdentificacion().equals("")) {
                    elemento.setTipoident("");
                }
                if ((elemento.getTipoident() == null || elemento.getTipoident().equals(""))
                        &&(elemento.getIdentificacion() != null && !elemento.getIdentificacion().equals(""))) {
                    FacesUtil.addErrorMessage("No se puede guardar cliente","Si tiene identificación debe seleccionar un tipo");
                }
                elemento.setTiposjuegosList(tiposJuegosTodos.getTarget());
                List<Clienteatributo> clienteatributos = elemento.getClientesatributosList();
                elemento.setClientesatributosList(new ArrayList<Clienteatributo>());
                elemento = marketingUserFacade.guardarClientes(elemento);
                for (Clienteatributo clienteatributo : clienteatributos) {
                    clienteatributo.getClientesatributosPK().setIdCliente(elemento.getIdCliente());
                    clienteatributo.setClientes(elemento);
                    elemento.getClientesatributosList().add(clienteatributo);
                }
                elemento = marketingUserFacade.guardarClientes(elemento);
                FacesUtil.addInfoMessage("Cliente actualizado", elemento.toString());
                FacesContext.getCurrentInstance().getExternalContext().redirect("clientes.xhtml");
                sessionBean.registrarlog("actualizar", "Clientes", elemento.toString());
            } catch (IOException ex) {
                LOGGER.error(ex);
            }
        }
    }

    public List<Casino> getListacasinos() {
        return listacasinos;
    }

    public void setListacasinos(List<Casino> listacasinos) {
        this.listacasinos = listacasinos;
    }

    public List<Categoria> getListacategorias() {
        return listacategorias;
    }

    public void setListacategorias(List<Categoria> listacategorias) {
        this.listacategorias = listacategorias;
    }
}
