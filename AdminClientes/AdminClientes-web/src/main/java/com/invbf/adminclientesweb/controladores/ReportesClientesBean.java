/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.controladores;

import com.invbf.adminclientesapi.entity.Atributo;
import com.invbf.adminclientesapi.entity.Casino;
import com.invbf.adminclientesapi.entity.Categoria;
import com.invbf.adminclientesapi.entity.Cliente;
import com.invbf.adminclientesapi.entity.TipoJuego;
import com.invbf.adminclientesapi.facade.MarketingUserFacade;
import com.invbf.adminclientesweb.util.FacesUtil;
import java.io.IOException;
import java.util.Iterator;
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
public class ReportesClientesBean {

    private static final Logger LOGGER =
            Logger.getLogger(SessionBean.class);
    @EJB
    MarketingUserFacade marketingUserFacade;
    private List<Cliente> lista;
    private Cliente elemento;
    private List<Casino> listacasinos;
    private List<Atributo> listaatributos;
    private List<Categoria> listacategorias;
    private List<TipoJuego> listatiposjuegos;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private boolean editar;
    private Casino casino;
    private String pais;
    private String ciudad;
    private String bono;
    private List<Casino> casinos;

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
    public ReportesClientesBean() {
    }

    @PostConstruct
    public void init() {
        casino = new Casino();
        pais = "";
        ciudad = "";
        bono = "";
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("reportes");
        if (!sessionBean.perfilViewMatch("Reportes")) {
            try {
                sessionBean.Desconectar();
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
                LOGGER.error(ex);
            }
        }
        elemento = new Cliente();
        lista = marketingUserFacade.findAllClientes();
        listacasinos = marketingUserFacade.findAllCasinos();
        listaatributos = marketingUserFacade.findAllAtributos();
        listacategorias = marketingUserFacade.findAllCategorias();
        listatiposjuegos = marketingUserFacade.findAllTiposjuegos();
        editar = false;
        casinos = marketingUserFacade.findAllCasinos();
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

    public MarketingUserFacade getMarketingUserFacade() {
        return marketingUserFacade;
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

    public void setMarketingUserFacade(MarketingUserFacade marketingUserFacade) {
        this.marketingUserFacade = marketingUserFacade;
    }

    public void delete() {
        marketingUserFacade.deleteClientes(elemento);
        lista = marketingUserFacade.findAllClientes();
        sessionBean.registrarlog("eliminar", "Clientes", elemento.toString());
        FacesUtil.addInfoMessage("Cliente eliminado", elemento.toString());
        elemento = new Cliente();
    }

    public void goCliente(int id) {
        try {
            sessionBean.getAttributes().put("idCliente", new Integer(id));
            FacesContext.getCurrentInstance().getExternalContext().redirect("ClientesAct.xhtml");
        } catch (IOException ex) {
            LOGGER.error(ex);
        }
    }

    public boolean isEditar() {
        return editar;
    }

    public void setEditar(boolean editar) {
        this.editar = editar;
    }

    public Casino getCasino() {
        return casino;
    }

    public void setCasino(Casino casino) {
        this.casino = casino;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getBono() {
        return bono;
    }

    public void setBono(String bono) {
        this.bono = bono;
    }

    public List<Casino> getCasinos() {
        return casinos;
    }

    public void setCasinos(List<Casino> casinos) {
        this.casinos = casinos;
    }

    public void busquedaAvanzada() {
        lista = marketingUserFacade.findAllClientes();

        for (Iterator<Cliente> it = lista.iterator(); it.hasNext();) {
            Cliente cliente = it.next();
            if (casino != null && casino.getIdCasino() != null && casino.getIdCasino() > 0) {
                if (!cliente.getIdCasinoPreferencial().equals(casino)) {
                    it.remove();
                }
            }
            if (ciudad != null && !ciudad.equals("")) {
                if (!cliente.getCiudad().contains(ciudad)) {
                    it.remove();
                }
            }
            if (pais != null && !pais.equals("")) {
                if (!cliente.getPais().contains(pais)) {
                    it.remove();
                }
            }
            if (bono != null && !bono.equals("")) {
                if (!cliente.getBonoFidelizacion().equals(bono)) {
                    it.remove();
                }
            }

        }
    }
}
