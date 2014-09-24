
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

    private static final Logger LOGGER =
            Logger.getLogger(SessionBean.class);
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
        for(Accion a:elemento.getTipo().getAccionList()){
            acciones.add(a.getNombre());
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

}