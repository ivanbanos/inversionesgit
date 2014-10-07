/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.controladores;

import com.invbf.sistemagestionclientes.entity.Atributo;
import com.invbf.sistemagestionclientes.entity.Casino;
import com.invbf.sistemagestionclientes.entity.Categoria;
import com.invbf.sistemagestionclientes.entity.Cliente;
import com.invbf.sistemagestionclientes.entity.Clienteatributo;
import com.invbf.sistemagestionclientes.entity.ClientesatributosPK;
import com.invbf.sistemagestionclientes.entity.TipoDocumento;
import com.invbf.sistemagestionclientes.entity.TipoJuego;
import com.invbf.sistemagestionclientes.facade.MarketingUserFacade;
import com.invbf.sistemagestionclientes.facade.impl.MarketingUserFacadeImpl;
import com.invbf.sistemagestionclientes.util.FacesUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.DualListModel;

/**
 *
 * @author ideacentre
 */
@ManagedBean
@ViewScoped
public class ClientesActBean {

    MarketingUserFacade marketingUserFacade;
    private List<TipoJuego> tiposjuegos;
    private List<Atributo> atributos;
    private Cliente elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private DualListModel<TipoJuego> tiposJuegosTodos;
    private List<Casino> listacasinos;
    private List<Categoria> listacategorias;
    private List<TipoDocumento> tipoDocumentos;

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
        marketingUserFacade = new MarketingUserFacadeImpl();
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("clientes");
        if (!sessionBean.perfilViewMatch("Clientes")) {
            try {
                System.out.println("No lo coje");
                sessionBean.Desconectar();
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        }

        if (sessionBean.getAttributes() == null || !sessionBean.getAttributes().containsKey("idCliente")) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("AdministradorAtributosMarketing.xhtml");
            } catch (IOException ex) {
            }
        }
        if ((Integer) sessionBean.getAttributes().get("idCliente") != 0) {
            elemento = marketingUserFacade.findCliente((Integer) sessionBean.getAttributes().get("idCliente"));
            if (elemento.getIdTipoDocumento() == null) {
                elemento.setIdTipoDocumento(new TipoDocumento(0));
            }
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
            elemento.setIdTipoDocumento(new TipoDocumento(0));
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
        tipoDocumentos = marketingUserFacade.findAllTipoDocumentos();
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
                    elemento.setIdTipoDocumento(null);
                }
                if ((elemento.getIdTipoDocumento() == null)
                        && (elemento.getIdentificacion() != null && !elemento.getIdentificacion().equals(""))) {
                    FacesUtil.addErrorMessage("No se puede guardar cliente", "Si tiene identificación debe seleccionar un tipo");
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

    public List<TipoDocumento> getTipoDocumentos() {
        return tipoDocumentos;
    }

    public void setTipoDocumentos(List<TipoDocumento> tipoDocumentos) {
        this.tipoDocumentos = tipoDocumentos;
    }
}
