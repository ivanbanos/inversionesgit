/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.controladores;

import com.invbf.adminclientesapi.entity.Accion;
import com.invbf.adminclientesapi.entity.Listasclientestareas;
import com.invbf.adminclientesapi.entity.Tarea;
import com.invbf.adminclientesapi.exceptions.EventoSinClientesPorRevisarException;
import com.invbf.adminclientesapi.facade.AdminFacade;
import com.invbf.adminclientesapi.facade.HostessFacade;
import com.invbf.adminclientesapi.facade.MarketingUserFacade;
import com.invbf.adminclientesweb.util.FacesUtil;
import com.invbf.adminclientesweb.util.LCTPojo;
import java.io.IOException;
import java.util.ArrayList;
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
public class HostessTareaManejadorBean {

    private static final Logger LOGGER = Logger.getLogger(SessionBean.class);
    @EJB
    MarketingUserFacade marketingUserFacade;
    @EJB
    AdminFacade adminFacade;
    @EJB
    HostessFacade hostessFacade;
    private Tarea elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private List<LCTPojo> clientesPojo;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public HostessTareaManejadorBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("eventoshostess");
        if (!sessionBean.perfilViewMatch("ManejadorEventosHostess")) {
            try {
                sessionBean.Desconectar();
                FacesUtil.addErrorMessage("Session finalizada", "No tiene credenciales para ingresar a esa pantalla");
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");

            } catch (IOException ex) {
                LOGGER.error(ex);
            }
        }

        if (sessionBean.getAttributes() == null || !sessionBean.getAttributes().containsKey("idTarea")) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("tareasHostess.xhtml");
            } catch (IOException ex) {
                LOGGER.error(ex);
            }
        }
        elemento = marketingUserFacade.findTarea((Integer) sessionBean.getAttributes().get("idTarea"));
        try {
            List<Listasclientestareas> clientes = hostessFacade.findClienteTareaHostess((Integer) sessionBean.getAttributes().get("idTarea"));
            clientesPojo = new ArrayList<LCTPojo>();
            for (Iterator<Listasclientestareas> it = clientes.iterator(); it.hasNext();) {
                Listasclientestareas listasclientestareas = it.next();
                clientesPojo.add(new LCTPojo(listasclientestareas));
            }
        } catch (EventoSinClientesPorRevisarException ex) {
        }
    }

    public Tarea getElemento() {
        return elemento;
    }

    public void setElemento(Tarea elemento) {
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

    public HostessFacade getHostessFacade() {
        return hostessFacade;
    }

    public void setHostessFacade(HostessFacade hostessFacade) {
        this.hostessFacade = hostessFacade;
    }

    public void guardar(Integer idCliente) {
        guardar:
        {
            int index = 0;
            for (int i = 0; i < clientesPojo.size(); i++) {
                if (clientesPojo.get(index).getCliente().getIdCliente() == idCliente
                        && clientesPojo.get(index).getTareas().getIdTarea() == (Integer) sessionBean.getAttributes().get("idTarea")) {
                    index = i;
                }
            }
            clientesPojo.indexOf(new LCTPojo((Integer) sessionBean.getAttributes().get("idTarea"), idCliente));

            LCTPojo l = clientesPojo.get(index);
            if (l.getAccion() == null || l.getAccion().equals("")) {
                FacesUtil.addErrorMessage("No se puede guardar accion de cliente", "Debe seleccionar una Accion");
                break guardar;
            }

            l = clientesPojo.remove(index);
            Accion a = marketingUserFacade.findAccion(l.getAccion());


            hostessFacade.guardarLCE(l.getListaclientetareas(sessionBean.getUsuario(), a));
            List<Listasclientestareas> clientes = new ArrayList<Listasclientestareas>();
            for (Iterator<LCTPojo> it = clientesPojo.iterator(); it.hasNext();) {
                LCTPojo lct = it.next();
                clientes.add(lct.getListaclientetareas(null, a));
            }
            try {
                Listasclientestareas nuevo = hostessFacade.nuevoLCE((Integer) sessionBean.getAttributes().get("idTarea"), clientes, l.getListaclientetareas(sessionBean.getUsuario(), a));
                clientesPojo.add(new LCTPojo(nuevo));
            } catch (EventoSinClientesPorRevisarException ex) {
                LOGGER.info(ex);
                if (clientesPojo.isEmpty()) {
                    FacesUtil.addInfoMessage("Tarea Finalizada", "");
                }
            }
        }
    }

    public void nuevo(Integer idCliente) {
        guardar:
        {
            int index = clientesPojo.indexOf(new LCTPojo((Integer) sessionBean.getAttributes().get("idTarea"), idCliente));
            LCTPojo l = clientesPojo.remove(index);
            Accion a = marketingUserFacade.findByNombreAccion("INICIAL");

            List<Listasclientestareas> clientes = new ArrayList<Listasclientestareas>();
            for (Iterator<LCTPojo> it = clientesPojo.iterator(); it.hasNext();) {
                LCTPojo lct = it.next();
                clientes.add(lct.getListaclientetareas(null, a));
            }
            try {
                Listasclientestareas nuevo = hostessFacade.nuevoLCE((Integer) sessionBean.getAttributes().get("idTarea"), clientes, l.getListaclientetareas(sessionBean.getUsuario(), a));
                clientesPojo.add(new LCTPojo(nuevo));
            } catch (EventoSinClientesPorRevisarException ex) {
                LOGGER.info(ex);
                if (clientesPojo.isEmpty()) {
                    FacesUtil.addInfoMessage("Tarea Finalizada", "");
                }
            }
        }
    }

    public List<LCTPojo> getClientesPojo() {
        return clientesPojo;
    }

    public void setClientesPojo(List<LCTPojo> clientesPojo) {
        this.clientesPojo = clientesPojo;
    }
}
