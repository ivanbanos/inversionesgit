/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.controladores;

import com.invbf.adminclientesapi.exceptions.ClavesNoConcuerdanException;
import com.invbf.adminclientesapi.exceptions.NoCambioContrasenaException;
import com.invbf.adminclientesapi.facade.SystemFacade;
import com.invbf.adminclientesweb.util.FacesUtil;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author ideacentre
 */
@ManagedBean
@ViewScoped
public class CuentaUsuarioBean {
    
    
    private static final org.apache.log4j.Logger LOGGER =
            org.apache.log4j.Logger.getLogger(CuentaUsuarioBean.class);
    @EJB
    SystemFacade systemFacade;
    
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    
    private String contrasena;
    private String nueva;
    
    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }
    
    public CuentaUsuarioBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
    }

    public SystemFacade getSystemFacade() {
        return systemFacade;
    }

    public void setSystemFacade(SystemFacade systemFacade) {
        this.systemFacade = systemFacade;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNueva() {
        return nueva;
    }

    public void setNueva(String nueva) {
        this.nueva = nueva;
    }
    
    public void cambioContrasena(){
        try {
            systemFacade.cambiarContrasena(contrasena, nueva, sessionBean.getUsuario());
            FacesUtil.addInfoMessage("Contraseña cambiada con exito!");
        } catch (ClavesNoConcuerdanException ex) {
            LOGGER.error(ex);
            FacesUtil.addErrorMessage("Claves no concuerdan");
        } catch (NoCambioContrasenaException ex) {
            LOGGER.error(ex);
            FacesUtil.addErrorMessage("No se pudo cambiar la contraseña");
        } finally {
            contrasena = "";
            nueva = "";
        }
    }
}
