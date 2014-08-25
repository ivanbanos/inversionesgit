/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.controladores;

import com.invbf.adminclientesapi.entity.Formularios;
import com.invbf.adminclientesapi.entity.Perfiles;
import com.invbf.adminclientesapi.entity.Vistas;
import com.invbf.adminclientesapi.facade.AdminFacade;
import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;

/**
 *
 * @author ideacentre
 */
@ManagedBean
@ViewScoped
public class PerfilActBean {

    private static final Logger LOGGER =
            Logger.getLogger(SessionBean.class);
    @EJB
    AdminFacade adminFacade;
    private Perfiles elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private boolean vistaUsuario = false;
    private boolean agregarUsuario = false;
    private boolean actualizarUsuario = false;
    private boolean eliminarUsuario = false;
    private boolean vistaPerfiles = false;
    private boolean agregarPerfiles = false;
    private boolean actualizarPerfiles = false;
    private boolean eliminarPerfiles = false;
    private boolean vistaVistas = false;
    private boolean agregarVistas = false;
    private boolean actualizarVistas = false;
    private boolean eliminarVistas = false;
    private boolean vistaForm = false;
    private boolean agregarForm = false;
    private boolean actualizarForm = false;
    private boolean eliminarForm = false;
    private boolean vistaCasinos = false;
    private boolean agregarCasinos = false;
    private boolean actualizarCasinos = false;
    private boolean eliminarCasinos = false;
    private boolean vistaEventos = false;
    private boolean agregarEventos = false;
    private boolean actualizarEventos = false;
    private boolean eliminarEventos = false;
    private boolean vistaClientes = false;
    private boolean agregarClientes = false;
    private boolean actualizarClientes = false;
    private boolean eliminarClientes = false;
    private boolean vistaAtributos = false;
    private boolean agregarAtributos = false;
    private boolean actualizarAtributos = false;
    private boolean eliminarAtributos = false;
    private boolean vistaTipo = false;
    private boolean agregarTipo = false;
    private boolean actualizarTipo = false;
    private boolean eliminarTipo = false;
    private boolean vistaCat = false;
    private boolean agregarCat = false;
    private boolean actualizarCat = false;
    private boolean eliminarCat = false;
    private boolean vistaEstados = false;
    private boolean agregarEstados = false;
    private boolean actualizarEstados = false;
    private boolean eliminarEstados = false;
    private boolean vistaEvMarketing = false;
    private boolean vistaEvHostess = false;
    private boolean vistaReportes = false;
    private boolean vistaConfiguraciones = false;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public PerfilActBean() {
    }

    @PostConstruct
    public void init() {
        if (!sessionBean.perfilViewMatch("Perfiles")) {
            try {
                sessionBean.Desconectar();
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
                LOGGER.error(ex);
            }
        }

        if (sessionBean.getAttributes() == null || !sessionBean.getAttributes().containsKey("idPerfil")) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("AdministradorAtributosSistema.xhtml");
            } catch (IOException ex) {
                LOGGER.error(ex);
            }
        }
        elemento = adminFacade.findPerfil((Integer) sessionBean.getAttributes().get("idPerfil"));
        acomodaropciones();


    }

    public Perfiles getElemento() {
        return elemento;
    }

    public void setElemento(Perfiles elemento) {
        this.elemento = elemento;
    }

    public AdminFacade getAdminFacade() {
        return adminFacade;
    }

    public void setAdminFacade(AdminFacade adminFacade) {
        this.adminFacade = adminFacade;
    }

    public void guardar() {
        try {
            acomodaropcionesdevuelta();
            adminFacade.guardarPerfiles(elemento);
            sessionBean.actualizarUsuario();
            sessionBean.getAttributes().remove("idPerfil");
            FacesContext.getCurrentInstance().getExternalContext().redirect("AdministradorAtributosSistema.xhtml");
        } catch (IOException ex) {
            LOGGER.error(ex);
        }
    }

    private void acomodaropciones() {
        for (Formularios f : elemento.getFormulariosList()) {

            if (f.getAccion().equals("crear")) {
                if (f.getTabla().equals("Usuarios")) {
                    agregarUsuario = true;
                }
                if (f.getTabla().equals("Perfiles")) {
                    agregarPerfiles = true;
                }
                if (f.getTabla().equals("Vistas")) {
                    agregarVistas = true;
                }
                if (f.getTabla().equals("Formularios")) {
                    agregarForm = true;
                }
                if (f.getTabla().equals("Casinos")) {
                    agregarCasinos = true;
                }
                if (f.getTabla().equals("Eventos")) {
                    agregarEventos = true;
                }
                if (f.getTabla().equals("Clientes")) {
                    agregarClientes = true;
                }
                if (f.getTabla().equals("Atributos")) {
                    agregarAtributos = true;
                }
                if (f.getTabla().equals("TiposJuegos")) {
                    agregarTipo = true;
                }
                if (f.getTabla().equals("Categorias")) {
                    agregarCat = true;
                }
                if (f.getTabla().equals("EstadosCliente")) {
                    agregarEstados = true;
                }
            }
            if (f.getAccion().equals("actualizar")) {
                if (f.getTabla().equals("Usuarios")) {
                    actualizarUsuario = true;
                }
                if (f.getTabla().equals("Perfiles")) {
                    actualizarPerfiles = true;
                }
                if (f.getTabla().equals("Vistas")) {
                    actualizarVistas = true;
                }
                if (f.getTabla().equals("Formularios")) {
                    actualizarForm = true;
                }
                if (f.getTabla().equals("Casinos")) {
                    actualizarCasinos = true;
                }
                if (f.getTabla().equals("Eventos")) {
                    actualizarEventos = true;
                }
                if (f.getTabla().equals("Clientes")) {
                    actualizarClientes = true;
                }
                if (f.getTabla().equals("Atributos")) {
                    actualizarAtributos = true;
                }
                if (f.getTabla().equals("TiposJuegos")) {
                    actualizarTipo = true;
                }
                if (f.getTabla().equals("Categorias")) {
                    actualizarCat = true;
                }
                if (f.getTabla().equals("EstadosCliente")) {
                    actualizarEstados = true;
                }
            }
            if (f.getAccion().equals("eliminar")) {
                if (f.getTabla().equals("Usuarios")) {
                    eliminarUsuario = true;
                }
                if (f.getTabla().equals("Perfiles")) {
                    eliminarPerfiles = true;
                }
                if (f.getTabla().equals("Vistas")) {
                    eliminarVistas = true;
                }
                if (f.getTabla().equals("Formularios")) {
                    eliminarForm = true;
                }
                if (f.getTabla().equals("Casinos")) {
                    eliminarCasinos = true;
                }
                if (f.getTabla().equals("Eventos")) {
                    eliminarEventos = true;
                }
                if (f.getTabla().equals("Clientes")) {
                    eliminarClientes = true;
                }
                if (f.getTabla().equals("Atributos")) {
                    eliminarAtributos = true;
                }
                if (f.getTabla().equals("TiposJuegos")) {
                    eliminarTipo = true;
                }
                if (f.getTabla().equals("Categorias")) {
                    eliminarCat = true;
                }
                if (f.getTabla().equals("EstadosCliente")) {
                    eliminarEstados = true;
                }
            }

        }
        for (Vistas v : elemento.getVistasList()) {
            if (v.getNombreVista().equals("Usuarios")) {
                vistaUsuario = true;
            }
            if (v.getNombreVista().equals("Perfiles")) {
                vistaPerfiles = true;
            }
            if (v.getNombreVista().equals("Vistas")) {
                vistaVistas = true;
            }
            if (v.getNombreVista().equals("Formularios")) {
                vistaForm = true;
            }
            if (v.getNombreVista().equals("Casinos")) {
                vistaCasinos = true;
            }
            if (v.getNombreVista().equals("Eventos")) {
                vistaEventos = true;
            }
            if (v.getNombreVista().equals("Clientes")) {
                vistaClientes = true;
            }
            if (v.getNombreVista().equals("Atributos")) {
                vistaAtributos = true;
            }
            if (v.getNombreVista().equals("TipoJuego")) {
                vistaTipo = true;
            }
            if (v.getNombreVista().equals("Categorias")) {
                vistaCat = true;
            }
            if (v.getNombreVista().equals("EstadosClientes")) {
                vistaEstados = true;
            }
            if (v.getNombreVista().equals("ManejadorEventosMarketing")) {
                vistaEvMarketing = true;
            }
            if (v.getNombreVista().equals("ManejadorEventosHostess")) {
                vistaEvHostess = true;
            }
            if (v.getNombreVista().equals("Reportes")) {
                vistaReportes = true;
            }
            if (v.getNombreVista().equals("ConfiguracionesGenerales")) {
                vistaConfiguraciones = true;
            }
        }
    }

    private void acomodaropcionesdevuelta() {
        Vistas v = adminFacade.findVistasByNombre("Usuarios");
        Vistas v2 = adminFacade.findVistasByNombre("AtributosSistema");
        int count = 0;
        if (vistaUsuario == false) {
            count++;
            if (elemento.getVistasList().contains(v)) {
                elemento.getVistasList().remove(v);
            }
        } else {
            if (!elemento.getVistasList().contains(v2)) {
                elemento.getVistasList().add(v2);
            }
            if (!elemento.getVistasList().contains(v)) {
                elemento.getVistasList().add(v);
            }
        }
        v = adminFacade.findVistasByNombre("Perfiles");
        if (vistaPerfiles == false) {
            count++;
            if (elemento.getVistasList().contains(v)) {
                elemento.getVistasList().remove(v);
            }
        } else {

            if (!elemento.getVistasList().contains(v2)) {
                elemento.getVistasList().add(v2);
            }
            if (!elemento.getVistasList().contains(v)) {
                elemento.getVistasList().add(v);
            }
        }
        v = adminFacade.findVistasByNombre("Vistas");
        if (vistaVistas == false) {
            count++;
            if (elemento.getVistasList().contains(v)) {
                elemento.getVistasList().remove(v);
            }
        } else {

            if (!elemento.getVistasList().contains(v2)) {
                elemento.getVistasList().add(v2);
            }
            if (!elemento.getVistasList().contains(v)) {
                elemento.getVistasList().add(v);
            }
        }
        v = adminFacade.findVistasByNombre("Formularios");
        if (vistaForm == false) {
            count++;
            if (elemento.getVistasList().contains(v)) {
                elemento.getVistasList().remove(v);
            }
        } else {
            if (!elemento.getVistasList().contains(v2)) {
                elemento.getVistasList().add(v2);
            }
            if (!elemento.getVistasList().contains(v)) {
                elemento.getVistasList().add(v);
            }
        }
        if (count == 4) {
            if (elemento.getVistasList().contains(v2)) {
                elemento.getVistasList().remove(v2);
            }
        }
        v = adminFacade.findVistasByNombre("Casinos");
        v2 = adminFacade.findVistasByNombre("AtributosMarketing");
        count = 0;
        if (vistaCasinos == false) {
            count++;
            if (elemento.getVistasList().contains(v)) {
                elemento.getVistasList().remove(v);
            }
        } else {

            if (!elemento.getVistasList().contains(v2)) {
                elemento.getVistasList().add(v2);
            }
            if (!elemento.getVistasList().contains(v)) {
                elemento.getVistasList().add(v);
            }
        }
        v = adminFacade.findVistasByNombre("Eventos");
        if (vistaEventos == false) {
            count++;
            if (elemento.getVistasList().contains(v)) {
                elemento.getVistasList().remove(v);
            }
        } else {

            if (!elemento.getVistasList().contains(v2)) {
                elemento.getVistasList().add(v2);
            }
            if (!elemento.getVistasList().contains(v)) {
                elemento.getVistasList().add(v);
            }
        }
        v = adminFacade.findVistasByNombre("Clientes");
        if (vistaClientes == false) {
            count++;
            if (elemento.getVistasList().contains(v)) {
                elemento.getVistasList().remove(v);
            }
        } else {

            if (!elemento.getVistasList().contains(v2)) {
                elemento.getVistasList().add(v2);
            }
            if (!elemento.getVistasList().contains(v)) {
                elemento.getVistasList().add(v);
            }
        }
        v = adminFacade.findVistasByNombre("Atributos");
        if (vistaAtributos == false) {
            count++;
            if (elemento.getVistasList().contains(v)) {
                elemento.getVistasList().remove(v);
            }
        } else {

            if (!elemento.getVistasList().contains(v2)) {
                elemento.getVistasList().add(v2);
            }
            if (!elemento.getVistasList().contains(v)) {
                elemento.getVistasList().add(v);
            }
        }
        v = adminFacade.findVistasByNombre("TipoJuego");
        if (vistaTipo == false) {
            count++;
            if (elemento.getVistasList().contains(v)) {
                elemento.getVistasList().remove(v);
            }
        } else {

            if (!elemento.getVistasList().contains(v2)) {
                elemento.getVistasList().add(v2);
            }
            if (!elemento.getVistasList().contains(v)) {
                elemento.getVistasList().add(v);
            }
        }
        v = adminFacade.findVistasByNombre("Categorias");
        if (vistaCat == false) {
            count++;
            if (elemento.getVistasList().contains(v)) {
                elemento.getVistasList().remove(v);
            }
        } else {

            if (!elemento.getVistasList().contains(v2)) {
                elemento.getVistasList().add(v2);
            }
            if (!elemento.getVistasList().contains(v)) {
                elemento.getVistasList().add(v);
            }
        }
        v = adminFacade.findVistasByNombre("EstadosClientes");
        if (vistaEstados == false) {
            count++;
            if (elemento.getVistasList().contains(v)) {
                elemento.getVistasList().remove(v);
            }
        } else {

            if (!elemento.getVistasList().contains(v2)) {
                elemento.getVistasList().add(v2);
            }
            if (!elemento.getVistasList().contains(v)) {
                elemento.getVistasList().add(v);
            }
        }


        if (count == 7) {
            if (elemento.getVistasList().contains(v2)) {
                elemento.getVistasList().remove(v2);
            }
        }

        v = adminFacade.findVistasByNombre("ManejadorEventosMarketing");
        if (vistaEvMarketing == false) {
            if (elemento.getVistasList().contains(v)) {
                elemento.getVistasList().remove(v);
            }
        } else {

            if (!elemento.getVistasList().contains(v)) {
                elemento.getVistasList().add(v);
            }
        }
        v = adminFacade.findVistasByNombre("ManejadorEventosHostess");
        if (vistaEvHostess == false) {
            if (elemento.getVistasList().contains(v)) {
                elemento.getVistasList().remove(v);
            }
        } else {
            if (!elemento.getVistasList().contains(v)) {
                elemento.getVistasList().add(v);
            }
        }
        v = adminFacade.findVistasByNombre("Reportes");
        if (vistaReportes == false) {
            if (elemento.getVistasList().contains(v)) {
                elemento.getVistasList().remove(v);
            }
        } else {
            if (!elemento.getVistasList().contains(v)) {
                elemento.getVistasList().add(v);
            }
        }
        v = adminFacade.findVistasByNombre("ConfiguracionesGenerales");
        if (vistaConfiguraciones == false) {
            if (elemento.getVistasList().contains(v)) {
                elemento.getVistasList().remove(v);
            }
        } else {
            if (!elemento.getVistasList().contains(v)) {
                elemento.getVistasList().add(v);
            }
        }

        Formularios f = adminFacade.findFormularioByAccionAndTabla("crear", "Usuarios");
        if (agregarUsuario == false) {
            if (elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().remove(f);
            }
        } else {
            if (!elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().add(f);
            }
        }
        f = adminFacade.findFormularioByAccionAndTabla("actualizar", "Usuarios");
        if (actualizarUsuario == false) {
            if (elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().remove(f);
            }
        } else {
            if (!elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().add(f);
            }
        }
        f = adminFacade.findFormularioByAccionAndTabla("eliminar", "Usuarios");
        if (eliminarUsuario == false) {
            if (elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().remove(f);
            }
        } else {
            if (!elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().add(f);
            }
        }
        f = adminFacade.findFormularioByAccionAndTabla("crear", "Perfiles");
        if ( agregarPerfiles == false) {
            if (elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().remove(f);
            }
        } else {
            if (!elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().add(f);
            }
        }
        f = adminFacade.findFormularioByAccionAndTabla("actualizar", "Perfiles");
        if (actualizarPerfiles == false) {
            if (elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().remove(f);
            }
        } else {
            if (!elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().add(f);
            }
        }
        f = adminFacade.findFormularioByAccionAndTabla("eliminar", "Perfiles");
        if (eliminarPerfiles == false) {
            if (elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().remove(f);
            }
        } else {
            if (!elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().add(f);
            }
        }
        f = adminFacade.findFormularioByAccionAndTabla("crear", "Vistas");
        if (agregarVistas == false) {
            if (elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().remove(f);
            }
        } else {
            if (!elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().add(f);
            }
        }
        f = adminFacade.findFormularioByAccionAndTabla("actualizar", "Vistas");
        if (actualizarVistas == false) {
            if (elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().remove(f);
            }
        } else {
            if (!elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().add(f);
            }
        }
        f = adminFacade.findFormularioByAccionAndTabla("eliminar", "Vistas");
        if (eliminarVistas == false) {
            if (elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().remove(f);
            }
        } else {
            if (!elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().add(f);
            }
        }
        f = adminFacade.findFormularioByAccionAndTabla("crear", "Formularios");
        if (agregarForm == false) {
            if (elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().remove(f);
            }
        } else {
            if (!elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().add(f);
            }
        }
        f = adminFacade.findFormularioByAccionAndTabla("actualizar", "Formularios");
        if (actualizarForm == false) {
            if (elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().remove(f);
            }
        } else {
            if (!elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().add(f);
            }
        }
        f = adminFacade.findFormularioByAccionAndTabla("eliminar", "Formularios");
        if (eliminarForm == false) {
            if (elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().remove(f);
            }
        } else {
            if (!elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().add(f);
            }
        }
        f = adminFacade.findFormularioByAccionAndTabla("crear", "Casinos");
        if (agregarCasinos == false) {
            if (elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().remove(f);
            }
        } else {
            if (!elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().add(f);
            }
        }
        f = adminFacade.findFormularioByAccionAndTabla("actualizar", "Casinos");
        if (actualizarCasinos == false) {
            if (elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().remove(f);
            }
        } else {
            if (!elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().add(f);
            }
        }
        f = adminFacade.findFormularioByAccionAndTabla("eliminar", "Casinos");
        if (eliminarCasinos == false) {
            if (elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().remove(f);
            }
        } else {
            if (!elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().add(f);
            }
        }
        f = adminFacade.findFormularioByAccionAndTabla("crear", "Eventos");
        if (agregarEventos == false) {
            if (elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().remove(f);
            }
        } else {
            if (!elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().add(f);
            }
        }
        f = adminFacade.findFormularioByAccionAndTabla("actualizar", "Eventos");
        if (actualizarEventos == false) {
            if (elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().remove(f);
            }
        } else {
            if (!elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().add(f);
            }
        }
        f = adminFacade.findFormularioByAccionAndTabla("eliminar", "Eventos");
        if (eliminarEventos == false) {
            if (elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().remove(f);
            }
        } else {
            if (!elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().add(f);
            }
        }
        f = adminFacade.findFormularioByAccionAndTabla("crear", "Clientes");
        if (agregarClientes == false) {
            if (elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().remove(f);
            }
        } else {
            if (!elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().add(f);
            }
        }
        f = adminFacade.findFormularioByAccionAndTabla("actualizar", "Clientes");
        if (actualizarClientes == false) {
            if (elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().remove(f);
            }
        } else {
            if (!elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().add(f);
            }
        }
        f = adminFacade.findFormularioByAccionAndTabla("eliminar", "Clientes");
        if (eliminarClientes == false) {
            if (elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().remove(f);
            }
        } else {
            if (!elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().add(f);
            }
        }
        f = adminFacade.findFormularioByAccionAndTabla("crear", "Atributos");
        if (agregarAtributos == false) {
            if (elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().remove(f);
            }
        } else {
            if (!elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().add(f);
            }
        }
        f = adminFacade.findFormularioByAccionAndTabla("actualizar", "Atributos");
        if (actualizarAtributos == false) {
            if (elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().remove(f);
            }
        } else {
            if (!elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().add(f);
            }
        }
        f = adminFacade.findFormularioByAccionAndTabla("eliminar", "Atributos");
        if (eliminarAtributos == false) {
            if (elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().remove(f);
            }
        } else {
            if (!elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().add(f);
            }
        }
        f = adminFacade.findFormularioByAccionAndTabla("crear", "TiposJuegos");
        if (agregarTipo == false) {
            if (elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().remove(f);
            }
        } else {
            if (!elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().add(f);
            }
        }
        f = adminFacade.findFormularioByAccionAndTabla("actualizar", "TiposJuegos");
        if (actualizarTipo== false) {
            if (elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().remove(f);
            }
        } else {
            if (!elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().add(f);
            }
        }
        f = adminFacade.findFormularioByAccionAndTabla("eliminar", "TiposJuegos");
        if (eliminarTipo == false) {
            if (elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().remove(f);
            }
        } else {
            if (!elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().add(f);
            }
        }
        f = adminFacade.findFormularioByAccionAndTabla("crear", "Categorias");
        if (agregarCat == false) {
            if (elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().remove(f);
            }
        } else {
            if (!elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().add(f);
            }
        }
        f = adminFacade.findFormularioByAccionAndTabla("actualizar", "Categorias");
        if (actualizarCat == false) {
            if (elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().remove(f);
            }
        } else {
            if (!elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().add(f);
            }
        }
        f = adminFacade.findFormularioByAccionAndTabla("eliminar", "Categorias");
        if (eliminarCat == false) {
            if (elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().remove(f);
            }
        } else {
            if (!elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().add(f);
            }
        }
        f = adminFacade.findFormularioByAccionAndTabla("crear", "EstadosCliente");
        if (agregarEstados == false) {
            if (elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().remove(f);
            }
        } else {
            if (!elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().add(f);
            }
        }
        f = adminFacade.findFormularioByAccionAndTabla("actualizar", "EstadosCliente");
        if (actualizarEstados == false) {
            if (elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().remove(f);
            }
        } else {
            if (!elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().add(f);
            }
        }
        f = adminFacade.findFormularioByAccionAndTabla("eliminar", "EstadosCliente");
        if (eliminarEstados == false) {
            if (elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().remove(f);
            }
        } else {
            if (!elemento.getFormulariosList().contains(f)) {
                elemento.getFormulariosList().add(f);
            }
        }

    }

    public boolean isVistaUsuario() {
        return vistaUsuario;
    }

    public void setVistaUsuario(boolean vistaUsuario) {
        this.vistaUsuario = vistaUsuario;
    }

    public boolean isAgregarUsuario() {
        return agregarUsuario;
    }

    public void setAgregarUsuario(boolean agregarUsuario) {
        this.agregarUsuario = agregarUsuario;
    }

    public boolean isActualizarUsuario() {
        return actualizarUsuario;
    }

    public void setActualizarUsuario(boolean actualizarUsuario) {
        this.actualizarUsuario = actualizarUsuario;
    }

    public boolean isEliminarUsuario() {
        return eliminarUsuario;
    }

    public void setEliminarUsuario(boolean eliminarUsuario) {
        this.eliminarUsuario = eliminarUsuario;
    }

    public boolean isVistaPerfiles() {
        return vistaPerfiles;
    }

    public void setVistaPerfiles(boolean vistaPerfiles) {
        this.vistaPerfiles = vistaPerfiles;
    }

    public boolean isAgregarPerfiles() {
        return agregarPerfiles;
    }

    public void setAgregarPerfiles(boolean agregarPerfiles) {
        this.agregarPerfiles = agregarPerfiles;
    }

    public boolean isActualizarPerfiles() {
        return actualizarPerfiles;
    }

    public void setActualizarPerfiles(boolean actualizarPerfiles) {
        this.actualizarPerfiles = actualizarPerfiles;
    }

    public boolean isEliminarPerfiles() {
        return eliminarPerfiles;
    }

    public void setEliminarPerfiles(boolean eliminarPerfiles) {
        this.eliminarPerfiles = eliminarPerfiles;
    }

    public boolean isVistaVistas() {
        return vistaVistas;
    }

    public void setVistaVistas(boolean vistaVistas) {
        this.vistaVistas = vistaVistas;
    }

    public boolean isAgregarVistas() {
        return agregarVistas;
    }

    public void setAgregarVistas(boolean agregarVistas) {
        this.agregarVistas = agregarVistas;
    }

    public boolean isActualizarVistas() {
        return actualizarVistas;
    }

    public void setActualizarVistas(boolean actualizarVistas) {
        this.actualizarVistas = actualizarVistas;
    }

    public boolean isEliminarVistas() {
        return eliminarVistas;
    }

    public void setEliminarVistas(boolean eliminarVistas) {
        this.eliminarVistas = eliminarVistas;
    }

    public boolean isVistaForm() {
        return vistaForm;
    }

    public void setVistaForm(boolean vistaForm) {
        this.vistaForm = vistaForm;
    }

    public boolean isAgregarForm() {
        return agregarForm;
    }

    public void setAgregarForm(boolean agregarForm) {
        this.agregarForm = agregarForm;
    }

    public boolean isActualizarForm() {
        return actualizarForm;
    }

    public void setActualizarForm(boolean actualizarForm) {
        this.actualizarForm = actualizarForm;
    }

    public boolean isEliminarForm() {
        return eliminarForm;
    }

    public void setEliminarForm(boolean eliminarForm) {
        this.eliminarForm = eliminarForm;
    }

    public boolean isVistaCasinos() {
        return vistaCasinos;
    }

    public void setVistaCasinos(boolean vistaCasinos) {
        this.vistaCasinos = vistaCasinos;
    }

    public boolean isAgregarCasinos() {
        return agregarCasinos;
    }

    public void setAgregarCasinos(boolean agregarCasinos) {
        this.agregarCasinos = agregarCasinos;
    }

    public boolean isActualizarCasinos() {
        return actualizarCasinos;
    }

    public void setActualizarCasinos(boolean actualizarCasinos) {
        this.actualizarCasinos = actualizarCasinos;
    }

    public boolean isEliminarCasinos() {
        return eliminarCasinos;
    }

    public void setEliminarCasinos(boolean eliminarCasinos) {
        this.eliminarCasinos = eliminarCasinos;
    }

    public boolean isVistaEventos() {
        return vistaEventos;
    }

    public void setVistaEventos(boolean vistaEventos) {
        this.vistaEventos = vistaEventos;
    }

    public boolean isAgregarEventos() {
        return agregarEventos;
    }

    public void setAgregarEventos(boolean agregarEventos) {
        this.agregarEventos = agregarEventos;
    }

    public boolean isActualizarEventos() {
        return actualizarEventos;
    }

    public void setActualizarEventos(boolean actualizarEventos) {
        this.actualizarEventos = actualizarEventos;
    }

    public boolean isEliminarEventos() {
        return eliminarEventos;
    }

    public void setEliminarEventos(boolean eliminarEventos) {
        this.eliminarEventos = eliminarEventos;
    }

    public boolean isVistaClientes() {
        return vistaClientes;
    }

    public void setVistaClientes(boolean vistaClientes) {
        this.vistaClientes = vistaClientes;
    }

    public boolean isAgregarClientes() {
        return agregarClientes;
    }

    public void setAgregarClientes(boolean agregarClientes) {
        this.agregarClientes = agregarClientes;
    }

    public boolean isActualizarClientes() {
        return actualizarClientes;
    }

    public void setActualizarClientes(boolean actualizarClientes) {
        this.actualizarClientes = actualizarClientes;
    }

    public boolean isEliminarClientes() {
        return eliminarClientes;
    }

    public void setEliminarClientes(boolean eliminarClientes) {
        this.eliminarClientes = eliminarClientes;
    }

    public boolean isVistaAtributos() {
        return vistaAtributos;
    }

    public void setVistaAtributos(boolean vistaAtributos) {
        this.vistaAtributos = vistaAtributos;
    }

    public boolean isAgregarAtributos() {
        return agregarAtributos;
    }

    public void setAgregarAtributos(boolean agregarAtributos) {
        this.agregarAtributos = agregarAtributos;
    }

    public boolean isActualizarAtributos() {
        return actualizarAtributos;
    }

    public void setActualizarAtributos(boolean actualizarAtributos) {
        this.actualizarAtributos = actualizarAtributos;
    }

    public boolean isEliminarAtributos() {
        return eliminarAtributos;
    }

    public void setEliminarAtributos(boolean eliminarAtributos) {
        this.eliminarAtributos = eliminarAtributos;
    }

    public boolean isVistaTipo() {
        return vistaTipo;
    }

    public void setVistaTipo(boolean vistaTipo) {
        this.vistaTipo = vistaTipo;
    }

    public boolean isAgregarTipo() {
        return agregarTipo;
    }

    public void setAgregarTipo(boolean agregarTipo) {
        this.agregarTipo = agregarTipo;
    }

    public boolean isActualizarTipo() {
        return actualizarTipo;
    }

    public void setActualizarTipo(boolean actualizarTipo) {
        this.actualizarTipo = actualizarTipo;
    }

    public boolean isEliminarTipo() {
        return eliminarTipo;
    }

    public void setEliminarTipo(boolean eliminarTipo) {
        this.eliminarTipo = eliminarTipo;
    }

    public boolean isVistaCat() {
        return vistaCat;
    }

    public void setVistaCat(boolean vistaCat) {
        this.vistaCat = vistaCat;
    }

    public boolean isAgregarCat() {
        return agregarCat;
    }

    public void setAgregarCat(boolean agregarCat) {
        this.agregarCat = agregarCat;
    }

    public boolean isActualizarCat() {
        return actualizarCat;
    }

    public void setActualizarCat(boolean actualizarCat) {
        this.actualizarCat = actualizarCat;
    }

    public boolean isEliminarCat() {
        return eliminarCat;
    }

    public void setEliminarCat(boolean eliminarCat) {
        this.eliminarCat = eliminarCat;
    }

    public boolean isVistaEstados() {
        return vistaEstados;
    }

    public void setVistaEstados(boolean vistaEstados) {
        this.vistaEstados = vistaEstados;
    }

    public boolean isAgregarEstados() {
        return agregarEstados;
    }

    public void setAgregarEstados(boolean agregarEstados) {
        this.agregarEstados = agregarEstados;
    }

    public boolean isActualizarEstados() {
        return actualizarEstados;
    }

    public void setActualizarEstados(boolean actualizarEstados) {
        this.actualizarEstados = actualizarEstados;
    }

    public boolean isEliminarEstados() {
        return eliminarEstados;
    }

    public void setEliminarEstados(boolean eliminarEstados) {
        this.eliminarEstados = eliminarEstados;
    }

    public boolean isVistaEvMarketing() {
        return vistaEvMarketing;
    }

    public void setVistaEvMarketing(boolean vistaEvMarketing) {
        this.vistaEvMarketing = vistaEvMarketing;
    }

    public boolean isVistaEvHostess() {
        return vistaEvHostess;
    }

    public void setVistaEvHostess(boolean vistaEvHostess) {
        this.vistaEvHostess = vistaEvHostess;
    }

    public boolean isVistaReportes() {
        return vistaReportes;
    }

    public void setVistaReportes(boolean vistaReportes) {
        this.vistaReportes = vistaReportes;
    }

    public boolean isVistaConfiguraciones() {
        return vistaConfiguraciones;
    }

    public void setVistaConfiguraciones(boolean vistaConfiguraciones) {
        this.vistaConfiguraciones = vistaConfiguraciones;
    }
    
}
