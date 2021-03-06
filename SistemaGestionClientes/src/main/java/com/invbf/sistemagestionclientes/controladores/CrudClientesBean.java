/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.controladores;

import com.invbf.sistemagestionclientes.entity.Atributo;
import com.invbf.sistemagestionclientes.entity.Casino;
import com.invbf.sistemagestionclientes.entity.Categoria;
import com.invbf.sistemagestionclientes.entity.Cliente;
import com.invbf.sistemagestionclientes.entity.Permiso;
import com.invbf.sistemagestionclientes.entity.TipoJuego;
import com.invbf.sistemagestionclientes.util.FacesUtil;
import java.io.IOException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author ideacentre
 */
@ManagedBean
@ViewScoped
public class CrudClientesBean {

    private List<Cliente> lista;
    private Cliente elemento;
    private List<Casino> listacasinos;
    private List<Atributo> listaatributos;
    private List<Categoria> listacategorias;
    private List<TipoJuego> listatiposjuegos;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private boolean editar;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }
    private List<Cliente> flista;

    public List<Cliente> getFlista() {
        return flista;
    }

    public void setFlista(List<Cliente> flista) {
        this.flista = flista;
    }

    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public CrudClientesBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("clientes");
        if (!sessionBean.perfilViewMatch("Clientes")) {
            try {
                sessionBean.Desconectar();
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        }
        elemento = new Cliente();
        lista = sessionBean.marketingUserFacade.findAllClientes();
        listacasinos = sessionBean.marketingUserFacade.findAllCasinos();
        listaatributos = sessionBean.marketingUserFacade.findAllAtributos();
        listacategorias = sessionBean.marketingUserFacade.findAllCategorias();
        listatiposjuegos = sessionBean.marketingUserFacade.findAllTiposjuegos();
        editar = false;
    }

    public List<Cliente> getLista() {
        return lista;
    }

    public void setLista(List<Cliente> lista) {
        this.lista = lista;
    }

    public Cliente getElemento() {
        return elemento;
    }

    public void setElemento(Cliente elemento) {
        this.elemento = elemento;
    }

    public List<Casino> getListacasinos() {
        return listacasinos;
    }

    public void setListacasinos(List<Casino> listacasinos) {
        this.listacasinos = listacasinos;
    }

    public List<Atributo> getListaatributos() {
        return listaatributos;
    }

    public void setListaatributos(List<Atributo> listaatributos) {
        this.listaatributos = listaatributos;
    }

    public List<Categoria> getListacategorias() {
        return listacategorias;
    }

    public void setListacategorias(List<Categoria> listacategorias) {
        this.listacategorias = listacategorias;
    }

    public List<TipoJuego> getListatiposjuegos() {
        return listatiposjuegos;
    }

    public void setListatiposjuegos(List<TipoJuego> listatiposjuegos) {
        this.listatiposjuegos = listatiposjuegos;
    }

    public void delete() {
        sessionBean.managerUserFacade.addPermiso(new Permiso("ELIMINAR", elemento.getIdCliente().toString(), "CLIENTE", "", "", "", "", "", ""));
        lista = sessionBean.marketingUserFacade.findAllClientes();
        sessionBean.registrarlog("eliminar", "Clientes", "Cleinte enviado a eliminar:"+elemento.toString());
        FacesUtil.addInfoMessage("Eliminación enviada", "Pendiente de autorización");
        elemento = new Cliente();
    }

    public void goCliente(int id) {
        try {
            sessionBean.getAttributes().put("idCliente", new Integer(id));
            FacesContext.getCurrentInstance().getExternalContext().redirect("ClientesAct.xhtml");
        } catch (IOException ex) {
        }
    }

    public boolean isEditar() {
        return editar;
    }

    public void setEditar(boolean editar) {
        this.editar = editar;
    }
}
