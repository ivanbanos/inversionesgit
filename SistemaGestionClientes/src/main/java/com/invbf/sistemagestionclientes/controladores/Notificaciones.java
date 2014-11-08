/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.controladores;

import com.invbf.sistemagestionclientes.entity.Cliente;
import com.invbf.sistemagestionclientes.entity.Permiso;
import com.invbf.sistemagestionclientes.exceptions.clienteInexistenteException;
import com.invbf.sistemagestionclientes.util.FacesUtil;
import com.invbf.sistemagestionclientes.util.PermisoCliente;
import java.util.ArrayList;
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
public class Notificaciones {

    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private List<Permiso> permisos;
    private List<PermisoCliente> lista;
    private Permiso permiso;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public Notificaciones() {
    }

    @PostConstruct
    public void init() {
        permisos = sessionBean.managerUserFacade.getAllPermisos();
        lista = new ArrayList<PermisoCliente>();
        for (Permiso p : permisos) {
            Cliente c = sessionBean.marketingUserFacade.findCliente(Integer.parseInt(p.getId()));
            if (c != null) {
                lista.add(new PermisoCliente(p, c));
            } else {
                sessionBean.managerUserFacade.eliminarPermiso(p);
            }
        }
        permiso = new Permiso();
    }

    public List<Permiso> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<Permiso> permisos) {
        this.permisos = permisos;
    }

    public Permiso getPermiso() {
        return permiso;
    }

    public void setPermiso(Permiso permiso) {
        this.permiso = permiso;
    }

    public void ejecutarPermiso() {
        try {
            String message = sessionBean.managerUserFacade.ejecutarPermiso(permiso);
            FacesUtil.addInfoMessage("Acción realizada con éxito", message);
        } catch (clienteInexistenteException ex) {
            FacesUtil.addInfoMessage("Problemas al realizar la accion", "El registro ya no existe");
        }

        sessionBean.managerUserFacade.eliminarPermiso(permiso);
        permisos = sessionBean.managerUserFacade.getAllPermisos();
        lista = new ArrayList<PermisoCliente>();
        for (Permiso p : permisos) {
            Cliente c = sessionBean.marketingUserFacade.findCliente(Integer.parseInt(p.getId()));
            if (c != null) {
                lista.add(new PermisoCliente(p, c));
            } else {
                sessionBean.managerUserFacade.eliminarPermiso(p);
            }
        }
        permiso = new Permiso();
    }

    public void eliminarPermiso() {
        sessionBean.managerUserFacade.eliminarPermiso(permiso);
        FacesUtil.addInfoMessage("Acción no realizada", "Eliminada con exito");
        permisos = sessionBean.managerUserFacade.getAllPermisos();
        lista = new ArrayList<PermisoCliente>();
        for (Permiso p : permisos) {
            Cliente c = sessionBean.marketingUserFacade.findCliente(Integer.parseInt(p.getId()));
            if (c != null) {
                lista.add(new PermisoCliente(p, c));
            } else {
                sessionBean.managerUserFacade.eliminarPermiso(p);
            }
        }
        permiso = new Permiso();
    }

    public List<PermisoCliente> getLista() {
        return lista;
    }

    public void setLista(List<PermisoCliente> lista) {
        this.lista = lista;
    }
}
