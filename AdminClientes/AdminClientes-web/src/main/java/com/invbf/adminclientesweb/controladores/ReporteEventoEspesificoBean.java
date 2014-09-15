/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.controladores;

import com.invbf.adminclientesapi.entity.Cliente;
import com.invbf.adminclientesapi.entity.Clienteevento;
import com.invbf.adminclientesapi.entity.Estadocliente;
import com.invbf.adminclientesapi.entity.Evento;
import com.invbf.adminclientesapi.entity.Usuario;
import com.invbf.adminclientesapi.facade.AdminFacade;
import com.invbf.adminclientesapi.facade.MarketingUserFacade;
import com.invbf.adminclientesapi.facade.SystemFacade;
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
public class ReporteEventoEspesificoBean {

    private static final Logger LOGGER =
            Logger.getLogger(SessionBean.class);
    @EJB
    MarketingUserFacade marketingUserFacade;
    @EJB
    SystemFacade systemFacade;
    @EJB
    AdminFacade adminFacade;
    private Evento elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private List<Cliente> clienteses;
    private List<Cliente> clientesesEvento;
    private List<Usuario> usuarioses;
    private DualListModel<Cliente> todosclienteses;
    private DualListModel<Usuario> todosusuarioses;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }
    
    private List<Clienteevento> flista;

    public List<Clienteevento> getFlista() {
        return flista;
    }

    public void setFlista(List<Clienteevento> flista) {
        this.flista = flista;
    }

    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public ReporteEventoEspesificoBean() {
    }

    @PostConstruct
    public void init() {
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

        if (sessionBean.getAttributes() == null || !sessionBean.getAttributes().containsKey("idEvento")) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("eventos.xhtml");
            } catch (IOException ex) {
                LOGGER.error(ex);
            }
        }
        elemento = marketingUserFacade.findEvento((Integer) sessionBean.getAttributes().get("idEvento"));

        clienteses = marketingUserFacade.findAllClientes();
        usuarioses = adminFacade.findAllUsuariosHostess();
        clientesesEvento = new ArrayList<Cliente>();
        for (Clienteevento lce : elemento.getListasclienteseventoList()) {
            clientesesEvento.add(lce.getClientes());
            if (clienteses.contains(lce.getClientes())) {
                clienteses.remove(lce.getClientes());
            }
        }
        for (Usuario u : elemento.getUsuariosList()) {
            if (usuarioses.contains(u)) {
                usuarioses.remove(u);
            }
        }
        todosclienteses = new DualListModel<Cliente>(clienteses, clientesesEvento);
        todosusuarioses = new DualListModel<Usuario>(usuarioses, elemento.getUsuariosList());
    }

    public Evento getElemento() {
        return elemento;
    }

    public void setElemento(Evento elemento) {
        this.elemento = elemento;
    }

    public MarketingUserFacade getMarketingUserFacade() {
        return marketingUserFacade;
    }

    public void setMarketingUserFacade(MarketingUserFacade marketingUserFacade) {
        this.marketingUserFacade = marketingUserFacade;
    }

    public void guardar() {
        Estadocliente estadoscliente = marketingUserFacade.findByNombreEstadoCliente("Inicial");
        elemento.setUsuariosList(todosusuarioses.getTarget());
        for (Usuario s : todosusuarioses.getTarget()) {
            adminFacade.agregarEventoUsuarios(s, elemento);
        }
        ArrayList<Clienteevento> al = new ArrayList<Clienteevento>(elemento.getListasclienteseventoList());
        elemento.getListasclienteseventoList().clear();
        for (Cliente c : todosclienteses.getTarget()) {

            Clienteevento listasclientesevento = new Clienteevento(elemento.getIdEvento(), c.getIdCliente());
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

        clienteses = marketingUserFacade.findAllClientes();
        usuarioses = adminFacade.findAllUsuariosHostess();
        clientesesEvento = new ArrayList<Cliente>();
        for (Clienteevento lce : elemento.getListasclienteseventoList()) {
            clientesesEvento.add(lce.getClientes());
            if (clienteses.contains(lce.getClientes())) {
                clienteses.remove(lce.getClientes());
            }
        }
        for (Usuario u : elemento.getUsuariosList()) {
            if (usuarioses.contains(u)) {
                usuarioses.remove(u);
            }
        }
        todosclienteses = new DualListModel<Cliente>(clienteses, clientesesEvento);
        todosusuarioses = new DualListModel<Usuario>(usuarioses, elemento.getUsuariosList());

        FacesUtil.addInfoMessage("Evento guardado con exito", elemento.getNombre());
    }

    public AdminFacade getAdminFacade() {
        return adminFacade;
    }

    public void setAdminFacade(AdminFacade adminFacade) {
        this.adminFacade = adminFacade;
    }

    public DualListModel<Cliente> getTodosclienteses() {
        return todosclienteses;
    }

    public void setTodosclienteses(DualListModel<Cliente> todosclienteses) {
        this.todosclienteses = todosclienteses;
    }

    public DualListModel<Usuario> getTodosusuarioses() {
        return todosusuarioses;
    }

    public void setTodosusuarioses(DualListModel<Usuario> todosusuarioses) {
        this.todosusuarioses = todosusuarioses;
    }
}
