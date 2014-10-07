/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.controladores;

import com.invbf.sistemagestionclientes.exceptions.ClavesNoConcuerdanException;
import com.invbf.sistemagestionclientes.exceptions.NoCambioContrasenaException;
import com.invbf.sistemagestionclientes.facade.SystemFacade;
import com.invbf.sistemagestionclientes.facade.impl.SystemFacadeImpl;
import com.invbf.sistemagestionclientes.util.FacesUtil;
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
public class CuentaUsuarioBean {
    
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
        systemFacade = new SystemFacadeImpl();
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("cuenta");
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
            FacesUtil.addErrorMessage("Claves no concuerdan");
        } catch (NoCambioContrasenaException ex) {
            FacesUtil.addErrorMessage("No se pudo cambiar la contraseña");
        } finally {
            contrasena = "";
            nueva = "";
        }
    }
}
