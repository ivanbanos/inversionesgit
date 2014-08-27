/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.controladores;

import com.invbf.adminclientesapi.entity.Clientes;
import com.invbf.adminclientesapi.entity.Estadoscliente;
import com.invbf.adminclientesapi.entity.Eventos;
import com.invbf.adminclientesapi.entity.Listasclientesevento;
import com.invbf.adminclientesapi.entity.Usuarios;
import com.invbf.adminclientesapi.facade.AdminFacade;
import com.invbf.adminclientesapi.facade.MarketingUserFacade;
import com.invbf.adminclientesweb.util.FacesUtil;
import java.io.ByteArrayInputStream;
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
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author ideacentre
 */
@ManagedBean
@ViewScoped
public class MarketingEventoManejadorBean {

    private static final Logger LOGGER =
            Logger.getLogger(SessionBean.class);
    @EJB
    MarketingUserFacade marketingUserFacade;
    @EJB
    AdminFacade adminFacade;
    private Eventos elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private StreamedContent file;
    private List<Clientes> clienteses;
    private List<Clientes> clientesesEvento;
    private List<Usuarios> usuarioses;
    private DualListModel<Clientes> todosclienteses;
    private DualListModel<Usuarios> todosusuarioses;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public MarketingEventoManejadorBean() {
    }

    @PostConstruct
    public void init() {
        if (!sessionBean.perfilViewMatch("ManejadorEventosMarketing")) {
            try {
                sessionBean.Desconectar();
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
                LOGGER.error(ex);
            }
        }

        if (sessionBean.getAttributes() == null || !sessionBean.getAttributes().containsKey("idEvento")) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("eventos.xhtml");
            } catch (IOException ex) {
                LOGGER.error(ex);
            }
        }
        elemento = marketingUserFacade.findEvento((Integer) sessionBean.getAttributes().get("idEvento"));
        file = new DefaultStreamedContent();
        if (elemento.getImagen() != null) {
            file = new DefaultStreamedContent(new ByteArrayInputStream(elemento.getImagen()), "image/" + elemento.getFormatoImagen());
        }
        clienteses = marketingUserFacade.findAllClientes();
        usuarioses = adminFacade.findAllUsuariosHostess();
        clientesesEvento = new ArrayList<Clientes>();
        for (Listasclientesevento lce : elemento.getListasclienteseventoList()) {
            clientesesEvento.add(lce.getClientes());
            if (clienteses.contains(lce.getClientes())) {
                clienteses.remove(lce.getClientes());
            }
        }
        for (Usuarios u : elemento.getUsuariosList()) {
            if (usuarioses.contains(u)) {
                usuarioses.remove(u);
            }
        }
        todosclienteses = new DualListModel<Clientes>(clienteses, clientesesEvento);
        todosusuarioses = new DualListModel<Usuarios>(usuarioses, elemento.getUsuariosList());
    }

    public Eventos getElemento() {
        return elemento;
    }

    public void setElemento(Eventos elemento) {
        this.elemento = elemento;
    }

    public MarketingUserFacade getMarketingUserFacade() {
        return marketingUserFacade;
    }

    public void setMarketingUserFacade(MarketingUserFacade marketingUserFacade) {
        this.marketingUserFacade = marketingUserFacade;
    }

    public void guardar() {
        Estadoscliente estadoscliente = marketingUserFacade.findByNombreEstadoCliente("Inicial");
        elemento.setUsuariosList(todosusuarioses.getTarget());
        for(Usuarios s : todosusuarioses.getTarget()){
            adminFacade.agregarEventoUsuarios(s, elemento);
        }
        ArrayList<Listasclientesevento> al = new ArrayList<Listasclientesevento>(elemento.getListasclienteseventoList());
        elemento.getListasclienteseventoList().clear();
        for (Clientes c : todosclienteses.getTarget()) {

            Listasclientesevento listasclientesevento = new Listasclientesevento(elemento.getIdEvento(), c.getIdCliente());
            if (al.contains(listasclientesevento)) {
                elemento.getListasclienteseventoList().add(al.get(al.indexOf(listasclientesevento)));
            } else {
                listasclientesevento.setIdEstadoCliente(estadoscliente);
                listasclientesevento.setEventos(elemento);
                listasclientesevento.setClientes(c);
                elemento.getListasclienteseventoList().add(listasclientesevento);
            }
        }
        marketingUserFacade.guardarEventos(elemento);
        sessionBean.actualizarUsuario();
        elemento = marketingUserFacade.findEvento((Integer) sessionBean.getAttributes().get("idEvento"));
        file = new DefaultStreamedContent();
        if (elemento.getImagen() != null) {
            file = new DefaultStreamedContent(new ByteArrayInputStream(elemento.getImagen()), "image/" + elemento.getFormatoImagen());
        }
        clienteses = marketingUserFacade.findAllClientes();
        usuarioses = adminFacade.findAllUsuariosHostess();
        clientesesEvento = new ArrayList<Clientes>();
        for (Listasclientesevento lce : elemento.getListasclienteseventoList()) {
            clientesesEvento.add(lce.getClientes());
            if (clienteses.contains(lce.getClientes())) {
                clienteses.remove(lce.getClientes());
            }
        }
        for (Usuarios u : elemento.getUsuariosList()) {
            if (usuarioses.contains(u)) {
                usuarioses.remove(u);
            }
        }
        todosclienteses = new DualListModel<Clientes>(clienteses, clientesesEvento);
        todosusuarioses = new DualListModel<Usuarios>(usuarioses, elemento.getUsuariosList());
        
            FacesUtil.addInfoMessage("Evento guardado con exito",elemento.getNombre());
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public AdminFacade getAdminFacade() {
        return adminFacade;
    }

    public void setAdminFacade(AdminFacade adminFacade) {
        this.adminFacade = adminFacade;
    }

    public DualListModel<Clientes> getTodosclienteses() {
        return todosclienteses;
    }

    public void setTodosclienteses(DualListModel<Clientes> todosclienteses) {
        this.todosclienteses = todosclienteses;
    }

    public DualListModel<Usuarios> getTodosusuarioses() {
        return todosusuarioses;
    }

    public void setTodosusuarioses(DualListModel<Usuarios> todosusuarioses) {
        this.todosusuarioses = todosusuarioses;
    }
}
