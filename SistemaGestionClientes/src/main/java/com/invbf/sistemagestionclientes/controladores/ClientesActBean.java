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
import com.invbf.sistemagestionclientes.entity.Permiso;
import com.invbf.sistemagestionclientes.entity.TipoDocumento;
import com.invbf.sistemagestionclientes.entity.TipoJuego;
import com.invbf.sistemagestionclientes.util.FacesUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    private List<TipoJuego> tiposjuegos;
    private List<Atributo> atributos;
    private Cliente elemento;
    private Cliente viejo;
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
            elemento = sessionBean.marketingUserFacade.findCliente((Integer) sessionBean.getAttributes().get("idCliente"));
            viejo = sessionBean.marketingUserFacade.findCliente((Integer) sessionBean.getAttributes().get("idCliente"));
            if (elemento.getIdTipoDocumento() == null) {
                elemento.setIdTipoDocumento(new TipoDocumento(0));
            }
            tiposjuegos = sessionBean.marketingUserFacade.findAllTiposjuegos();
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
            tiposjuegos = sessionBean.marketingUserFacade.findAllTiposjuegos();
            tiposJuegosTodos = new DualListModel<TipoJuego>(tiposjuegos, elemento.getTiposjuegosList());
        }
        atributos = sessionBean.marketingUserFacade.findAllAtributos();
        for (Atributo a : atributos) {
            Clienteatributo clientesatributos = new Clienteatributo(elemento.getIdCliente(), a.getIdAtributo());
            if (!elemento.getClientesatributosList().contains(clientesatributos)) {
                clientesatributos.setClientesatributosPK(new ClientesatributosPK(elemento.getIdCliente(), a.getIdAtributo()));
                clientesatributos.setAtributos(a);
                clientesatributos.setClientes(elemento);
                elemento.getClientesatributosList().add(clientesatributos);
            }
        }
        listacasinos = sessionBean.marketingUserFacade.findAllCasinos();
        listacategorias = sessionBean.marketingUserFacade.findAllCategorias();
        tipoDocumentos = sessionBean.marketingUserFacade.findAllTipoDocumentos();
    }

    public Cliente getElemento() {
        return elemento;
    }

    public void setElemento(Cliente elemento) {
        this.elemento = elemento;
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

            if (!elemento.getNombres().equals(viejo.getNombres())) {
                sessionBean.managerUserFacade.addPermiso(new Permiso("EDITAR", elemento.getIdCliente().toString(), "CLIENTE", "nombres", elemento.getNombres()));
            }
            if (!elemento.getApellidos().equals(viejo.getApellidos())) {
                sessionBean.managerUserFacade.addPermiso(new Permiso("EDITAR", elemento.getIdCliente().toString(), "CLIENTE", "apellidos", elemento.getApellidos()));
            }
            if (elemento.getIdTipoDocumento() != null && !elemento.getIdCasinoPreferencial().equals(viejo.getIdCasinoPreferencial())) {
                sessionBean.managerUserFacade.addPermiso(new Permiso("EDITAR", elemento.getIdCliente().toString(), "CLIENTE", "idCasinoPreferencial", elemento.getIdCasinoPreferencial().getIdCasino().toString()));
            }
            if (elemento.getIdCategorias() != null && !elemento.getIdCategorias().equals(viejo.getIdCategorias())) {
                sessionBean.managerUserFacade.addPermiso(new Permiso("EDITAR", elemento.getIdCliente().toString(), "CLIENTE", "idCategorias", elemento.getIdCategorias().getIdCategorias().toString()));
            }
            if (!elemento.getTelefono1().equals(viejo.getTelefono1())) {
                sessionBean.managerUserFacade.addPermiso(new Permiso("EDITAR", elemento.getIdCliente().toString(), "CLIENTE", "telefono1", elemento.getTelefono1()));
            }
            if (!elemento.getTelefono2().equals(viejo.getTelefono2())) {
                sessionBean.managerUserFacade.addPermiso(new Permiso("EDITAR", elemento.getIdCliente().toString(), "CLIENTE", "telefono2", elemento.getTelefono2()));
            }
            if (!elemento.getIdentificacion().equals(viejo.getIdentificacion())) {
                sessionBean.managerUserFacade.addPermiso(new Permiso("EDITAR", elemento.getIdCliente().toString(), "CLIENTE", "identificacion", elemento.getIdentificacion()));
            }
            if (!elemento.getCorreo().equals(viejo.getCorreo())) {
                sessionBean.managerUserFacade.addPermiso(new Permiso("EDITAR", elemento.getIdCliente().toString(), "CLIENTE", "correo", elemento.getCorreo()));
            }
            if (elemento.getCumpleanos() != null && !elemento.getCumpleanos().equals(viejo.getCumpleanos())) {
                sessionBean.managerUserFacade.addPermiso(new Permiso("EDITAR", elemento.getIdCliente().toString(), "CLIENTE", "cumpleanos", elemento.getCumpleanos().getTime() + ""));
            }
            if (!elemento.getPais().equals(viejo.getPais())) {
                sessionBean.managerUserFacade.addPermiso(new Permiso("EDITAR", elemento.getIdCliente().toString(), "CLIENTE", "pais", elemento.getPais()));
            }
            if (!elemento.getDireccion().equals(viejo.getDireccion())) {
                sessionBean.managerUserFacade.addPermiso(new Permiso("EDITAR", elemento.getIdCliente().toString(), "CLIENTE", "direccion", elemento.getDireccion()));
            }
            if (!elemento.getCiudad().equals(viejo.getCiudad())) {
                sessionBean.managerUserFacade.addPermiso(new Permiso("EDITAR", elemento.getIdCliente().toString(), "CLIENTE", "ciudad", elemento.getCiudad()));
            }
            if (!elemento.getBonoFidelizacion().equals(viejo.getBonoFidelizacion())) {
                sessionBean.managerUserFacade.addPermiso(new Permiso("EDITAR", elemento.getIdCliente().toString(), "CLIENTE", "bonoFidelizacion", elemento.getBonoFidelizacion()));
            }
            if (!elemento.getGenero().equals(viejo.getGenero())) {
                sessionBean.managerUserFacade.addPermiso(new Permiso("EDITAR", elemento.getIdCliente().toString(), "CLIENTE", "genero", elemento.getGenero()));
            }
            if (elemento.getIdTipoDocumento() != null && !elemento.getIdTipoDocumento().equals(viejo.getIdTipoDocumento())) {
                sessionBean.managerUserFacade.addPermiso(new Permiso("EDITAR", elemento.getIdCliente().toString(), "CLIENTE", "idTipoDocumento", elemento.getIdTipoDocumento().getIdTipoDocumento().toString()));
            }

            FacesUtil.addInfoMessage("Actualización enviada", "Pendiente de autorización");
            FacesContext.getCurrentInstance().getExternalContext().redirect("clientes.xhtml");
            sessionBean.registrarlog("actualizar", "Clientes", elemento.toString());
        } catch (IOException ex) {
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
