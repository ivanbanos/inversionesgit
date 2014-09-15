/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.controladores;

import com.invbf.adminclientesapi.entity.Configuracion;
import com.invbf.adminclientesapi.facade.SystemFacade;
import com.invbf.adminclientesweb.util.FacesUtil;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;

/**
 *
 * @author ideacentre
 */
@ManagedBean
@ViewScoped
public class ConfiguracionesGeneralesBean {
    private static final Logger LOGGER =
            Logger.getLogger(SessionBean.class);
    
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    
    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }
    @EJB
    SystemFacade systemFacade;
    private List<Configuracion> configuraciones;
    
    public ConfiguracionesGeneralesBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("configuracion");
        configuraciones = systemFacade.getAllConfiguraciones();
    }

    public SystemFacade getSystemFacade() {
        return systemFacade;
    }

    public void setSystemFacade(SystemFacade systemFacade) {
        this.systemFacade = systemFacade;
    }

    public List<Configuracion> getConfiguraciones() {
        return configuraciones;
    }

    public void setConfiguraciones(List<Configuracion> configuraciones) {
        this.configuraciones = configuraciones;
    }
    
    public void guardar(){
        systemFacade.setAllConfiguraciones(configuraciones);
        FacesUtil.addInfoMessage("Configuraciones guardadas con exito!");
    }
}
