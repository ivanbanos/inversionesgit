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
import com.invbf.sistemagestionclientes.util.FormularioBoolean;
import com.invbf.sistemagestionclientes.util.VistaBoolean;
import java.io.IOException;
import java.util.List;
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
    private VistaBoolean vistaUsuario;
    private FormularioBoolean agregarUsuario;
    private FormularioBoolean actualizarUsuario;
    private FormularioBoolean eliminarUsuario;
    private VistaBoolean vistaTareas;
    private FormularioBoolean agregarTareas;
    private FormularioBoolean actualizarTareas;
    private FormularioBoolean eliminarTareas;
    private VistaBoolean vistaPerfiles;
    private FormularioBoolean agregarPerfiles;
    private FormularioBoolean actualizarPerfiles;
    private FormularioBoolean eliminarPerfiles;
    private VistaBoolean vistaVistas;
    private FormularioBoolean agregarVistas;
    private FormularioBoolean actualizarVistas;
    private FormularioBoolean eliminarVistas;
    private VistaBoolean vistaForm;
    private FormularioBoolean agregarForm;
    private FormularioBoolean actualizarForm;
    private FormularioBoolean eliminarForm;
    private VistaBoolean vistaCasinos;
    private FormularioBoolean agregarCasinos;
    private FormularioBoolean actualizarCasinos;
    private FormularioBoolean eliminarCasinos;
    private VistaBoolean vistaEventos;
    private FormularioBoolean agregarEventos;
    private FormularioBoolean actualizarEventos;
    private FormularioBoolean eliminarEventos;
    private VistaBoolean vistaClientes;
    private FormularioBoolean agregarClientes;
    private FormularioBoolean actualizarClientes;
    private FormularioBoolean eliminarClientes;
    private VistaBoolean vistaAtributos;
    private FormularioBoolean agregarAtributos;
    private FormularioBoolean actualizarAtributos;
    private FormularioBoolean eliminarAtributos;
    private VistaBoolean vistaTipo;
    private FormularioBoolean agregarTipo;
    private FormularioBoolean actualizarTipo;
    private FormularioBoolean eliminarTipo;
    private VistaBoolean vistaCat;
    private FormularioBoolean agregarCat;
    private FormularioBoolean actualizarCat;
    private FormularioBoolean eliminarCat;
    private VistaBoolean vistaAcciones;
    private FormularioBoolean agregarAcciones;
    private FormularioBoolean actualizarAcciones;
    private FormularioBoolean eliminarAcciones;
    private VistaBoolean vistaTipoevento;
    private FormularioBoolean agregarTipoevento;
    private FormularioBoolean actualizarTipoevento;
    private FormularioBoolean eliminarTipoevento;
    private VistaBoolean vistaEvMarketing;
    private VistaBoolean vistaEvHostess;
    private VistaBoolean vistaReportes;
    private VistaBoolean vistaConfiguraciones;
    private VistaBoolean vistaNotificacion;
    private VistaBoolean cupofidelizacion;
    private VistaBoolean vistatipoDocumento;
    private FormularioBoolean agregartipoDocumento;
    private FormularioBoolean actualizartipoDocumento;
    private FormularioBoolean eliminartipoDocumento;
    private VistaBoolean vistaCargo;
    private FormularioBoolean agregarCargo;
    private FormularioBoolean actualizarCargo;
    private FormularioBoolean eliminarCargo;
    private VistaBoolean vistaDenominacion;
    private FormularioBoolean agregarDenominacion;
    private FormularioBoolean actualizarDenominacion;
    private FormularioBoolean eliminarDenominacion;
    private VistaBoolean vistaAcceso;
    private FormularioBoolean agregarAcceso;
    private FormularioBoolean actualizarAcceso;
    private FormularioBoolean eliminarAcceso;
    private VistaBoolean vistaTipoBono;
    private FormularioBoolean agregarTipoBono;
    private FormularioBoolean actualizarTipoBono;
    private FormularioBoolean eliminarTipoBono;
    private VistaBoolean logs;
    private VistaBoolean aceptarSolictudLotesBons;
    private VistaBoolean vistaSolicitudesLotes;
    private FormularioBoolean agregarSolicitudesLotes;
    private FormularioBoolean actualizarSolicitudesLotes;
    private FormularioBoolean eliminarSolicitudesLotes;
    private VistaBoolean vistaLoteBono;
    private FormularioBoolean agregarLoteBono;
    private FormularioBoolean eliminarLoteBono;

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
            System.out.println("empezando");
            acomodaropcionesdevuelta();

            System.out.println("acomodo");
            sessionBean.adminFacade.guardarPerfiles(elemento);

            System.out.println("guardo");
            sessionBean.actualizarUsuario();
            sessionBean.getAttributes().remove("idPerfil");
            FacesUtil.addInfoMessage("Perfil actualizado", elemento.getNombre());
            FacesContext.getCurrentInstance().getExternalContext().redirect("AdministradorAtributosSistema.xhtml");
        } catch (PerfilExistenteException ex) {
            System.out.println(ex);
        } catch (IOException ex) {

            System.out.println(ex);
        }
    }

    private void acomodaropciones() {
        List<Vista> vistas = sessionBean.adminFacade.findAllVistas();
        List<Formulario> formularios = sessionBean.adminFacade.findAllFormularios();
        for (Formulario f : formularios) {

            if (f.getAccion().equals("crear")) {
                if (f.getTabla().equals("LoteBono")) {
                    
                    if (elemento.getFormulariosList().contains(f)) {
                        agregarLoteBono = new FormularioBoolean(f, true);
                    } else {
                        agregarLoteBono = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("SolicitudLotes")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        agregarSolicitudesLotes = new FormularioBoolean(f, true);
                    } else {
                        agregarSolicitudesLotes = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("acceso")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        agregarAcceso = new FormularioBoolean(f, true);
                    } else {
                        agregarAcceso = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("TipoBono")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        agregarTipoBono = new FormularioBoolean(f, true);
                    } else {
                        agregarTipoBono = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("denominaciones")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        agregarDenominacion = new FormularioBoolean(f, true);
                    } else {
                        agregarDenominacion = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("tipodocumento")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        agregartipoDocumento = new FormularioBoolean(f, true);
                    } else {
                        agregartipoDocumento = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("cargo")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        agregarCargo = new FormularioBoolean(f, true);
                    } else {
                        agregarCargo = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Tipotareas")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        agregarTipoevento = new FormularioBoolean(f, true);
                    } else {
                        agregarTipoevento = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Tareas")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        agregarTareas = new FormularioBoolean(f, true);
                    } else {
                        agregarTareas = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Usuarios")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        agregarUsuario = new FormularioBoolean(f, true);
                    } else {
                        agregarUsuario = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Perfiles")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        agregarPerfiles = new FormularioBoolean(f, true);
                    } else {
                        agregarPerfiles = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Vistas")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        agregarVistas = new FormularioBoolean(f, true);
                    } else {
                        agregarVistas = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Formularios")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        agregarForm = new FormularioBoolean(f, true);
                    } else {
                        agregarForm = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Casinos")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        agregarCasinos = new FormularioBoolean(f, true);
                    } else {
                        agregarCasinos = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Eventos")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        agregarEventos = new FormularioBoolean(f, true);
                    } else {
                        agregarEventos = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Clientes")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        agregarClientes = new FormularioBoolean(f, true);
                    } else {
                        agregarClientes = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Atributos")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        agregarAtributos = new FormularioBoolean(f, true);
                    } else {
                        agregarAtributos = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("TiposJuegos")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        agregarTipo = new FormularioBoolean(f, true);
                    } else {
                        agregarTipo = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Categorias")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        agregarCat = new FormularioBoolean(f, true);
                    } else {
                        agregarCat = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Acciones")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        agregarAcciones = new FormularioBoolean(f, true);
                    } else {
                        agregarAcciones = new FormularioBoolean(f, false);
                    }
                }
            }
            if (f.getAccion().equals("actualizar")) {
                if (f.getTabla().equals("SolicitudLotes")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        actualizarSolicitudesLotes = new FormularioBoolean(f, true);
                    } else {
                        actualizarSolicitudesLotes = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("acceso")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        actualizarAcceso = new FormularioBoolean(f, true);
                    } else {
                        actualizarAcceso = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("TipoBono")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        actualizarTipoBono = new FormularioBoolean(f, true);
                    } else {
                        actualizarTipoBono = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("denominaciones")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        actualizarDenominacion = new FormularioBoolean(f, true);
                    } else {
                        actualizarDenominacion = new FormularioBoolean(f, false);
                    }
                }

                if (f.getTabla().equals("tipodocumento")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        actualizartipoDocumento = new FormularioBoolean(f, true);
                    } else {
                        actualizartipoDocumento = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("cargo")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        actualizarCargo = new FormularioBoolean(f, true);
                    } else {
                        actualizarCargo = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Tipotareas")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        actualizarTipoevento = new FormularioBoolean(f, true);
                    } else {
                        actualizarTipoevento = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Tareas")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        actualizarTareas = new FormularioBoolean(f, true);
                    } else {
                        actualizarTareas = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Usuarios")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        actualizarUsuario = new FormularioBoolean(f, true);
                    } else {
                        actualizarUsuario = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Perfiles")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        actualizarPerfiles = new FormularioBoolean(f, true);
                    } else {
                        actualizarPerfiles = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Vistas")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        actualizarVistas = new FormularioBoolean(f, true);
                    } else {
                        actualizarVistas = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Formularios")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        actualizarForm = new FormularioBoolean(f, true);
                    } else {
                        actualizarForm = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Casinos")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        actualizarCasinos = new FormularioBoolean(f, true);
                    } else {
                        actualizarCasinos = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Eventos")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        actualizarEventos = new FormularioBoolean(f, true);
                    } else {
                        actualizarEventos = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Clientes")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        actualizarClientes = new FormularioBoolean(f, true);
                    } else {
                        actualizarClientes = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Atributos")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        actualizarAtributos = new FormularioBoolean(f, true);
                    } else {
                        actualizarAtributos = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("TiposJuegos")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        actualizarTipo = new FormularioBoolean(f, true);
                    } else {
                        actualizarTipo = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Categorias")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        actualizarCat = new FormularioBoolean(f, true);
                    } else {
                        actualizarCat = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Acciones")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        actualizarAcciones = new FormularioBoolean(f, true);
                    } else {
                        actualizarAcciones = new FormularioBoolean(f, false);
                    }
                }
            }
            if (f.getAccion().equals("eliminar")) {
                if (f.getTabla().equals("SolicitudLotes")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        eliminarSolicitudesLotes = new FormularioBoolean(f, true);
                    } else {
                        eliminarSolicitudesLotes = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("LoteBono")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        eliminarLoteBono = new FormularioBoolean(f, true);
                    } else {
                        eliminarLoteBono = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("acceso")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        eliminarAcceso = new FormularioBoolean(f, true);
                    } else {
                        eliminarAcceso = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("TipoBono")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        eliminarTipoBono = new FormularioBoolean(f, true);
                    } else {
                        eliminarTipoBono = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("denominaciones")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        eliminarDenominacion = new FormularioBoolean(f, true);
                    } else {
                        eliminarDenominacion = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("tipodocumento")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        eliminartipoDocumento = new FormularioBoolean(f, true);
                    } else {
                        eliminartipoDocumento = new FormularioBoolean(f, false);
                    }
                }

                if (f.getTabla().equals("cargo")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        eliminarCargo = new FormularioBoolean(f, true);
                    } else {
                        eliminarCargo = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Tipotareas")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        eliminarTipoevento = new FormularioBoolean(f, true);
                    } else {
                        eliminarTipoevento = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Tareas")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        eliminarTareas = new FormularioBoolean(f, true);
                    } else {
                        eliminarTareas = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Usuarios")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        eliminarUsuario = new FormularioBoolean(f, true);
                    } else {
                        eliminarUsuario = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Perfiles")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        eliminarPerfiles = new FormularioBoolean(f, true);
                    } else {
                        eliminarPerfiles = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Vistas")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        eliminarVistas = new FormularioBoolean(f, true);
                    } else {
                        eliminarVistas = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Formularios")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        eliminarForm = new FormularioBoolean(f, true);
                    } else {
                        eliminarForm = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Casinos")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        eliminarCasinos = new FormularioBoolean(f, true);
                    } else {
                        eliminarCasinos = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Eventos")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        eliminarEventos = new FormularioBoolean(f, true);
                    } else {
                        eliminarEventos = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Clientes")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        eliminarClientes = new FormularioBoolean(f, true);
                    } else {
                        eliminarClientes = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Atributos")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        eliminarAtributos = new FormularioBoolean(f, true);
                    } else {
                        eliminarAtributos = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("TiposJuegos")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        eliminarTipo = new FormularioBoolean(f, true);
                    } else {
                        eliminarTipo = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Categorias")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        eliminarCat = new FormularioBoolean(f, true);
                    } else {
                        eliminarCat = new FormularioBoolean(f, false);
                    }
                }
                if (f.getTabla().equals("Acciones")) {
                    if (elemento.getFormulariosList().contains(f)) {
                        eliminarAcciones = new FormularioBoolean(f, true);
                    } else {
                        eliminarAcciones = new FormularioBoolean(f, false);
                    }
                }
            }

        }
        for (Vista v : vistas) {

            if (v.getNombreVista().equals("TipoBono")) {
                if (elemento.getVistasList().contains(v)) {
                    vistaTipoBono = new VistaBoolean(v, true);
                } else {
                    vistaTipoBono = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("denominaciones")) {
                if (elemento.getVistasList().contains(v)) {
                    vistaDenominacion = new VistaBoolean(v, true);
                } else {
                    vistaDenominacion = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("acceso")) {
                if (elemento.getVistasList().contains(v)) {
                    vistaAcceso = new VistaBoolean(v, true);
                } else {
                    vistaAcceso = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("Tipotareas")) {
                if (elemento.getVistasList().contains(v)) {
                    vistaTipoevento = new VistaBoolean(v, true);
                } else {
                    vistaTipoevento = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("Tareas")) {
                if (elemento.getVistasList().contains(v)) {
                    vistaTareas = new VistaBoolean(v, true);
                } else {
                    vistaTareas = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("Usuarios")) {
                if (elemento.getVistasList().contains(v)) {
                    vistaUsuario = new VistaBoolean(v, true);
                } else {
                    vistaUsuario = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("Perfiles")) {
                if (elemento.getVistasList().contains(v)) {
                    vistaPerfiles = new VistaBoolean(v, true);
                } else {
                    vistaPerfiles = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("Vistas")) {
                if (elemento.getVistasList().contains(v)) {
                    vistaVistas = new VistaBoolean(v, true);
                } else {
                    vistaVistas = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("Formularios")) {
                if (elemento.getVistasList().contains(v)) {
                    vistaForm = new VistaBoolean(v, true);
                } else {
                    vistaForm = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("Casinos")) {
                if (elemento.getVistasList().contains(v)) {
                    vistaCasinos = new VistaBoolean(v, true);
                } else {
                    vistaCasinos = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("Eventos")) {
                if (elemento.getVistasList().contains(v)) {
                    vistaEventos = new VistaBoolean(v, true);
                } else {
                    vistaEventos = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("Clientes")) {
                if (elemento.getVistasList().contains(v)) {
                    vistaClientes = new VistaBoolean(v, true);
                } else {
                    vistaClientes = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("Atributos")) {
                if (elemento.getVistasList().contains(v)) {
                    vistaAtributos = new VistaBoolean(v, true);
                } else {
                    vistaAtributos = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("TipoJuego")) {
                if (elemento.getVistasList().contains(v)) {
                    vistaTipo = new VistaBoolean(v, true);
                } else {
                    vistaTipo = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("Categorias")) {
                if (elemento.getVistasList().contains(v)) {
                    vistaCat = new VistaBoolean(v, true);
                } else {
                    vistaCat = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("Acciones")) {
                if (elemento.getVistasList().contains(v)) {
                    vistaAcciones = new VistaBoolean(v, true);
                } else {
                    vistaAcciones = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("ManejadorEventosMarketing")) {
                if (elemento.getVistasList().contains(v)) {
                    vistaEvMarketing = new VistaBoolean(v, true);
                } else {
                    vistaEvMarketing = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("ManejadorEventosHostess")) {
                if (elemento.getVistasList().contains(v)) {
                    vistaEvHostess = new VistaBoolean(v, true);
                } else {
                    vistaEvHostess = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("Reportes")) {
                if (elemento.getVistasList().contains(v)) {
                    vistaReportes = new VistaBoolean(v, true);
                } else {
                    vistaReportes = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("ConfiguracionesGenerales")) {
                if (elemento.getVistasList().contains(v)) {
                    vistaConfiguraciones = new VistaBoolean(v, true);
                } else {
                    vistaConfiguraciones = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("cupofidelizacion")) {
                if (elemento.getVistasList().contains(v)) {
                    cupofidelizacion = new VistaBoolean(v, true);
                } else {
                    cupofidelizacion = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("tipodocumento")) {
                if (elemento.getVistasList().contains(v)) {
                    vistatipoDocumento = new VistaBoolean(v, true);
                } else {
                    vistatipoDocumento = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("notificaciones")) {
                if (elemento.getVistasList().contains(v)) {
                    vistaNotificacion = new VistaBoolean(v, true);
                } else {
                    vistaNotificacion = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("logs")) {
                if (elemento.getVistasList().contains(v)) {
                    logs = new VistaBoolean(v, true);
                } else {
                    logs = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("AceptarSolicitudLotesBonos")) {
                if (elemento.getVistasList().contains(v)) {
                    aceptarSolictudLotesBons = new VistaBoolean(v, true);
                } else {
                    aceptarSolictudLotesBons = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("SolicitudLotes")) {
                if (elemento.getVistasList().contains(v)) {
                    vistaSolicitudesLotes = new VistaBoolean(v, true);
                } else {
                    vistaSolicitudesLotes = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("LoteBono")) {
                if (elemento.getVistasList().contains(v)) {
                    vistaLoteBono = new VistaBoolean(v, true);
                } else {
                    vistaLoteBono = new VistaBoolean(v, false);
                }
            }
            if (v.getNombreVista().equals("cargo")) {
                if (elemento.getVistasList().contains(v)) {
                    vistaCargo = new VistaBoolean(v, true);
                } else {
                    vistaCargo = new VistaBoolean(v, false);
                }
            }
        }
    }

    private void acomodaropcionesdevuelta() {
        elemento.getVistasList().clear();
        int count = 0;
        if (!vistaDenominacion.isSelected()) {
            count++;
        } else {
            elemento.getVistasList().add(vistaDenominacion.getVista());
        }
        if (!vistaTipoBono.isSelected()) {
            count++;
        } else {
            elemento.getVistasList().add(vistaTipoBono.getVista());
        }
        if (count < 3) {
            elemento.getVistasList().add(sessionBean.adminFacade.findVistasByNombre("atributosbonos"));
        }
        count = 0;
        if (!vistaUsuario.isSelected()) {
            count++;
        } else {
            elemento.getVistasList().add(vistaUsuario.getVista());
        }
        if (!vistaPerfiles.isSelected()) {
            count++;
        } else {
            elemento.getVistasList().add(vistaPerfiles.getVista());
        }
        if (!vistaCargo.isSelected()) {
            count++;
        } else {
            elemento.getVistasList().add(vistaCargo.getVista());
        }
        if (!vistaVistas.isSelected()) {
            count++;
        } else {
            elemento.getVistasList().add(vistaVistas.getVista());
        }
        if (!vistaForm.isSelected()) {
            count++;
        } else {
            elemento.getVistasList().add(vistaForm.getVista());
        }
        if (!vistaAcceso.isSelected()) {
            count++;
        } else {
            elemento.getVistasList().add(vistaAcceso.getVista());
        }
        if (count < 6) {
            elemento.getVistasList().add(sessionBean.adminFacade.findVistasByNombre("AtributosSistema"));
        }
        if (vistaEventos.isSelected()) {
            elemento.getVistasList().add(vistaEventos.getVista());
        }
        if (vistaTareas.isSelected()) {
            elemento.getVistasList().add(vistaTareas.getVista());
        }
        if (vistaClientes.isSelected()) {
            elemento.getVistasList().add(vistaClientes.getVista());
        }
        count = 0;
        if (!vistaCasinos.isSelected()) {
            count++;
        } else {
            elemento.getVistasList().add(vistaCasinos.getVista());
        }
        if (!vistaAtributos.isSelected()) {
            count++;
        } else {
            elemento.getVistasList().add(vistaAtributos.getVista());
        }
        if (!vistaTipo.isSelected()) {
            count++;
        } else {
            elemento.getVistasList().add(vistaTipo.getVista());
        }
        if (!vistaTipoevento.isSelected()) {
            count++;
        } else {
            elemento.getVistasList().add(vistaTipoevento.getVista());
        }
        if (!vistaCat.isSelected()) {
            count++;
        } else {
            elemento.getVistasList().add(vistaCat.getVista());
        }
        if (!vistaAcciones.isSelected()) {
            count++;
        } else {
            elemento.getVistasList().add(vistaAcciones.getVista());
        }
        if (!vistatipoDocumento.isSelected()) {
            count++;
        } else {
            elemento.getVistasList().add(vistatipoDocumento.getVista());
        }

        if (count < 7) {
            elemento.getVistasList().add(sessionBean.adminFacade.findVistasByNombre("AtributosMarketing"));
        }

        if (vistaEvMarketing.isSelected()) {
            elemento.getVistasList().add(vistaEvMarketing.getVista());
        }
        if (vistaEvHostess.isSelected()) {
            elemento.getVistasList().add(vistaEvHostess.getVista());
        }
        if (vistaReportes.isSelected()) {
            elemento.getVistasList().add(vistaReportes.getVista());
        }
        if (vistaConfiguraciones.isSelected()) {
            elemento.getVistasList().add(vistaConfiguraciones.getVista());
        }
        if (cupofidelizacion.isSelected()) {
            elemento.getVistasList().add(cupofidelizacion.getVista());
        }
        if (vistaNotificacion.isSelected()) {
            elemento.getVistasList().add(vistaNotificacion.getVista());
        }
        if (logs.isSelected()) {
            elemento.getVistasList().add(logs.getVista());
        }
        if (aceptarSolictudLotesBons.isSelected()) {
            elemento.getVistasList().add(aceptarSolictudLotesBons.getVista());
        }
        if (vistaSolicitudesLotes.isSelected()) {
            elemento.getVistasList().add(vistaSolicitudesLotes.getVista());
        }
        if (vistaLoteBono.isSelected()) {
            elemento.getVistasList().add(vistaLoteBono.getVista());
        }

        elemento.getFormulariosList().clear();
        if (eliminarAcceso.isSelected()) {
            elemento.getFormulariosList().add(eliminarAcceso.getFormulario());
        }
        if (actualizarAcceso.isSelected()) {
            elemento.getFormulariosList().add(actualizarAcceso.getFormulario());
        }
        if (agregarAcceso.isSelected()) {
            elemento.getFormulariosList().add(agregarAcceso.getFormulario());
        }
        if (agregarTipoBono.isSelected()) {
            elemento.getFormulariosList().add(agregarTipoBono.getFormulario());
        }
        if (actualizarTipoBono.isSelected()) {
            elemento.getFormulariosList().add(actualizarTipoBono.getFormulario());
        }
        if (eliminarTipoBono.isSelected()) {
            elemento.getFormulariosList().add(eliminarTipoBono.getFormulario());
        }
        if (agregarDenominacion.isSelected()) {
            elemento.getFormulariosList().add(agregarDenominacion.getFormulario());
        }
        if (actualizarDenominacion.isSelected()) {
            elemento.getFormulariosList().add(actualizarDenominacion.getFormulario());
        }
        if (eliminarDenominacion.isSelected()) {
            elemento.getFormulariosList().add(eliminarDenominacion.getFormulario());
        }
        if (agregartipoDocumento.isSelected()) {
            elemento.getFormulariosList().add(agregartipoDocumento.getFormulario());
        }
        if (actualizartipoDocumento.isSelected()) {
            elemento.getFormulariosList().add(actualizartipoDocumento.getFormulario());
        }
        if (eliminartipoDocumento.isSelected()) {
            elemento.getFormulariosList().add(eliminartipoDocumento.getFormulario());
        }
        if (agregarCargo.isSelected()) {
            elemento.getFormulariosList().add(agregarCargo.getFormulario());
        }
        if (actualizarCargo.isSelected()) {
            elemento.getFormulariosList().add(actualizarCargo.getFormulario());
        }
        if (eliminarCargo.isSelected()) {
            elemento.getFormulariosList().add(eliminarCargo.getFormulario());
        }
        if (agregarUsuario.isSelected()) {
            elemento.getFormulariosList().add(agregarUsuario.getFormulario());
        }
        if (actualizarUsuario.isSelected()) {
            elemento.getFormulariosList().add(actualizarUsuario.getFormulario());
        }
        if (eliminarUsuario.isSelected()) {
            elemento.getFormulariosList().add(eliminarUsuario.getFormulario());
        }
        
        
        
        if (agregarTipoevento.isSelected()) {
            elemento.getFormulariosList().add(agregarTipoevento.getFormulario());
        }
        if (actualizarTipoevento.isSelected()) {
            elemento.getFormulariosList().add(actualizarTipoevento.getFormulario());
        }
        if (eliminarTipoevento.isSelected()) {
            elemento.getFormulariosList().add(eliminarTipoevento.getFormulario());
        }
        
        
        
        if (agregarTareas.isSelected()) {
            elemento.getFormulariosList().add(agregarTareas.getFormulario());
        }
        if (actualizarTareas.isSelected()) {
            elemento.getFormulariosList().add(actualizarTareas.getFormulario());
        }
        if (eliminarTareas.isSelected()) {
            elemento.getFormulariosList().add(eliminarTareas.getFormulario());
        }
        if (agregarPerfiles.isSelected()) {
            elemento.getFormulariosList().add(agregarPerfiles.getFormulario());
        }
        if (actualizarPerfiles.isSelected()) {
            elemento.getFormulariosList().add(actualizarPerfiles.getFormulario());
        }
        if (eliminarPerfiles.isSelected()) {
            elemento.getFormulariosList().add(eliminarPerfiles.getFormulario());
        }
        if (agregarVistas.isSelected()) {
            elemento.getFormulariosList().add(agregarVistas.getFormulario());
        }
        if (actualizarVistas.isSelected()) {
            elemento.getFormulariosList().add(actualizarVistas.getFormulario());
        }
        if (eliminarVistas.isSelected()) {
            elemento.getFormulariosList().add(eliminarVistas.getFormulario());
        }
        if (agregarForm.isSelected()) {
            elemento.getFormulariosList().add(agregarForm.getFormulario());
        }
        if (actualizarForm.isSelected()) {
            elemento.getFormulariosList().add(actualizarForm.getFormulario());
        }
        if (eliminarForm.isSelected()) {
            elemento.getFormulariosList().add(eliminarForm.getFormulario());
        }
        if (agregarCasinos.isSelected()) {
            elemento.getFormulariosList().add(agregarCasinos.getFormulario());
        }
        if (actualizarCasinos.isSelected()) {
            elemento.getFormulariosList().add(actualizarCasinos.getFormulario());
        }
        if (eliminarCasinos.isSelected()) {
            elemento.getFormulariosList().add(eliminarCasinos.getFormulario());
        }
        if (agregarEventos.isSelected()) {
            elemento.getFormulariosList().add(agregarEventos.getFormulario());
        }
        if (actualizarEventos.isSelected()) {
            elemento.getFormulariosList().add(actualizarEventos.getFormulario());
        }
        if (eliminarEventos.isSelected()) {
            elemento.getFormulariosList().add(eliminarEventos.getFormulario());
        }
        if (agregarClientes.isSelected()) {
            elemento.getFormulariosList().add(agregarClientes.getFormulario());
        }
        if (actualizarClientes.isSelected()) {
            elemento.getFormulariosList().add(actualizarClientes.getFormulario());
        }
        if (eliminarClientes.isSelected()) {
            elemento.getFormulariosList().add(eliminarClientes.getFormulario());
        }
        if (agregarAtributos.isSelected()) {
            elemento.getFormulariosList().add(agregarAtributos.getFormulario());
        }
        if (actualizarAtributos.isSelected()) {
            elemento.getFormulariosList().add(actualizarAtributos.getFormulario());
        }
        if (eliminarAtributos.isSelected()) {
            elemento.getFormulariosList().add(eliminarAtributos.getFormulario());
        }
        if (agregarTipo.isSelected()) {
            elemento.getFormulariosList().add(agregarTipo.getFormulario());
        }
        if (actualizarTipo.isSelected()) {
            elemento.getFormulariosList().add(actualizarTipo.getFormulario());
        }
        if (eliminarTipo.isSelected()) {
            elemento.getFormulariosList().add(eliminarTipo.getFormulario());
        }
        if (agregarCat.isSelected()) {
            elemento.getFormulariosList().add(agregarCat.getFormulario());
        }
        if (actualizarCat.isSelected()) {
            elemento.getFormulariosList().add(actualizarCat.getFormulario());
        }
        if (eliminarCat.isSelected()) {
            elemento.getFormulariosList().add(eliminarCat.getFormulario());
        }
        if (agregarAcciones.isSelected()) {
            elemento.getFormulariosList().add(agregarAcciones.getFormulario());
        }
        if (actualizarAcciones.isSelected()) {
            elemento.getFormulariosList().add(actualizarAcciones.getFormulario());
        }
        if (eliminarAcciones.isSelected()) {
            elemento.getFormulariosList().add(eliminarAcciones.getFormulario());
        }
        if (agregarSolicitudesLotes.isSelected()) {
            elemento.getFormulariosList().add(agregarSolicitudesLotes.getFormulario());
        }
        if (actualizarSolicitudesLotes.isSelected()) {
            elemento.getFormulariosList().add(actualizarSolicitudesLotes.getFormulario());
        }
        if (eliminarSolicitudesLotes.isSelected()) {
            elemento.getFormulariosList().add(eliminarSolicitudesLotes.getFormulario());
        }
        if (agregarLoteBono.isSelected()) {
            elemento.getFormulariosList().add(agregarLoteBono.getFormulario());
        }
        if (eliminarLoteBono.isSelected()) {
            elemento.getFormulariosList().add(eliminarLoteBono.getFormulario());
        }

    }

    public VistaBoolean getVistaUsuario() {
        return vistaUsuario;
    }

    public void setVistaUsuario(VistaBoolean vistaUsuario) {
        this.vistaUsuario = vistaUsuario;
    }

    public VistaBoolean getVistaTareas() {
        return vistaTareas;
    }

    public void setVistaTareas(VistaBoolean vistaTareas) {
        this.vistaTareas = vistaTareas;
    }

    public VistaBoolean getVistaPerfiles() {
        return vistaPerfiles;
    }

    public void setVistaPerfiles(VistaBoolean vistaPerfiles) {
        this.vistaPerfiles = vistaPerfiles;
    }

    public VistaBoolean getVistaVistas() {
        return vistaVistas;
    }

    public void setVistaVistas(VistaBoolean vistaVistas) {
        this.vistaVistas = vistaVistas;
    }

    public VistaBoolean getVistaForm() {
        return vistaForm;
    }

    public void setVistaForm(VistaBoolean vistaForm) {
        this.vistaForm = vistaForm;
    }

    public VistaBoolean getVistaCasinos() {
        return vistaCasinos;
    }

    public void setVistaCasinos(VistaBoolean vistaCasinos) {
        this.vistaCasinos = vistaCasinos;
    }

    public VistaBoolean getVistaEventos() {
        return vistaEventos;
    }

    public void setVistaEventos(VistaBoolean vistaEventos) {
        this.vistaEventos = vistaEventos;
    }

    public VistaBoolean getVistaClientes() {
        return vistaClientes;
    }

    public void setVistaClientes(VistaBoolean vistaClientes) {
        this.vistaClientes = vistaClientes;
    }

    public VistaBoolean getVistaAtributos() {
        return vistaAtributos;
    }

    public void setVistaAtributos(VistaBoolean vistaAtributos) {
        this.vistaAtributos = vistaAtributos;
    }

    public VistaBoolean getVistaTipo() {
        return vistaTipo;
    }

    public void setVistaTipo(VistaBoolean vistaTipo) {
        this.vistaTipo = vistaTipo;
    }

    public VistaBoolean getVistaCat() {
        return vistaCat;
    }

    public void setVistaCat(VistaBoolean vistaCat) {
        this.vistaCat = vistaCat;
    }

    public VistaBoolean getVistaAcciones() {
        return vistaAcciones;
    }

    public void setVistaAcciones(VistaBoolean vistaAcciones) {
        this.vistaAcciones = vistaAcciones;
    }

    public VistaBoolean getVistaTipoevento() {
        return vistaTipoevento;
    }

    public void setVistaTipoevento(VistaBoolean vistaTipoevento) {
        this.vistaTipoevento = vistaTipoevento;
    }

    public VistaBoolean getVistaEvMarketing() {
        return vistaEvMarketing;
    }

    public void setVistaEvMarketing(VistaBoolean vistaEvMarketing) {
        this.vistaEvMarketing = vistaEvMarketing;
    }

    public VistaBoolean getVistaEvHostess() {
        return vistaEvHostess;
    }

    public void setVistaEvHostess(VistaBoolean vistaEvHostess) {
        this.vistaEvHostess = vistaEvHostess;
    }

    public VistaBoolean getVistaReportes() {
        return vistaReportes;
    }

    public void setVistaReportes(VistaBoolean vistaReportes) {
        this.vistaReportes = vistaReportes;
    }

    public VistaBoolean getVistaConfiguraciones() {
        return vistaConfiguraciones;
    }

    public void setVistaConfiguraciones(VistaBoolean vistaConfiguraciones) {
        this.vistaConfiguraciones = vistaConfiguraciones;
    }

    public VistaBoolean getVistaNotificacion() {
        return vistaNotificacion;
    }

    public void setVistaNotificacion(VistaBoolean vistaNotificacion) {
        this.vistaNotificacion = vistaNotificacion;
    }

    public VistaBoolean getCupofidelizacion() {
        return cupofidelizacion;
    }

    public void setCupofidelizacion(VistaBoolean cupofidelizacion) {
        this.cupofidelizacion = cupofidelizacion;
    }

    public VistaBoolean getVistatipoDocumento() {
        return vistatipoDocumento;
    }

    public void setVistatipoDocumento(VistaBoolean vistatipoDocumento) {
        this.vistatipoDocumento = vistatipoDocumento;
    }

    public VistaBoolean getVistaCargo() {
        return vistaCargo;
    }

    public void setVistaCargo(VistaBoolean vistaCargo) {
        this.vistaCargo = vistaCargo;
    }

    public VistaBoolean getLogs() {
        return logs;
    }

    public void setLogs(VistaBoolean logs) {
        this.logs = logs;
    }

    public VistaBoolean getAceptarSolictudLotesBons() {
        return aceptarSolictudLotesBons;
    }

    public void setAceptarSolictudLotesBons(VistaBoolean aceptarSolictudLotesBons) {
        this.aceptarSolictudLotesBons = aceptarSolictudLotesBons;
    }

    public VistaBoolean getVistaSolicitudesLotes() {
        return vistaSolicitudesLotes;
    }

    public void setVistaSolicitudesLotes(VistaBoolean vistaSolicitudesLotes) {
        this.vistaSolicitudesLotes = vistaSolicitudesLotes;
    }

    public FormularioBoolean getAgregarSolicitudesLotes() {
        return agregarSolicitudesLotes;
    }

    public void setAgregarSolicitudesLotes(FormularioBoolean agregarSolicitudesLotes) {
        this.agregarSolicitudesLotes = agregarSolicitudesLotes;
    }

    public FormularioBoolean getActualizarSolicitudesLotes() {
        return actualizarSolicitudesLotes;
    }

    public void setActualizarSolicitudesLotes(FormularioBoolean actualizarSolicitudesLotes) {
        this.actualizarSolicitudesLotes = actualizarSolicitudesLotes;
    }

    public FormularioBoolean getEliminarSolicitudesLotes() {
        return eliminarSolicitudesLotes;
    }

    public void setEliminarSolicitudesLotes(FormularioBoolean eliminarSolicitudesLotes) {
        this.eliminarSolicitudesLotes = eliminarSolicitudesLotes;
    }

    public FormularioBoolean getAgregarUsuario() {
        return agregarUsuario;
    }

    public void setAgregarUsuario(FormularioBoolean agregarUsuario) {
        this.agregarUsuario = agregarUsuario;
    }

    public FormularioBoolean getActualizarUsuario() {
        return actualizarUsuario;
    }

    public void setActualizarUsuario(FormularioBoolean actualizarUsuario) {
        this.actualizarUsuario = actualizarUsuario;
    }

    public FormularioBoolean getEliminarUsuario() {
        return eliminarUsuario;
    }

    public void setEliminarUsuario(FormularioBoolean eliminarUsuario) {
        this.eliminarUsuario = eliminarUsuario;
    }

    public FormularioBoolean getAgregarTareas() {
        return agregarTareas;
    }

    public void setAgregarTareas(FormularioBoolean agregarTareas) {
        this.agregarTareas = agregarTareas;
    }

    public FormularioBoolean getActualizarTareas() {
        return actualizarTareas;
    }

    public void setActualizarTareas(FormularioBoolean actualizarTareas) {
        this.actualizarTareas = actualizarTareas;
    }

    public FormularioBoolean getEliminarTareas() {
        return eliminarTareas;
    }

    public void setEliminarTareas(FormularioBoolean eliminarTareas) {
        this.eliminarTareas = eliminarTareas;
    }

    public FormularioBoolean getAgregarPerfiles() {
        return agregarPerfiles;
    }

    public void setAgregarPerfiles(FormularioBoolean agregarPerfiles) {
        this.agregarPerfiles = agregarPerfiles;
    }

    public FormularioBoolean getActualizarPerfiles() {
        return actualizarPerfiles;
    }

    public void setActualizarPerfiles(FormularioBoolean actualizarPerfiles) {
        this.actualizarPerfiles = actualizarPerfiles;
    }

    public FormularioBoolean getEliminarPerfiles() {
        return eliminarPerfiles;
    }

    public void setEliminarPerfiles(FormularioBoolean eliminarPerfiles) {
        this.eliminarPerfiles = eliminarPerfiles;
    }

    public FormularioBoolean getAgregarVistas() {
        return agregarVistas;
    }

    public void setAgregarVistas(FormularioBoolean agregarVistas) {
        this.agregarVistas = agregarVistas;
    }

    public FormularioBoolean getActualizarVistas() {
        return actualizarVistas;
    }

    public void setActualizarVistas(FormularioBoolean actualizarVistas) {
        this.actualizarVistas = actualizarVistas;
    }

    public FormularioBoolean getEliminarVistas() {
        return eliminarVistas;
    }

    public void setEliminarVistas(FormularioBoolean eliminarVistas) {
        this.eliminarVistas = eliminarVistas;
    }

    public FormularioBoolean getAgregarForm() {
        return agregarForm;
    }

    public void setAgregarForm(FormularioBoolean agregarForm) {
        this.agregarForm = agregarForm;
    }

    public FormularioBoolean getActualizarForm() {
        return actualizarForm;
    }

    public void setActualizarForm(FormularioBoolean actualizarForm) {
        this.actualizarForm = actualizarForm;
    }

    public FormularioBoolean getEliminarForm() {
        return eliminarForm;
    }

    public void setEliminarForm(FormularioBoolean eliminarForm) {
        this.eliminarForm = eliminarForm;
    }

    public FormularioBoolean getAgregarCasinos() {
        return agregarCasinos;
    }

    public void setAgregarCasinos(FormularioBoolean agregarCasinos) {
        this.agregarCasinos = agregarCasinos;
    }

    public FormularioBoolean getActualizarCasinos() {
        return actualizarCasinos;
    }

    public void setActualizarCasinos(FormularioBoolean actualizarCasinos) {
        this.actualizarCasinos = actualizarCasinos;
    }

    public FormularioBoolean getEliminarCasinos() {
        return eliminarCasinos;
    }

    public void setEliminarCasinos(FormularioBoolean eliminarCasinos) {
        this.eliminarCasinos = eliminarCasinos;
    }

    public FormularioBoolean getAgregarEventos() {
        return agregarEventos;
    }

    public void setAgregarEventos(FormularioBoolean agregarEventos) {
        this.agregarEventos = agregarEventos;
    }

    public FormularioBoolean getActualizarEventos() {
        return actualizarEventos;
    }

    public void setActualizarEventos(FormularioBoolean actualizarEventos) {
        this.actualizarEventos = actualizarEventos;
    }

    public FormularioBoolean getEliminarEventos() {
        return eliminarEventos;
    }

    public void setEliminarEventos(FormularioBoolean eliminarEventos) {
        this.eliminarEventos = eliminarEventos;
    }

    public FormularioBoolean getAgregarClientes() {
        return agregarClientes;
    }

    public void setAgregarClientes(FormularioBoolean agregarClientes) {
        this.agregarClientes = agregarClientes;
    }

    public FormularioBoolean getActualizarClientes() {
        return actualizarClientes;
    }

    public void setActualizarClientes(FormularioBoolean actualizarClientes) {
        this.actualizarClientes = actualizarClientes;
    }

    public FormularioBoolean getEliminarClientes() {
        return eliminarClientes;
    }

    public void setEliminarClientes(FormularioBoolean eliminarClientes) {
        this.eliminarClientes = eliminarClientes;
    }

    public FormularioBoolean getAgregarAtributos() {
        return agregarAtributos;
    }

    public void setAgregarAtributos(FormularioBoolean agregarAtributos) {
        this.agregarAtributos = agregarAtributos;
    }

    public FormularioBoolean getActualizarAtributos() {
        return actualizarAtributos;
    }

    public void setActualizarAtributos(FormularioBoolean actualizarAtributos) {
        this.actualizarAtributos = actualizarAtributos;
    }

    public FormularioBoolean getEliminarAtributos() {
        return eliminarAtributos;
    }

    public void setEliminarAtributos(FormularioBoolean eliminarAtributos) {
        this.eliminarAtributos = eliminarAtributos;
    }

    public FormularioBoolean getAgregarTipo() {
        return agregarTipo;
    }

    public void setAgregarTipo(FormularioBoolean agregarTipo) {
        this.agregarTipo = agregarTipo;
    }

    public FormularioBoolean getActualizarTipo() {
        return actualizarTipo;
    }

    public void setActualizarTipo(FormularioBoolean actualizarTipo) {
        this.actualizarTipo = actualizarTipo;
    }

    public FormularioBoolean getEliminarTipo() {
        return eliminarTipo;
    }

    public void setEliminarTipo(FormularioBoolean eliminarTipo) {
        this.eliminarTipo = eliminarTipo;
    }

    public FormularioBoolean getAgregarCat() {
        return agregarCat;
    }

    public void setAgregarCat(FormularioBoolean agregarCat) {
        this.agregarCat = agregarCat;
    }

    public FormularioBoolean getActualizarCat() {
        return actualizarCat;
    }

    public void setActualizarCat(FormularioBoolean actualizarCat) {
        this.actualizarCat = actualizarCat;
    }

    public FormularioBoolean getEliminarCat() {
        return eliminarCat;
    }

    public void setEliminarCat(FormularioBoolean eliminarCat) {
        this.eliminarCat = eliminarCat;
    }

    public FormularioBoolean getAgregarAcciones() {
        return agregarAcciones;
    }

    public void setAgregarAcciones(FormularioBoolean agregarAcciones) {
        this.agregarAcciones = agregarAcciones;
    }

    public FormularioBoolean getActualizarAcciones() {
        return actualizarAcciones;
    }

    public void setActualizarAcciones(FormularioBoolean actualizarAcciones) {
        this.actualizarAcciones = actualizarAcciones;
    }

    public FormularioBoolean getEliminarAcciones() {
        return eliminarAcciones;
    }

    public void setEliminarAcciones(FormularioBoolean eliminarAcciones) {
        this.eliminarAcciones = eliminarAcciones;
    }

    public FormularioBoolean getAgregarTipoevento() {
        return agregarTipoevento;
    }

    public void setAgregarTipoevento(FormularioBoolean agregarTipoevento) {
        this.agregarTipoevento = agregarTipoevento;
    }

    public FormularioBoolean getActualizarTipoevento() {
        return actualizarTipoevento;
    }

    public void setActualizarTipoevento(FormularioBoolean actualizarTipoevento) {
        this.actualizarTipoevento = actualizarTipoevento;
    }

    public FormularioBoolean getEliminarTipoevento() {
        return eliminarTipoevento;
    }

    public void setEliminarTipoevento(FormularioBoolean eliminarTipoevento) {
        this.eliminarTipoevento = eliminarTipoevento;
    }

    public FormularioBoolean getAgregartipoDocumento() {
        return agregartipoDocumento;
    }

    public void setAgregartipoDocumento(FormularioBoolean agregartipoDocumento) {
        this.agregartipoDocumento = agregartipoDocumento;
    }

    public FormularioBoolean getActualizartipoDocumento() {
        return actualizartipoDocumento;
    }

    public void setActualizartipoDocumento(FormularioBoolean actualizartipoDocumento) {
        this.actualizartipoDocumento = actualizartipoDocumento;
    }

    public FormularioBoolean getEliminartipoDocumento() {
        return eliminartipoDocumento;
    }

    public void setEliminartipoDocumento(FormularioBoolean eliminartipoDocumento) {
        this.eliminartipoDocumento = eliminartipoDocumento;
    }

    public FormularioBoolean getAgregarCargo() {
        return agregarCargo;
    }

    public void setAgregarCargo(FormularioBoolean agregarCargo) {
        this.agregarCargo = agregarCargo;
    }

    public FormularioBoolean getActualizarCargo() {
        return actualizarCargo;
    }

    public void setActualizarCargo(FormularioBoolean actualizarCargo) {
        this.actualizarCargo = actualizarCargo;
    }

    public FormularioBoolean getEliminarCargo() {
        return eliminarCargo;
    }

    public void setEliminarCargo(FormularioBoolean eliminarCargo) {
        this.eliminarCargo = eliminarCargo;
    }

    public VistaBoolean getVistaDenominacion() {
        return vistaDenominacion;
    }

    public void setVistaDenominacion(VistaBoolean vistaDenominacion) {
        this.vistaDenominacion = vistaDenominacion;
    }

    public FormularioBoolean getAgregarDenominacion() {
        return agregarDenominacion;
    }

    public void setAgregarDenominacion(FormularioBoolean agregarDenominacion) {
        this.agregarDenominacion = agregarDenominacion;
    }

    public FormularioBoolean getActualizarDenominacion() {
        return actualizarDenominacion;
    }

    public void setActualizarDenominacion(FormularioBoolean actualizarDenominacion) {
        this.actualizarDenominacion = actualizarDenominacion;
    }

    public FormularioBoolean getEliminarDenominacion() {
        return eliminarDenominacion;
    }

    public void setEliminarDenominacion(FormularioBoolean eliminarDenominacion) {
        this.eliminarDenominacion = eliminarDenominacion;
    }

    public VistaBoolean getVistaTipoBono() {
        return vistaTipoBono;
    }

    public void setVistaTipoBono(VistaBoolean vistaTipoBono) {
        this.vistaTipoBono = vistaTipoBono;
    }

    public FormularioBoolean getAgregarTipoBono() {
        return agregarTipoBono;
    }

    public void setAgregarTipoBono(FormularioBoolean agregarTipoBono) {
        this.agregarTipoBono = agregarTipoBono;
    }

    public FormularioBoolean getActualizarTipoBono() {
        return actualizarTipoBono;
    }

    public void setActualizarTipoBono(FormularioBoolean actualizarTipoBono) {
        this.actualizarTipoBono = actualizarTipoBono;
    }

    public FormularioBoolean getEliminarTipoBono() {
        return eliminarTipoBono;
    }

    public void setEliminarTipoBono(FormularioBoolean eliminarTipoBono) {
        this.eliminarTipoBono = eliminarTipoBono;
    }

    public VistaBoolean getVistaAcceso() {
        return vistaAcceso;
    }

    public void setVistaAcceso(VistaBoolean vistaAcceso) {
        this.vistaAcceso = vistaAcceso;
    }

    public FormularioBoolean getAgregarAcceso() {
        return agregarAcceso;
    }

    public void setAgregarAcceso(FormularioBoolean agregarAcceso) {
        this.agregarAcceso = agregarAcceso;
    }

    public FormularioBoolean getActualizarAcceso() {
        return actualizarAcceso;
    }

    public void setActualizarAcceso(FormularioBoolean actualizarAcceso) {
        this.actualizarAcceso = actualizarAcceso;
    }

    public FormularioBoolean getEliminarAcceso() {
        return eliminarAcceso;
    }

    public void setEliminarAcceso(FormularioBoolean eliminarAcceso) {
        this.eliminarAcceso = eliminarAcceso;
    }

    public VistaBoolean getVistaLoteBono() {
        return vistaLoteBono;
    }

    public void setVistaLoteBono(VistaBoolean vistaLoteBono) {
        this.vistaLoteBono = vistaLoteBono;
    }

    public FormularioBoolean getAgregarLoteBono() {
        return agregarLoteBono;
    }

    public void setAgregarLoteBono(FormularioBoolean agregarLoteBono) {
        this.agregarLoteBono = agregarLoteBono;
    }

    public FormularioBoolean getEliminarLoteBono() {
        return eliminarLoteBono;
    }

    public void setEliminarLoteBono(FormularioBoolean eliminarLoteBono) {
        this.eliminarLoteBono = eliminarLoteBono;
    }

}
