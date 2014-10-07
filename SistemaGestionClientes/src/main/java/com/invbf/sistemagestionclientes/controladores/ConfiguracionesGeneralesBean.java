/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.controladores;

import com.invbf.sistemagestionclientes.entity.Configuracion;
import com.invbf.sistemagestionclientes.facade.SystemFacade;
import com.invbf.sistemagestionclientes.facade.impl.SystemFacadeImpl;
import com.invbf.sistemagestionclientes.util.FacesUtil;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author ideacentre
 */
@ManagedBean
@ViewScoped
public class ConfiguracionesGeneralesBean {
    
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    
    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }
    SystemFacade systemFacade;
    private List<Configuracion> configuraciones;
    
    public ConfiguracionesGeneralesBean() {
    }

    @PostConstruct
    public void init() {
        systemFacade = new SystemFacadeImpl();
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
