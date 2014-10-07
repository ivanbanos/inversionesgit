/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.controladores;


import com.invbf.sistemagestionclientes.entity.Cliente;
import com.invbf.sistemagestionclientes.entity.Evento;
import com.invbf.sistemagestionclientes.entity.Tarea;
import com.invbf.sistemagestionclientes.entity.Usuario;
import com.invbf.sistemagestionclientes.facade.AdminFacade;
import com.invbf.sistemagestionclientes.facade.MarketingUserFacade;
import com.invbf.sistemagestionclientes.facade.SystemFacade;
import com.invbf.sistemagestionclientes.facade.impl.AdminFacadeImpl;
import com.invbf.sistemagestionclientes.facade.impl.MarketingUserFacadeImpl;
import com.invbf.sistemagestionclientes.facade.impl.SystemFacadeImpl;
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
public class ReporteEventoEspesificoBean {
    
    MarketingUserFacade marketingUserFacade;
    SystemFacade systemFacade;
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
    private List<Evento> flista;

    public List<Evento> getFlista() {
        return flista;
    }

    public void setFlista(List<Evento> flista) {
        this.flista = flista;
    }

    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public ReporteEventoEspesificoBean() {
    }

    @PostConstruct
    public void init() {
        marketingUserFacade = new MarketingUserFacadeImpl();
        systemFacade = new SystemFacadeImpl();
        adminFacade = new AdminFacadeImpl();
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("reportes");
        if (!sessionBean.perfilViewMatch("Reportes")) {
            try {
                sessionBean.Desconectar();
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        }

        if (sessionBean.getAttributes() == null || !sessionBean.getAttributes().containsKey("idEvento")) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("eventos.xhtml");
            } catch (IOException ex) {
            }
        }
        elemento = marketingUserFacade.findEvento((Integer) sessionBean.getAttributes().get("idEvento"));
        for (Tarea t : elemento.getTareasList()) {
            if (!t.getEstado().equals("VENCIDO")) {
                sessionBean.checkEstadoTarea(t);
            }
        }
        clienteses = marketingUserFacade.findAllClientes();
        usuarioses = adminFacade.findAllUsuariosHostess();
        clientesesEvento = new ArrayList<Cliente>();
        todosclienteses = new DualListModel<Cliente>(clienteses, clientesesEvento);
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

    public void goTareaReporte(int id) {
        try {
            sessionBean.getAttributes().put("idTarea", new Integer(id));
            FacesContext.getCurrentInstance().getExternalContext().redirect("ReporteTareaEspesifica.xhtml");
        } catch (IOException ex) {
        }
    }
}
