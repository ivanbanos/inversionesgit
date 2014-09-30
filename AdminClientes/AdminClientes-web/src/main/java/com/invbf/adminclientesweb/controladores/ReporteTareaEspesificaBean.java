package com.invbf.adminclientesweb.controladores;

import com.invbf.adminclientesapi.entity.Accion;
import com.invbf.adminclientesapi.entity.Cliente;
import com.invbf.adminclientesapi.entity.Evento;
import com.invbf.adminclientesapi.entity.Listasclientestareas;
import com.invbf.adminclientesapi.entity.Tarea;
import com.invbf.adminclientesapi.entity.Usuario;
import com.invbf.adminclientesapi.facade.AdminFacade;
import com.invbf.adminclientesapi.facade.MarketingUserFacade;
import com.invbf.adminclientesapi.facade.SystemFacade;
import com.invbf.adminclientesweb.util.AccionConteo;
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
public class ReporteTareaEspesificaBean {

    private static final Logger LOGGER = Logger.getLogger(SessionBean.class);
    @EJB
    MarketingUserFacade marketingUserFacade;
    @EJB
    SystemFacade systemFacade;
    @EJB
    AdminFacade adminFacade;
    private Tarea elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private List<String> acciones;
    private List<AccionConteo> accionesConteo;
    private List<AccionConteo> hostessConteo;
    private long totalClientes;
    private long totalRevisados;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public ReporteTareaEspesificaBean() {
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

        if (sessionBean.getAttributes() == null || !sessionBean.getAttributes().containsKey("idTarea")) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("ReporteTareas.xhtml");
            } catch (IOException ex) {
                LOGGER.error(ex);
            }
        }
        elemento = marketingUserFacade.findTarea((Integer) sessionBean.getAttributes().get("idTarea"));
        acciones = new ArrayList<String>();
        for (Accion a : elemento.getTipo().getAccionList()) {
            acciones.add(a.getNombre());
        }
        accionesConteo = new ArrayList<AccionConteo>();
        hostessConteo = new ArrayList<AccionConteo>();
        totalClientes = 0;
        totalRevisados = 0;
        for (Usuario u : elemento.getUsuarioList()) {
            hostessConteo.add(new AccionConteo(u.getNombreUsuario(), 0));
        }
        for (Accion a : elemento.getTipo().getAccionList()) {
            accionesConteo.add(new AccionConteo(a.getNombre(), 0));
        }
        for (Listasclientestareas lct : elemento.getListasclientestareasList()) {
            lct.setFechaAtencion(marketingUserFacade.getLCTFecha(lct));
            totalClientes++;
            if (!lct.getIdAccion().getNombre().equals("INICIAL")) {
                totalRevisados++;
                if (accionesConteo.contains(new AccionConteo(lct.getIdAccion().getNombre(), 0))) {
                    AccionConteo ac = accionesConteo.get(accionesConteo.indexOf(new AccionConteo(lct.getIdAccion().getNombre(), 0)));
                    ac.setCantidad(ac.getCantidad() + 1);
                } else {
                    accionesConteo.add(new AccionConteo(lct.getIdAccion().getNombre(), 1));
                }
                if (hostessConteo.contains(new AccionConteo(lct.getUsuario().getNombreUsuario(), 0))) {
                    AccionConteo ac = hostessConteo.get(hostessConteo.indexOf(new AccionConteo(lct.getUsuario().getNombreUsuario(), 0)));
                    ac.setCantidad(ac.getCantidad() + 1);
                } else {
                    hostessConteo.add(new AccionConteo(lct.getUsuario().getNombreUsuario(), 1));
                }
            }
        }
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

    public Tarea getElemento() {
        return elemento;
    }

    public void setElemento(Tarea elemento) {
        this.elemento = elemento;
    }

    public List<String> getAcciones() {
        return acciones;
    }

    public void setAcciones(List<String> acciones) {
        this.acciones = acciones;
    }

    public List<AccionConteo> getAccionesConteo() {
        return accionesConteo;
    }

    public void setAccionesConteo(List<AccionConteo> accionesConteo) {
        this.accionesConteo = accionesConteo;
    }

    public List<AccionConteo> getHostessConteo() {
        return hostessConteo;
    }

    public void setHostessConteo(List<AccionConteo> hostessConteo) {
        this.hostessConteo = hostessConteo;
    }

    public long getTotalClientes() {
        return totalClientes;
    }

    public void setTotalClientes(long totalClientes) {
        this.totalClientes = totalClientes;
    }

    public long getTotalRevisados() {
        return totalRevisados;
    }

    public void setTotalRevisados(long totalRevisados) {
        this.totalRevisados = totalRevisados;
    }
}
