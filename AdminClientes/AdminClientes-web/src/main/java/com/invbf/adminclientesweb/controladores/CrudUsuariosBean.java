/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.controladores;

import com.invbf.adminclientesapi.entity.Perfil;
import com.invbf.adminclientesapi.entity.Usuario;
import com.invbf.adminclientesapi.exceptions.NombreUsuarioExistenteException;
import com.invbf.adminclientesapi.facade.AdminFacade;
import com.invbf.adminclientesweb.interfaces.Observer;
import com.invbf.adminclientesweb.util.FacesUtil;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
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
public class CrudUsuariosBean implements Observer {

    private static final Logger LOGGER =
            Logger.getLogger(SessionBean.class);
    @EJB
    AdminFacade adminFacade;
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
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("configuracion");

        setNuevoUsuario();
        lista = adminFacade.findAllUsuarios();
        listaperfiles = adminFacade.findAllPerfiles();
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

    public AdminFacade getAdminFacade() {
        return adminFacade;
    }

    public void setAdminFacade(AdminFacade adminFacade) {
        this.adminFacade = adminFacade;
    }

    public List<Perfil> getListaperfiles() {
        return listaperfiles;
    }

    public void setListaperfiles(List<Perfil> listaperfiles) {
        this.listaperfiles = listaperfiles;
    }

    public void delete() {
        adminFacade.deleteUsuarios(elemento);
        lista = adminFacade.findAllUsuarios();
        sessionBean.registrarlog("eliminar", "Usuarios", elemento.toString());

        FacesUtil.addInfoMessage("Usuario eliminado", elemento.getNombreUsuario());

        setNuevoUsuario();

    }

    public void guardar() {
        if(contrasena.equals(elemento.getContrasena())){
        try {
            boolean opcion = adminFacade.guardarUsuarios(elemento);
            lista = adminFacade.findAllUsuarios();
            if (opcion) {
                sessionBean.registrarlog("actualizar", "Usuarios", elemento.toString());
                FacesUtil.addInfoMessage("Usuario actualizado", elemento.getNombreUsuario());
            } else {
                sessionBean.registrarlog("crear", "Usuarios", elemento.toString());
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
        listaperfiles = adminFacade.findAllPerfiles();
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
