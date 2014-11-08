/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.controladores;

import com.invbf.sistemagestionclientes.entity.Formulario;
import com.invbf.sistemagestionclientes.entity.Perfil;
import com.invbf.sistemagestionclientes.entity.Vista;
import com.invbf.sistemagestionclientes.exceptions.PerfilExistenteException;
import com.invbf.sistemagestionclientes.util.FacesUtil;
import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author ideacentre
 */
@ManagedBean
@ViewScoped
public class PerfilActBean {

    private Perfil elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private boolean vistaUsuario = false;
    private boolean agregarUsuario = false;
    private boolean actualizarUsuario = false;
    private boolean eliminarUsuario = false;
    private boolean vistaTareas = false;
    private boolean agregarTareas = false;
    private boolean actualizarTareas = false;
    private boolean eliminarTareas = false;
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
    private boolean vistaAcciones = false;
    private boolean agregarAcciones = false;
    private boolean actualizarAcciones = false;
    private boolean eliminarAcciones = false;
    private boolean vistaTipoevento = false;
    private boolean agregarTipoevento = false;
    private boolean actualizarTipoevento = false;
    private boolean eliminarTipoevento = false;
    private boolean vistaEvMarketing = false;
    private boolean vistaEvHostess = false;
    private boolean vistaReportes = false;
    private boolean vistaConfiguraciones = false;
    private boolean vistaNotificacion = false;
    private boolean cupofidelizacion = false;
    private boolean vistatipoDocumento = false;
    private boolean agregartipoDocumento = false;
    private boolean actualizartipoDocumento = false;
    private boolean eliminartipoDocumento = false;

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
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("configuracion");
        if (!sessionBean.perfilViewMatch("Perfiles")) {
            try {
                sessionBean.Desconectar();
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        }

        if (sessionBean.getAttributes() == null || !sessionBean.getAttributes().containsKey("idPerfil")) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("AdministradorAtributosSistema.xhtml");
            } catch (IOException ex) {
            }
        }
        elemento = sessionBean.adminFacade.findPerfil((Integer) sessionBean.getAttributes().get("idPerfil"));
        acomodaropciones();


    }

    public Perfil getElemento() {
        return elemento;
    }

    public void setElemento(Perfil elemento) {
        this.elemento = elemento;
    }

    public void guardar() {
        try {
            acomodaropcionesdevuelta();
            sessionBean.adminFacade.guardarPerfiles(elemento);
            sessionBean.actualizarUsuario();
            sessionBean.getAttributes().remove("idPerfil");
            sessionBean.registrarlog("actualizar", "Perfiles", elemento.getNombre());
            FacesUtil.addInfoMessage("Perfil actualizado", elemento.getNombre());
            FacesContext.getCurrentInstance().getExternalContext().redirect("AdministradorAtributosSistema.xhtml");
        } catch (PerfilExistenteException ex) {
        } catch (IOException ex) {
        }
    }

    private void acomodaropciones() {
        for (Formulario f : elemento.getFormulariosList()) {

            if (f.getAccion().equals("crear")) {

                if (f.getTabla().equals("tipodocumento")) {
                    agregartipoDocumento = true;
                }
                if (f.getTabla().equals("Tipotareas")) {
                    agregarTipoevento = true;
                }
                if (f.getTabla().equals("Tareas")) {
                    agregarTareas = true;
                }
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
                if (f.getTabla().equals("Acciones")) {
                    agregarAcciones = true;
                }
            }
            if (f.getAccion().equals("actualizar")) {

                if (f.getTabla().equals("tipodocumento")) {
                    actualizartipoDocumento = true;
                }
                if (f.getTabla().equals("Tipotareas")) {
                    actualizarTipoevento = true;
                }
                if (f.getTabla().equals("Tareas")) {
                    actualizarTareas = true;
                }
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
                if (f.getTabla().equals("Acciones")) {
                    actualizarAcciones = true;
                }
            }
            if (f.getAccion().equals("eliminar")) {

                if (f.getTabla().equals("tipodocumento")) {
                    eliminartipoDocumento = true;
                }
                if (f.getTabla().equals("Tipotareas")) {
                    eliminarTipoevento = true;
                }
                if (f.getTabla().equals("Tareas")) {
                    eliminarTareas = true;
                }
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
                if (f.getTabla().equals("Acciones")) {
                    eliminarAcciones = true;
                }
            }

        }
        for (Vista v : elemento.getVistasList()) {
            if (v.getNombreVista().equals("Tipotareas")) {
                vistaTipoevento = true;
            }
            if (v.getNombreVista().equals("Tareas")) {
                vistaTareas = true;
            }
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
            if (v.getNombreVista().equals("Acciones")) {
                vistaAcciones = true;
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
            if (v.getNombreVista().equals("cupofidelizacion")) {
                cupofidelizacion = true;
            }
            if (v.getNombreVista().equals("tipodocumento")) {
                vistatipoDocumento = true;
            }
            if (v.getNombreVista().equals("notificaciones")) {
                vistaNotificacion = true;
            }
        }
    }

    private void acomodaropcionesdevuelta() {
        Vista v;
        int count = 0;
        elemento.getVistasList().clear();
        if (vistaUsuario == false) {
            count++;
        } else {
            v = sessionBean.adminFacade.findVistasByNombre("Usuarios");
            elemento.getVistasList().add(v);
        }
        if (vistaPerfiles == false) {
            count++;
        } else {
            v = sessionBean.adminFacade.findVistasByNombre("Perfiles");
            elemento.getVistasList().add(v);
        }
        if (vistaVistas == false) {
            count++;
        } else {
            v = sessionBean.adminFacade.findVistasByNombre("Vistas");
            elemento.getVistasList().add(v);
        }
        if (vistaForm == false) {
            count++;
        } else {
            v = sessionBean.adminFacade.findVistasByNombre("Formularios");
            elemento.getVistasList().add(v);
        }
        if (count < 4) {
            v = sessionBean.adminFacade.findVistasByNombre("AtributosSistema");
            elemento.getVistasList().add(v);
        }
        if (vistaEventos == true) {
            v = sessionBean.adminFacade.findVistasByNombre("Eventos");
            elemento.getVistasList().add(v);
        }
        if (vistaTareas == true) {
            v = sessionBean.adminFacade.findVistasByNombre("Tareas");
            elemento.getVistasList().add(v);
        }
        if (vistaClientes == true) {
            v = sessionBean.adminFacade.findVistasByNombre("Clientes");
            elemento.getVistasList().add(v);
        }
        count = 0;
        if (vistaCasinos == false) {
            count++;
        } else {
            v = sessionBean.adminFacade.findVistasByNombre("Casinos");
            elemento.getVistasList().add(v);
        }
        if (vistaAtributos == false) {
            count++;
        } else {
            v = sessionBean.adminFacade.findVistasByNombre("Tipotareas");
            elemento.getVistasList().add(v);
        }
        if (vistaAtributos == false) {
            count++;
        } else {
            v = sessionBean.adminFacade.findVistasByNombre("Atributos");
            elemento.getVistasList().add(v);
        }
        if (vistaTipo == false) {
            count++;
        } else {
            v = sessionBean.adminFacade.findVistasByNombre("TipoJuego");
            elemento.getVistasList().add(v);
        }
        if (vistaCat == false) {
            count++;
        } else {
            v = sessionBean.adminFacade.findVistasByNombre("Categorias");
            elemento.getVistasList().add(v);
        }
        if (vistaAcciones == false) {
            count++;
        } else {
            v = sessionBean.adminFacade.findVistasByNombre("Acciones");
            elemento.getVistasList().add(v);
        }
        if (vistatipoDocumento == false) {
            count++;
        } else {
            v = sessionBean.adminFacade.findVistasByNombre("tipodocumento");
            elemento.getVistasList().add(v);
        }

        if (count < 7) {
            v = sessionBean.adminFacade.findVistasByNombre("AtributosMarketing");
            elemento.getVistasList().add(v);
        }

        if (vistaEvMarketing == true) {
            v = sessionBean.adminFacade.findVistasByNombre("ManejadorEventosMarketing");
            elemento.getVistasList().add(v);
        }
        if (vistaEvHostess == true) {
            v = sessionBean.adminFacade.findVistasByNombre("ManejadorEventosHostess");
            elemento.getVistasList().add(v);
        }
        if (vistaReportes == true) {
            v = sessionBean.adminFacade.findVistasByNombre("Reportes");
            elemento.getVistasList().add(v);
        }
        if (vistaConfiguraciones == true) {
            v = sessionBean.adminFacade.findVistasByNombre("ConfiguracionesGenerales");
            elemento.getVistasList().add(v);
        }
        if (cupofidelizacion == true) {
            v = sessionBean.adminFacade.findVistasByNombre("cupofidelizacion");
            elemento.getVistasList().add(v);
        }
        if (vistaNotificacion == true) {
            v = sessionBean.adminFacade.findVistasByNombre("notificaciones");
            elemento.getVistasList().add(v);
        }

        elemento.getFormulariosList().clear();
        Formulario f;
        if (agregartipoDocumento == true) {
            f = sessionBean.adminFacade.findFormularioByAccionAndTabla("crear", "tipodocumento");
            elemento.getFormulariosList().add(f);
        }
        if (actualizartipoDocumento == true) {
            f = sessionBean.adminFacade.findFormularioByAccionAndTabla("actualizar", "tipodocumento");
            elemento.getFormulariosList().add(f);
        }
        if (eliminartipoDocumento == true) {
            f = sessionBean.adminFacade.findFormularioByAccionAndTabla("eliminar", "tipodocumento");
            elemento.getFormulariosList().add(f);
        }
        if (agregarUsuario == true) {
            f = sessionBean.adminFacade.findFormularioByAccionAndTabla("crear", "Usuarios");
            elemento.getFormulariosList().add(f);
        }
        if (actualizarUsuario == true) {
            f = sessionBean.adminFacade.findFormularioByAccionAndTabla("actualizar", "Usuarios");
            elemento.getFormulariosList().add(f);
        }
        if (eliminarUsuario == true) {
            f = sessionBean.adminFacade.findFormularioByAccionAndTabla("eliminar", "Usuarios");
            elemento.getFormulariosList().add(f);
        }
        if (agregarTareas == true) {
            f = sessionBean.adminFacade.findFormularioByAccionAndTabla("crear", "Tareas");
            elemento.getFormulariosList().add(f);
        }
        if (actualizarTareas == true) {
            f = sessionBean.adminFacade.findFormularioByAccionAndTabla("actualizar", "Tareas");
            elemento.getFormulariosList().add(f);
        }
        if (eliminarTareas == true) {
            f = sessionBean.adminFacade.findFormularioByAccionAndTabla("eliminar", "Tareas");
            elemento.getFormulariosList().add(f);
        }
        if (agregarPerfiles == true) {
            f = sessionBean.adminFacade.findFormularioByAccionAndTabla("crear", "Perfiles");
            elemento.getFormulariosList().add(f);
        }
        if (actualizarPerfiles == true) {
            f = sessionBean.adminFacade.findFormularioByAccionAndTabla("actualizar", "Perfiles");
            elemento.getFormulariosList().add(f);
        }
        if (eliminarPerfiles == true) {
            f = sessionBean.adminFacade.findFormularioByAccionAndTabla("eliminar", "Perfiles");
            elemento.getFormulariosList().add(f);
        }
        if (agregarVistas == true) {
            f = sessionBean.adminFacade.findFormularioByAccionAndTabla("crear", "Vistas");
            elemento.getFormulariosList().add(f);
        }
        if (actualizarVistas == true) {
            f = sessionBean.adminFacade.findFormularioByAccionAndTabla("actualizar", "Vistas");
            elemento.getFormulariosList().add(f);
        }
        if (eliminarVistas == true) {
            f = sessionBean.adminFacade.findFormularioByAccionAndTabla("eliminar", "Vistas");
            elemento.getFormulariosList().add(f);
        }
        if (agregarForm == true) {
            f = sessionBean.adminFacade.findFormularioByAccionAndTabla("crear", "Formularios");
            elemento.getFormulariosList().add(f);
        }
        if (actualizarForm == true) {
            f = sessionBean.adminFacade.findFormularioByAccionAndTabla("actualizar", "Formularios");
            elemento.getFormulariosList().add(f);
        }
        if (eliminarForm == true) {
            f = sessionBean.adminFacade.findFormularioByAccionAndTabla("eliminar", "Formularios");
            elemento.getFormulariosList().add(f);
        }
        if (agregarCasinos == true) {
            f = sessionBean.adminFacade.findFormularioByAccionAndTabla("crear", "Tipotareas");
            elemento.getFormulariosList().add(f);
        }
        if (actualizarCasinos == true) {
            f = sessionBean.adminFacade.findFormularioByAccionAndTabla("actualizar", "Tipotareas");
            elemento.getFormulariosList().add(f);
        }
        if (eliminarCasinos == true) {
            f = sessionBean.adminFacade.findFormularioByAccionAndTabla("eliminar", "Tipotareas");
            elemento.getFormulariosList().add(f);
        }
        if (agregarCasinos == true) {
            f = sessionBean.adminFacade.findFormularioByAccionAndTabla("crear", "Casinos");
            elemento.getFormulariosList().add(f);
        }
        if (actualizarCasinos == true) {
            f = sessionBean.adminFacade.findFormularioByAccionAndTabla("actualizar", "Casinos");
            elemento.getFormulariosList().add(f);
        }
        if (eliminarCasinos == true) {
            f = sessionBean.adminFacade.findFormularioByAccionAndTabla("eliminar", "Casinos");
            elemento.getFormulariosList().add(f);
        }
        if (agregarEventos == true) {
            f = sessionBean.adminFacade.findFormularioByAccionAndTabla("crear", "Eventos");
            elemento.getFormulariosList().add(f);
        }
        if (actualizarEventos == true) {
            f = sessionBean.adminFacade.findFormularioByAccionAndTabla("actualizar", "Eventos");
            elemento.getFormulariosList().add(f);
        }
        if (eliminarEventos == true) {
            f = sessionBean.adminFacade.findFormularioByAccionAndTabla("eliminar", "Eventos");
            elemento.getFormulariosList().add(f);
        }
        if (agregarClientes == true) {
            f = sessionBean.adminFacade.findFormularioByAccionAndTabla("crear", "Clientes");
            elemento.getFormulariosList().add(f);
        }
        if (actualizarClientes == true) {
            f = sessionBean.adminFacade.findFormularioByAccionAndTabla("actualizar", "Clientes");
            elemento.getFormulariosList().add(f);
        }
        if (eliminarClientes == true) {
            f = sessionBean.adminFacade.findFormularioByAccionAndTabla("eliminar", "Clientes");
            elemento.getFormulariosList().add(f);
        }
        if (agregarAtributos == true) {
            f = sessionBean.adminFacade.findFormularioByAccionAndTabla("crear", "Atributos");
            elemento.getFormulariosList().add(f);
        }
        if (actualizarAtributos == true) {
            f = sessionBean.adminFacade.findFormularioByAccionAndTabla("actualizar", "Atributos");
            elemento.getFormulariosList().add(f);
        }
        if (eliminarAtributos == true) {
            f = sessionBean.adminFacade.findFormularioByAccionAndTabla("eliminar", "Atributos");
            elemento.getFormulariosList().add(f);
        }
        if (agregarTipo == true) {
            f = sessionBean.adminFacade.findFormularioByAccionAndTabla("crear", "TiposJuegos");
            elemento.getFormulariosList().add(f);
        }
        if (actualizarTipo == true) {
            f = sessionBean.adminFacade.findFormularioByAccionAndTabla("actualizar", "TiposJuegos");
            elemento.getFormulariosList().add(f);
        }
        if (eliminarTipo == true) {
            f = sessionBean.adminFacade.findFormularioByAccionAndTabla("eliminar", "TiposJuegos");
            elemento.getFormulariosList().add(f);
        }
        if (agregarCat == true) {
            f = sessionBean.adminFacade.findFormularioByAccionAndTabla("crear", "Categorias");
            elemento.getFormulariosList().add(f);
        }
        if (actualizarCat == true) {
            f = sessionBean.adminFacade.findFormularioByAccionAndTabla("actualizar", "Categorias");
            elemento.getFormulariosList().add(f);
        }
        if (eliminarCat == true) {
            f = sessionBean.adminFacade.findFormularioByAccionAndTabla("eliminar", "Categorias");
            elemento.getFormulariosList().add(f);
        }
        if (agregarAcciones == true) {
            f = sessionBean.adminFacade.findFormularioByAccionAndTabla("crear", "Acciones");
            elemento.getFormulariosList().add(f);
        }
        if (actualizarAcciones == true) {
            f = sessionBean.adminFacade.findFormularioByAccionAndTabla("actualizar", "Acciones");
            elemento.getFormulariosList().add(f);
        }
        if (eliminarAcciones == true) {
            f = sessionBean.adminFacade.findFormularioByAccionAndTabla("eliminar", "Acciones");
            elemento.getFormulariosList().add(f);
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

    public boolean isVistaTareas() {
        return vistaTareas;
    }

    public void setVistaTareas(boolean vistaTareas) {
        this.vistaTareas = vistaTareas;
    }

    public boolean isAgregarTareas() {
        return agregarTareas;
    }

    public void setAgregarTareas(boolean agregarTareas) {
        this.agregarTareas = agregarTareas;
    }

    public boolean isActualizarTareas() {
        return actualizarTareas;
    }

    public void setActualizarTareas(boolean actualizarTareas) {
        this.actualizarTareas = actualizarTareas;
    }

    public boolean isEliminarTareas() {
        return eliminarTareas;
    }

    public void setEliminarTareas(boolean eliminarTareas) {
        this.eliminarTareas = eliminarTareas;
    }

    public boolean isVistaAcciones() {
        return vistaAcciones;
    }

    public void setVistaAcciones(boolean vistaAcciones) {
        this.vistaAcciones = vistaAcciones;
    }

    public boolean isAgregarAcciones() {
        return agregarAcciones;
    }

    public void setAgregarAcciones(boolean agregarAcciones) {
        this.agregarAcciones = agregarAcciones;
    }

    public boolean isActualizarAcciones() {
        return actualizarAcciones;
    }

    public void setActualizarAcciones(boolean actualizarAcciones) {
        this.actualizarAcciones = actualizarAcciones;
    }

    public boolean isEliminarAcciones() {
        return eliminarAcciones;
    }

    public void setEliminarAcciones(boolean eliminarAcciones) {
        this.eliminarAcciones = eliminarAcciones;
    }

    public boolean isVistaEstados() {
        return vistaAcciones;
    }

    public void setVistaEstados(boolean vistaEstados) {
        this.vistaAcciones = vistaEstados;
    }

    public boolean isAgregarEstados() {
        return agregarAcciones;
    }

    public void setAgregarEstados(boolean agregarEstados) {
        this.agregarAcciones = agregarEstados;
    }

    public boolean isActualizarEstados() {
        return actualizarAcciones;
    }

    public void setActualizarEstados(boolean actualizarEstados) {
        this.actualizarAcciones = actualizarEstados;
    }

    public boolean isEliminarEstados() {
        return eliminarAcciones;
    }

    public void setEliminarEstados(boolean eliminarEstados) {
        this.eliminarAcciones = eliminarEstados;
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

    public boolean isVistaTipoevento() {
        return vistaTipoevento;
    }

    public void setVistaTipoevento(boolean vistaTipoevento) {
        this.vistaTipoevento = vistaTipoevento;
    }

    public boolean isAgregarTipoevento() {
        return agregarTipoevento;
    }

    public void setAgregarTipoevento(boolean agregarTipoevento) {
        this.agregarTipoevento = agregarTipoevento;
    }

    public boolean isActualizarTipoevento() {
        return actualizarTipoevento;
    }

    public void setActualizarTipoevento(boolean actualizarTipoevento) {
        this.actualizarTipoevento = actualizarTipoevento;
    }

    public boolean isEliminarTipoevento() {
        return eliminarTipoevento;
    }

    public void setEliminarTipoevento(boolean eliminarTipoevento) {
        this.eliminarTipoevento = eliminarTipoevento;
    }

    public boolean isCupofidelizacion() {
        return cupofidelizacion;
    }

    public void setCupofidelizacion(boolean cupofidelizacion) {
        this.cupofidelizacion = cupofidelizacion;
    }

    public boolean isVistatipoDocumento() {
        return vistatipoDocumento;
    }

    public void setVistatipoDocumento(boolean vistatipoDocumento) {
        this.vistatipoDocumento = vistatipoDocumento;
    }

    public boolean isAgregartipoDocumento() {
        return agregartipoDocumento;
    }

    public void setAgregartipoDocumento(boolean agregartipoDocumento) {
        this.agregartipoDocumento = agregartipoDocumento;
    }

    public boolean isActualizartipoDocumento() {
        return actualizartipoDocumento;
    }

    public void setActualizartipoDocumento(boolean actualizartipoDocumento) {
        this.actualizartipoDocumento = actualizartipoDocumento;
    }

    public boolean isEliminartipoDocumento() {
        return eliminartipoDocumento;
    }

    public void setEliminartipoDocumento(boolean eliminartipoDocumento) {
        this.eliminartipoDocumento = eliminartipoDocumento;
    }

    public boolean isVistaNotificacion() {
        return vistaNotificacion;
    }

    public void setVistaNotificacion(boolean vistaNotificacion) {
        this.vistaNotificacion = vistaNotificacion;
    }
}
