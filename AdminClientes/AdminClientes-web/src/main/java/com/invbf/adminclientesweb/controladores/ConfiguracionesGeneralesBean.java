/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.controladores;

import com.invbf.adminclientesapi.entity.Configuraciones;
import com.invbf.adminclientesapi.facade.SystemFacade;
import com.invbf.adminclientesweb.util.FacesUtil;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
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
    @EJB
    SystemFacade systemFacade;
    private List<Configuraciones> configuraciones;
    
    public ConfiguracionesGeneralesBean() {
    }

    @PostConstruct
    public void init() {
        configuraciones = systemFacade.getAllConfiguraciones();
    }

    public SystemFacade getSystemFacade() {
        return systemFacade;
    }

    public void setSystemFacade(SystemFacade systemFacade) {
        this.systemFacade = systemFacade;
    }

    public List<Configuraciones> getConfiguraciones() {
        return configuraciones;
    }

    public void setConfiguraciones(List<Configuraciones> configuraciones) {
        this.configuraciones = configuraciones;
    }
    
    public void guardar(){
        systemFacade.setAllConfiguraciones(configuraciones);
        FacesUtil.addInfoMessage("Configuraciones guardadas con exito!");
    }
}
