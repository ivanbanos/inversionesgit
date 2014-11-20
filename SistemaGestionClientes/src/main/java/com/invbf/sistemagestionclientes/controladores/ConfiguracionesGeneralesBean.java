/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.controladores;

import com.invbf.sistemagestionclientes.entity.Configuracion;
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
    private List<Configuracion> configuraciones;
    
    public ConfiguracionesGeneralesBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("configuracion");
        configuraciones = sessionBean.sessionFacade.getAllConfiguraciones();
    }

    public List<Configuracion> getConfiguraciones() {
        return configuraciones;
    }

    public void setConfiguraciones(List<Configuracion> configuraciones) {
        this.configuraciones = configuraciones;
    }
    
    public void guardar(){
        sessionBean.sessionFacade.setAllConfiguraciones(configuraciones);
        FacesUtil.addInfoMessage("Configuraciones guardadas con exito!");
    }
}
