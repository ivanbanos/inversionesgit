/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.controladores;


import com.invbf.sistemagestionclientes.entity.Accion;
import com.invbf.sistemagestionclientes.entity.Categoria;
import com.invbf.sistemagestionclientes.entity.Cliente;
import com.invbf.sistemagestionclientes.entity.Evento;
import com.invbf.sistemagestionclientes.entity.Listasclientestareas;
import com.invbf.sistemagestionclientes.entity.Tarea;
import com.invbf.sistemagestionclientes.entity.TipoJuego;
import com.invbf.sistemagestionclientes.entity.Tipotarea;
import com.invbf.sistemagestionclientes.entity.Usuario;
import com.invbf.sistemagestionclientes.facade.AdminFacade;
import com.invbf.sistemagestionclientes.facade.MarketingUserFacade;
import com.invbf.sistemagestionclientes.facade.SystemFacade;
import com.invbf.sistemagestionclientes.facade.impl.AdminFacadeImpl;
import com.invbf.sistemagestionclientes.facade.impl.MarketingUserFacadeImpl;
import com.invbf.sistemagestionclientes.facade.impl.SystemFacadeImpl;
import com.invbf.sistemagestionclientes.util.CategoriaBoolean;
import com.invbf.sistemagestionclientes.util.FacesUtil;
import com.invbf.sistemagestionclientes.util.TipoJuegoBoolean;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.DualListModel;

/**
 *
 * @author ideacentre
 */
@ManagedBean
@ViewScoped
public class TareaAccionBean {
    private Tarea elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private List<Tipotarea> tipotareas;
    private Evento evento;
    private List<Usuario> usuarioses;
    private List<CategoriaBoolean> categoriasBoolean;
    private List<TipoJuegoBoolean> tipoJuegosBoolean;
    private DualListModel<Usuario> todosusuarioses;
    private boolean todoscat;
    private boolean todostip;
    private long conteo;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public TareaAccionBean() {
    }

    @PostConstruct
    public void init() {
        conteo = 0;
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("tareas");
        if (!sessionBean.perfilViewMatch("Tareas")) {
            try {
                sessionBean.Desconectar();
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        }

        if (sessionBean.getAttributes() == null || !sessionBean.getAttributes().containsKey("idTarea")) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("tareas.xhtml");
            } catch (IOException ex) {
            }
        }
        List<Categoria> categorias = sessionBean.marketingUserFacade.findAllCategorias();
        List<TipoJuego> tipoJuegos = sessionBean.marketingUserFacade.findAllTiposjuegos();
        categoriasBoolean = new ArrayList<CategoriaBoolean>();
        tipoJuegosBoolean = new ArrayList<TipoJuegoBoolean>();
        for (TipoJuego tipoJuego : tipoJuegos) {
            tipoJuegosBoolean.add(new TipoJuegoBoolean(tipoJuego, false));
        }
        for (Categoria categoria : categorias) {
            categoriasBoolean.add(new CategoriaBoolean(categoria, false));
        }
        if (((Integer) sessionBean.getAttributes().get("idTarea")) == 0) {
            elemento = new Tarea();
            if (sessionBean.getAttributes().containsKey("idEvento")) {
                evento = sessionBean.marketingUserFacade.findEvento((Integer) sessionBean.getAttributes().get("idEvento"));
                elemento.setIdEvento(evento);
            }
            elemento.setEstado("Ninguno");
            elemento.setTipo(new Tipotarea(0));
            usuarioses = sessionBean.adminFacade.findAllUsuariosHostess();
            elemento.setListasclientestareasList(new ArrayList<Listasclientestareas>());
            elemento.setUsuarioList(new ArrayList<Usuario>());
            todosusuarioses = new DualListModel<Usuario>(usuarioses, elemento.getUsuarioList());
        } else {
            elemento = sessionBean.marketingUserFacade.findTarea((Integer) sessionBean.getAttributes().get("idTarea"));
            conteo = elemento.getListasclientestareasList().size();
            evento = elemento.getIdEvento();
            if (elemento.getCategorias() == null || elemento.getCategorias().equals("")) {
                elemento.setCategorias("");
            } else {
                for (String s : elemento.getCategorias().split(" ")) {
                    for (CategoriaBoolean cb : categoriasBoolean) {
                        if (cb.compareIdCategorias(Integer.parseInt(s))) {
                            cb.setSelected(true);
                        }
                    }
                }
            }
            if (elemento.getTiposdejuegos() == null || elemento.getTiposdejuegos().equals("")) {
                elemento.setTiposdejuegos("");
            } else {
                for (String s : elemento.getTiposdejuegos().split(" ")) {
                    for (TipoJuegoBoolean tjb : tipoJuegosBoolean) {
                        if (tjb.compareIdTipoJuego(Integer.parseInt(s))) {
                            tjb.setSelected(true);
                        }
                    }
                }
            }
            usuarioses = sessionBean.adminFacade.findAllUsuariosHostess();
            for (Usuario u : elemento.getUsuarioList()) {
                if (usuarioses.contains(u)) {
                    usuarioses.remove(u);
                }
            }
            todosusuarioses = new DualListModel<Usuario>(usuarioses, elemento.getUsuarioList());
        }
        
        tipotareas = sessionBean.marketingUserFacade.findAllTipotarea();
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
            if (elemento.getTipo() == null || elemento.getTipo().getIdTipotarea() == 0) {
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
            elemento = sessionBean.marketingUserFacade.guardarTarea(elemento);
            elemento.setEstado("POR INICIAR");
            if (fechainicio.before(nowDate)) {
                elemento.setEstado("ACTIVO");
            }
            if (fechafinal.before(nowDate)) {
                elemento.setEstado("VENCIDO");
            }
            sessionBean.registrarlog("actualizar", "Eventos", elemento.getNombre());
            sessionBean.marketingUserFacade.guardarTarea(elemento);

            Accion estadoscliente = sessionBean.marketingUserFacade.findByNombreAccion("INICIAL");
            elemento.setUsuarioList(todosusuarioses.getTarget());
            for (Usuario s : todosusuarioses.getTarget()) {
                sessionBean.adminFacade.agregarTareaUsuarios(s, elemento);
            }
            ArrayList<Listasclientestareas> al = new ArrayList<Listasclientestareas>(elemento.getListasclientestareasList());
            elemento.getListasclientestareasList().clear();
            List<Cliente> todosclienteses = sessionBean.marketingUserFacade.findAllClientes();
            boolean noCatselected = true;
            boolean noTipselected = true;
            for (CategoriaBoolean cb : categoriasBoolean) {
                if (todoscat) {
                    cb.setSelected(true);
                    continue;
                }
                if (cb.isSelected()) {
                    noCatselected = false;
                    break;
                }
            }
            for (TipoJuegoBoolean tjb : tipoJuegosBoolean) {
                if (todostip) {
                    tjb.setSelected(true);
                    continue;
                }
                if (tjb.isSelected()) {
                    noTipselected = false;
                    break;
                }
            }
            for (Cliente c : todosclienteses) {

                boolean siCategoria = false;
                boolean siTipoJuego = false;
                if (elemento.getIdEvento() != null) {
                    if (!c.getIdCasinoPreferencial().equals(elemento.getIdEvento().getIdCasino())) {
                        continue;
                    }
                }
                if (noCatselected) {
                    siCategoria = true;
                } else {
                    for (CategoriaBoolean cb : categoriasBoolean) {
                        if (cb.isSelected()) {
                            if (c.getIdCategorias().equals(cb.getCategoria())) {
                                siCategoria = true;
                                break;
                            }
                        }
                    }
                }
                if (noTipselected) {
                    siTipoJuego = true;
                } else {
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

            usuarioses = sessionBean.adminFacade.findAllUsuariosHostess();
            for (Usuario u : elemento.getUsuarioList()) {
                if (usuarioses.contains(u)) {
                    usuarioses.remove(u);
                }
            }
            todosusuarioses = new DualListModel<Usuario>(usuarioses, elemento.getUsuarioList());

            elemento = sessionBean.marketingUserFacade.guardarTarea(elemento);
            if (evento != null) {
                evento.getTareasList().add(elemento);
                sessionBean.marketingUserFacade.guardarEventos(evento);
            }
            FacesUtil.addInfoMessage("Evento guardado con exito", elemento.getNombre());
            goBack();
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
        sessionBean.sessionFacade.enviarCorreo(elemento);
    }

    public boolean isTodoscat() {
        return todoscat;
    }

    public void setTodoscat(boolean todoscat) {
        this.todoscat = todoscat;
    }

    public boolean isTodostip() {
        return todostip;
    }

    public void setTodostip(boolean todostip) {
        this.todostip = todostip;
    }

    public void goBack() {
        try {
            if (sessionBean.getAttributes().get("from").equals("evento")) {
                sessionBean.getAttributes().put("idEvento", elemento.getIdEvento().getIdEvento());
                FacesContext.getCurrentInstance().getExternalContext().redirect("MarketingEventoManejadorView.xhtml");
            } else {
                FacesContext.getCurrentInstance().getExternalContext().redirect("tareas.xhtml");
            }
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(TareaAccionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public long getConteo() {
        return conteo;
    }

    public void setConteo(long conteo) {
        this.conteo = conteo;
    }
    
}
