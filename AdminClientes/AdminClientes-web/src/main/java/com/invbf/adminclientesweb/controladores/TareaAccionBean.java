/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.controladores;

import com.invbf.adminclientesapi.entity.Accion;
import com.invbf.adminclientesapi.entity.Categoria;
import com.invbf.adminclientesapi.entity.Cliente;
import com.invbf.adminclientesapi.entity.Evento;
import com.invbf.adminclientesapi.entity.Listasclientestareas;
import com.invbf.adminclientesapi.entity.Tarea;
import com.invbf.adminclientesapi.entity.TipoJuego;
import com.invbf.adminclientesapi.entity.Tipotarea;
import com.invbf.adminclientesapi.entity.Usuario;
import com.invbf.adminclientesapi.facade.AdminFacade;
import com.invbf.adminclientesapi.facade.MarketingUserFacade;
import com.invbf.adminclientesapi.facade.SystemFacade;
import com.invbf.adminclientesweb.util.CategoriaBoolean;
import com.invbf.adminclientesweb.util.FacesUtil;
import com.invbf.adminclientesweb.util.TipoJuegoBoolean;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;
import org.primefaces.model.DualListModel;

/**
 *
 * @author ideacentre
 */
@ManagedBean
@ViewScoped
public class TareaAccionBean {

    private static final Logger LOGGER = Logger.getLogger(SessionBean.class);
    private Tarea elemento;
    @EJB
    MarketingUserFacade marketingUserFacade;
    @EJB
    AdminFacade adminFacade;
    @EJB
    SystemFacade systemFacade;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private List<Tipotarea> tipotareas;
    private Evento evento;
    private List<Usuario> usuarioses;
    private List<CategoriaBoolean> categoriasBoolean;
    private List<TipoJuegoBoolean> tipoJuegosBoolean;
    private DualListModel<Usuario> todosusuarioses;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public TareaAccionBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("tareas");
        if (!sessionBean.perfilViewMatch("Tareas")) {
            try {
                sessionBean.Desconectar();
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
                LOGGER.error(ex);
            }
        }

        if (sessionBean.getAttributes() == null || !sessionBean.getAttributes().containsKey("idTarea")) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("tareas.xhtml");
            } catch (IOException ex) {
                LOGGER.error(ex);
            }
        }
        List<Categoria> categorias = marketingUserFacade.findAllCategorias();
        List<TipoJuego> tipoJuegos = marketingUserFacade.findAllTiposjuegos();
        categoriasBoolean = new ArrayList<CategoriaBoolean>();
        tipoJuegosBoolean = new ArrayList<TipoJuegoBoolean>();
        for (TipoJuego tipoJuego : tipoJuegos) {
            tipoJuegosBoolean.add(new TipoJuegoBoolean(tipoJuego, false));
        }
        for (Categoria categoria : categorias) {
            categoriasBoolean.add(new CategoriaBoolean(categoria, false));
        }
        if ((Integer) sessionBean.getAttributes().get("idTarea") == 0) {
            elemento = new Tarea();
            if (sessionBean.getAttributes().containsKey("idEvento")) {
                evento = marketingUserFacade.findEvento((Integer) sessionBean.getAttributes().get("idEvento"));
                elemento.setIdEvento(evento);
            }
            elemento.setEstado("Ninguno");
            elemento.setTipo(new Tipotarea(0));
            usuarioses = adminFacade.findAllUsuariosHostess();
            elemento.setListasclientestareasList(new ArrayList<Listasclientestareas>());
            elemento.setUsuarioList(new ArrayList<Usuario>());
            todosusuarioses = new DualListModel<Usuario>(usuarioses, elemento.getUsuarioList());
        } else {
            elemento = marketingUserFacade.findTarea((Integer) sessionBean.getAttributes().get("idTarea"));
            evento = elemento.getIdEvento();
            if (elemento.getCategorias() == null) {
                elemento.setCategorias("");
            }
            if (elemento.getTiposdejuegos() == null) {
                elemento.setTiposdejuegos("");
            }
            for (String s : elemento.getCategorias().split(" ")) {
                for (CategoriaBoolean cb : categoriasBoolean) {
                    if (cb.compareIdCategorias(Integer.parseInt(s))) {
                        cb.setSelected(true);
                    }
                }
            }
            for (String s : elemento.getTiposdejuegos().split(" ")) {
                for (TipoJuegoBoolean tjb : tipoJuegosBoolean) {
                    if (tjb.compareIdTipoJuego(Integer.parseInt(s))) {
                        tjb.setSelected(true);
                    }
                }
            }
            usuarioses = adminFacade.findAllUsuariosHostess();
            for (Usuario u : elemento.getUsuarioList()) {
                if (usuarioses.contains(u)) {
                    usuarioses.remove(u);
                }
            }
            todosusuarioses = new DualListModel<Usuario>(usuarioses, elemento.getUsuarioList());
        }
        tipotareas = marketingUserFacade.findAllTipotarea();
    }

    public Tarea getElemento() {
        return elemento;
    }

    public void setElemento(Tarea elemento) {
        this.elemento = elemento;
    }

    public void guardar() {
        guardar:
        {
            if (elemento.getTipo() == null) {
                FacesUtil.addErrorMessage("Elemento no creado", "Debe seleccionar un tipo de tarea");
                break guardar;
            }
            Calendar fechainicio = Calendar.getInstance();
            Calendar fechafinal = Calendar.getInstance();
            Calendar nowDate = Calendar.getInstance();
            fechainicio.setTime(elemento.getFechaInicio());
            fechafinal.setTime(elemento.getFechaFinalizacion());
            if (elemento.getIdTarea() == null || elemento.getIdTarea() == 0) {
                if (fechainicio.before(nowDate)) {
                    FacesUtil.addErrorMessage("Fehas incorrectas", "Fecha inicial antes de la fecha actual");
                    break guardar;
                } else if (fechafinal.before(fechainicio)) {
                    FacesUtil.addErrorMessage("Fehas incorrectas", "Fecha final antes de la fecha inicial");
                    break guardar;
                }
            }
            elemento = marketingUserFacade.guardarTarea(elemento);
            elemento.setEstado("POR INICIAR");
            if (fechainicio.before(nowDate)) {
                elemento.setEstado("ACTIVO");
            }
            if (fechafinal.before(nowDate)){
                elemento.setEstado("VENCIDO");
            }
            sessionBean.registrarlog("actualizar", "Eventos", elemento.getNombre());
            marketingUserFacade.guardarTarea(elemento);

            Accion estadoscliente = marketingUserFacade.findByNombreAccion("INICIAL");
            elemento.setUsuarioList(todosusuarioses.getTarget());
            for (Usuario s : todosusuarioses.getTarget()) {
                adminFacade.agregarTareaUsuarios(s, elemento);
            }
            ArrayList<Listasclientestareas> al = new ArrayList<Listasclientestareas>(elemento.getListasclientestareasList());
            elemento.getListasclientestareasList().clear();
            List<Cliente> todosclienteses = marketingUserFacade.findAllClientes();
            for (Cliente c : todosclienteses) {
                boolean siCategoria = false;
                boolean siTipoJuego = false;

                for (CategoriaBoolean cb : categoriasBoolean) {
                    if (cb.isSelected()) {
                        if (c.getIdCategorias().equals(cb.getCategoria())) {
                            siCategoria = true;
                            break;
                        }
                    }
                }

                for (TipoJuegoBoolean tjb : tipoJuegosBoolean) {
                    if (tjb.isSelected()) {
                        for (TipoJuego tj : c.getTiposjuegosList()) {
                            if (tj.equals(tjb.getTipoJuego())) {
                                siTipoJuego = true;
                                break;
                            }
                        }
                    }
                }
                if (siCategoria && siTipoJuego) {
                    Listasclientestareas listasclientesevento = new Listasclientestareas(elemento.getIdTarea(), c.getIdCliente());
                    if (al.contains(listasclientesevento)) {
                        elemento.getListasclientestareasList().add(al.get(al.indexOf(listasclientesevento)));
                    } else {
                        listasclientesevento.setIdAccion(estadoscliente);
                        listasclientesevento.setTareas(elemento);
                        listasclientesevento.setCliente(c);
                        elemento.getListasclientestareasList().add(listasclientesevento);
                    }
                } else {
                    Listasclientestareas listasclientesevento = new Listasclientestareas(elemento.getIdTarea(), c.getIdCliente());
                    if (al.contains(listasclientesevento)) {
                        elemento.getListasclientestareasList().remove(al.get(al.indexOf(listasclientesevento)));
                    }
                }
            }
            elemento.setCategorias("");
            for (CategoriaBoolean cb : categoriasBoolean) {
                if (cb.isSelected()) {
                    elemento.setCategorias(elemento.getCategorias() + cb.getCategoria().getIdCategorias() + " ");
                }
            }

            elemento.setTiposdejuegos("");
            for (TipoJuegoBoolean tjb : tipoJuegosBoolean) {
                if (tjb.isSelected()) {
                    elemento.setTiposdejuegos(elemento.getTiposdejuegos() + tjb.getTipoJuego().getIdTipoJuego() + " ");
                }
            }
            sessionBean.actualizarUsuario();

            usuarioses = adminFacade.findAllUsuariosHostess();
            for (Usuario u : elemento.getUsuarioList()) {
                if (usuarioses.contains(u)) {
                    usuarioses.remove(u);
                }
            }
            todosusuarioses = new DualListModel<Usuario>(usuarioses, elemento.getUsuarioList());
            elemento = marketingUserFacade.guardarTarea(elemento);
            FacesUtil.addInfoMessage("Evento guardado con exito", elemento.getNombre());

        }
    }

    public List<Tipotarea> getTipotareas() {
        return tipotareas;
    }

    public void setTipotareas(List<Tipotarea> tipotareas) {
        this.tipotareas = tipotareas;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public List<CategoriaBoolean> getCategoriasBoolean() {
        return categoriasBoolean;
    }

    public void setCategoriasBoolean(List<CategoriaBoolean> categoriasBoolean) {
        this.categoriasBoolean = categoriasBoolean;
    }

    public List<TipoJuegoBoolean> getTipoJuegosBoolean() {
        return tipoJuegosBoolean;
    }

    public void setTipoJuegosBoolean(List<TipoJuegoBoolean> tipoJuegosBoolean) {
        this.tipoJuegosBoolean = tipoJuegosBoolean;
    }

    public DualListModel<Usuario> getTodosusuarioses() {
        return todosusuarioses;
    }

    public void setTodosusuarioses(DualListModel<Usuario> todosusuarioses) {
        this.todosusuarioses = todosusuarioses;
    }

    public void enviarCorreo() {
        systemFacade.enviarCorreo(elemento);
    }
}
