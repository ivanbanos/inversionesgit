/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.controladores;

import com.invbf.sistemagestionclientes.entity.Perfil;
import com.invbf.sistemagestionclientes.entity.Usuario;
import com.invbf.sistemagestionclientes.exceptions.NombreUsuarioExistenteException;
import com.invbf.sistemagestionclientes.facade.impl.AdminFacadeImpl;
import com.invbf.sistemagestionclientes.observer.Observer;
import com.invbf.sistemagestionclientes.util.FacesUtil;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author ideacentre
 */
@ManagedBean
@ViewScoped
public class CrudUsuariosBean implements Observer {
    
    private List<Usuario> lista;
    private Usuario elemento;
    private List<Perfil> listaperfiles;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private String contrasena;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }
    private List<Usuario> flista;

    public List<Usuario> getFlista() {
        return flista;
    }

    public void setFlista(List<Usuario> flista) {
        this.flista = flista;
    }

    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public CrudUsuariosBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.adminFacade = new AdminFacadeImpl();
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("configuracion");

        setNuevoUsuario();
        lista = sessionBean.adminFacade.findAllUsuarios();
        listaperfiles = sessionBean.adminFacade.findAllPerfiles();
        sessionBean.registerObserver(this);
    }

    @PreDestroy
    public void preDestroy() {
        sessionBean.removeObserver(this);
    }

    public List<Usuario> getLista() {
        return lista;
    }

    public void setLista(List<Usuario> lista) {
        this.lista = lista;
    }

    public Usuario getElemento() {
        return elemento;
    }

    public void setElemento(Usuario elemento) {
        this.elemento = elemento;
    }

    public List<Perfil> getListaperfiles() {
        return listaperfiles;
    }

    public void setListaperfiles(List<Perfil> listaperfiles) {
        this.listaperfiles = listaperfiles;
    }

    public void delete() {
        sessionBean.adminFacade.deleteUsuarios(elemento);
        lista = sessionBean.adminFacade.findAllUsuarios();
        FacesUtil.addInfoMessage("Usuario eliminado", elemento.getNombreUsuario());

        setNuevoUsuario();

    }

    public void guardar() {
        if(contrasena.equals(elemento.getContrasena())){
        try {
            boolean opcion = sessionBean.adminFacade.guardarUsuarios(elemento);
            lista = sessionBean.adminFacade.findAllUsuarios();
            if (opcion) {
                FacesUtil.addInfoMessage("Usuario actualizado", elemento.getNombreUsuario());
            } else {
                FacesUtil.addInfoMessage("Usuario creado", elemento.getNombreUsuario());
            }

            setNuevoUsuario();
        } catch (NombreUsuarioExistenteException ex) {
            FacesUtil.addErrorMessage("Usuario no creado", "Nombre de usuario existente");
        }}else{FacesUtil.addErrorMessage("Usuario no creado", "Las contraseñas deben coincidir");
        }
    }

    @Override
    public void update() {
        listaperfiles = sessionBean.adminFacade.findAllPerfiles();
    }

    private void setNuevoUsuario() {

        elemento = new Usuario();
        elemento.setIdPerfil(new Perfil());
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    
}
