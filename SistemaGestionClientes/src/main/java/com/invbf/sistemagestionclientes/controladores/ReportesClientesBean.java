/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.controladores;

import com.invbf.sistemagestionclientes.entity.Atributo;
import com.invbf.sistemagestionclientes.entity.Casino;
import com.invbf.sistemagestionclientes.entity.Categoria;
import com.invbf.sistemagestionclientes.entity.Cliente;
import com.invbf.sistemagestionclientes.entity.TipoJuego;
import com.invbf.sistemagestionclientes.facade.MarketingUserFacade;
import com.invbf.sistemagestionclientes.facade.impl.MarketingUserFacadeImpl;
import com.invbf.sistemagestionclientes.util.CasinoBoolean;
import com.invbf.sistemagestionclientes.util.CategoriaBoolean;
import com.invbf.sistemagestionclientes.util.FacesUtil;
import com.invbf.sistemagestionclientes.util.TipoJuegoBoolean;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
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
public class ReportesClientesBean {
    private List<Cliente> lista;
    private Cliente elemento;
    private List<Casino> listacasinos;
    private List<Atributo> listaatributos;
    private List<Categoria> listacategorias;
    private List<TipoJuego> listatiposjuegos;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private boolean editar;
    private String pais;
    private String ciudad;
    private List<CasinoBoolean> casinoBooleans;
    private List<TipoJuegoBoolean> juegoBooleans;
    private List<CategoriaBoolean> categoriaBooleans;
    private boolean todoscasinos;
    private boolean todosCat;
    private boolean todostip;

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
        pais = "";
        ciudad = "";
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("reportes");
        if (!sessionBean.perfilViewMatch("Reportes")) {
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

        List<Casino> casinos = sessionBean.marketingUserFacade.findAllCasinos();
        List<TipoJuego> tipoJuegos = sessionBean.marketingUserFacade.findAllTiposjuegos();
        List<Categoria> categorias = sessionBean.marketingUserFacade.findAllCategorias();
        juegoBooleans = new ArrayList<TipoJuegoBoolean>();
        casinoBooleans = new ArrayList<CasinoBoolean>();
        categoriaBooleans = new ArrayList<CategoriaBoolean>();
        for (TipoJuego tipoJuego : tipoJuegos) {
            juegoBooleans.add(new TipoJuegoBoolean(tipoJuego, false));
        }
        for (Casino casinob : casinos) {
            casinoBooleans.add(new CasinoBoolean(casinob, false));
        }
        for (Categoria categoria : categorias) {
            categoriaBooleans.add(new CategoriaBoolean(categoria, false));
        }
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
        sessionBean.marketingUserFacade.deleteClientes(elemento);
        lista = sessionBean.marketingUserFacade.findAllClientes();
        sessionBean.registrarlog("eliminar", "Clientes", elemento.toString());
        FacesUtil.addInfoMessage("Cliente eliminado", elemento.toString());
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

    public void busquedaAvanzada() {
        lista = sessionBean.marketingUserFacade.findAllClientes();

        boolean noCatselected = true;
        boolean noTipselected = true;
        boolean noCasselected = true;
        for (CasinoBoolean cb : casinoBooleans) {
            if (todoscasinos) {
                cb.setSelected(true);
                continue;
            }
            if (cb.isSelected()) {
                noCasselected = false;
                break;
            }
        }
        for (CategoriaBoolean cb : categoriaBooleans) {
            if (todosCat) {
                cb.setSelected(true);
                continue;
            }
            if (cb.isSelected()) {
                noCatselected = false;
                break;
            }
        }
        for (TipoJuegoBoolean tjb : juegoBooleans) {
            if (todostip) {
                tjb.setSelected(true);
                continue;
            }
            if (tjb.isSelected()) {
                noTipselected = false;
                break;
            }
        }

        for (Iterator<Cliente> it = lista.iterator(); it.hasNext();) {
            Cliente cliente = it.next();

            boolean siCategoria = false;
            boolean siTipoJuego = false;
            boolean siCasino = false;
            if (noCasselected) {
                siCasino = true;
            } else {
                for (CasinoBoolean cb : casinoBooleans) {
                    if (cb.isSelected()) {
                        if (cliente.getIdCasinoPreferencial().equals(cb.getCasino())) {
                            siCasino = true;
                            break;
                        }
                    }
                }
            }
            if (noCatselected) {
                siCategoria = true;
            } else {
                for (CategoriaBoolean cb : categoriaBooleans) {
                    if (cb.isSelected()) {
                        if (cliente.getIdCategorias().equals(cb.getCategoria())) {
                            siCategoria = true;
                            break;
                        }
                    }
                }
            }
            if (noTipselected) {
                siTipoJuego = true;
            } else {
                for (TipoJuegoBoolean tjb : juegoBooleans) {
                    if (tjb.isSelected()) {
                        for (TipoJuego tj : cliente.getTiposjuegosList()) {
                            if (tj.equals(tjb.getTipoJuego())) {
                                siTipoJuego = true;
                                break;
                            }
                        }
                    }
                }
            }

            if (!siCategoria) {
                it.remove();
            }
            if (!siTipoJuego) {
                it.remove();
            }
            if (!siCasino) {
                it.remove();
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

        }
        FacesUtil.addInfoMessage("Clientes filtrados!");
    }

    public List<CasinoBoolean> getCasinoBooleans() {
        return casinoBooleans;
    }

    public void setCasinoBooleans(List<CasinoBoolean> casinoBooleans) {
        this.casinoBooleans = casinoBooleans;
    }

    public List<TipoJuegoBoolean> getJuegoBooleans() {
        return juegoBooleans;
    }

    public void setJuegoBooleans(List<TipoJuegoBoolean> juegoBooleans) {
        this.juegoBooleans = juegoBooleans;
    }

    public boolean isTodoscasinos() {
        return todoscasinos;
    }

    public void setTodoscasinos(boolean todoscasinos) {
        this.todoscasinos = todoscasinos;
    }

    public boolean isTodostip() {
        return todostip;
    }

    public void setTodostip(boolean todostip) {
        this.todostip = todostip;
    }

    public List<CategoriaBoolean> getCategoriaBooleans() {
        return categoriaBooleans;
    }

    public void setCategoriaBooleans(List<CategoriaBoolean> categoriaBooleans) {
        this.categoriaBooleans = categoriaBooleans;
    }

    public boolean isTodosCat() {
        return todosCat;
    }

    public void setTodosCat(boolean todosCat) {
        this.todosCat = todosCat;
    }
}
