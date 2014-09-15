/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.controladores;

import com.invbf.adminclientesapi.entity.Casino;
import com.invbf.adminclientesapi.entity.Categoria;
import com.invbf.adminclientesapi.entity.Cliente;
import com.invbf.adminclientesapi.entity.Clienteevento;
import com.invbf.adminclientesapi.entity.Estadocliente;
import com.invbf.adminclientesapi.entity.Evento;
import com.invbf.adminclientesapi.entity.TipoJuego;
import com.invbf.adminclientesapi.entity.Tipoevento;
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
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author ideacentre
 */
@ManagedBean
@ViewScoped
public class MarketingEventoManejadorBean {

    private static final Logger LOGGER = Logger.getLogger(SessionBean.class);
    @EJB
    MarketingUserFacade marketingUserFacade;
    @EJB
    SystemFacade systemFacade;
    @EJB
    AdminFacade adminFacade;
    private Evento elemento;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private List<Usuario> usuarioses;
    private List<CategoriaBoolean> categoriasBoolean;
    private List<TipoJuegoBoolean> tipoJuegosBoolean;
    private DualListModel<Usuario> todosusuarioses;
    private List<Tipoevento> tiposeventos;
    private UploadedFile file;
    private List<Casino> listacasinos;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }
    private List<Clienteevento> flista;

    public List<Clienteevento> getFlista() {
        return flista;
    }

    public void setFlista(List<Clienteevento> flista) {
        this.flista = flista;
    }

    /**
     * Creates a new instance of AtributosSistemaViewBean
     */
    public MarketingEventoManejadorBean() {
    }

    @PostConstruct
    public void init() {
        sessionBean.checkUsuarioConectado();
        sessionBean.setActive("eventos");
        if (!sessionBean.perfilViewMatch("ManejadorEventosMarketing")
                && !sessionBean.perfilFormMatch("Eventos", "crear")
                && !sessionBean.perfilFormMatch("Eventos", "actualizar")) {
            try {
                sessionBean.Desconectar();
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
                LOGGER.error(ex);
            }
        }

        if (sessionBean.getAttributes() == null || !sessionBean.getAttributes().containsKey("idEvento")) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("eventos.xhtml");
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
        if ((Integer) sessionBean.getAttributes().get("idEvento") != 0) {
            elemento = marketingUserFacade.findEvento((Integer) sessionBean.getAttributes().get("idEvento"));
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
            for (Usuario u : elemento.getUsuariosList()) {
                if (usuarioses.contains(u)) {
                    usuarioses.remove(u);
                }
            }
            todosusuarioses = new DualListModel<Usuario>(usuarioses, elemento.getUsuariosList());
        } else {
            elemento = new Evento();
            elemento.setEstado("Ninguno");
            elemento.setIdCasino(new Casino(0));
            elemento.setTipo(new Tipoevento(0));
            usuarioses = adminFacade.findAllUsuariosHostess();
            elemento.setListasclienteseventoList(new ArrayList<Clienteevento>());
            elemento.setUsuariosList(new ArrayList<Usuario>());
            todosusuarioses = new DualListModel<Usuario>(usuarioses, elemento.getUsuariosList());
        }
        listacasinos = marketingUserFacade.findAllCasinos();
        tiposeventos = marketingUserFacade.findAllTipoevento();
    }

    public Evento getElemento() {
        return elemento;
    }

    public void setElemento(Evento elemento) {
        this.elemento = elemento;
    }

    public MarketingUserFacade getMarketingUserFacade() {
        return marketingUserFacade;
    }

    public void setMarketingUserFacade(MarketingUserFacade marketingUserFacade) {
        this.marketingUserFacade = marketingUserFacade;
    }

    public void guardar() {
        Calendar fechainicio = Calendar.getInstance();
        Calendar fechafinal = Calendar.getInstance();
        Calendar nowDate = Calendar.getInstance();
        fechainicio.setTime(elemento.getFechaInicio());
        fechafinal.setTime(elemento.getFechaFinalizacion());
        if (!fechainicio.before(nowDate) && !fechafinal.before(fechainicio)) {
            elemento = marketingUserFacade.guardarEventos(elemento);
            elemento.setEstado("Por iniciar");
            if (fechainicio.get(Calendar.DAY_OF_YEAR) == nowDate.get(Calendar.DAY_OF_YEAR)) {
                elemento.setEstado("Activo");
            }
            sessionBean.registrarlog("actualizar", "Eventos", elemento.getNombre());
            if (file != null && file.getContents() != null) {
                marketingUserFacade.guardarImagen(file.getContents(), elemento.getIdEvento(), file.getFileName());
                elemento.setImagen(elemento.getIdEvento() + file.getFileName());
            }
            marketingUserFacade.guardarEventos(elemento);

            Estadocliente estadoscliente = marketingUserFacade.findByNombreEstadoCliente("Inicial");
            elemento.setUsuariosList(todosusuarioses.getTarget());
            for (Usuario s : todosusuarioses.getTarget()) {
                adminFacade.agregarEventoUsuarios(s, elemento);
            }
            ArrayList<Clienteevento> al = new ArrayList<Clienteevento>(elemento.getListasclienteseventoList());
            elemento.getListasclienteseventoList().clear();
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
                    Clienteevento listasclientesevento = new Clienteevento(elemento.getIdEvento(), c.getIdCliente());
                    if (al.contains(listasclientesevento)) {
                        elemento.getListasclienteseventoList().add(al.get(al.indexOf(listasclientesevento)));
                    } else {
                        listasclientesevento.setIdEstadoCliente(estadoscliente);
                        listasclientesevento.setEventos(elemento);
                        listasclientesevento.setClientes(c);
                        elemento.getListasclienteseventoList().add(listasclientesevento);
                    }
                } else {
                    Clienteevento listasclientesevento = new Clienteevento(elemento.getIdEvento(), c.getIdCliente());
                    if (al.contains(listasclientesevento)) {
                        elemento.getListasclienteseventoList().remove(al.get(al.indexOf(listasclientesevento)));
                    }
                }
            }
            elemento.setCategorias("");
            for (CategoriaBoolean cb : categoriasBoolean) {
                if (cb.isSelected()) {
                    elemento.setCategorias(elemento.getCategorias()+cb.getCategoria().getIdCategorias()+" ");
                }
            }
            
            elemento.setTiposdejuegos("");
            for (TipoJuegoBoolean tjb : tipoJuegosBoolean) {
                if (tjb.isSelected()) {
                    elemento.setTiposdejuegos(elemento.getTiposdejuegos()+tjb.getTipoJuego().getIdTipoJuego()+" ");
                }
            }
            sessionBean.actualizarUsuario();

            usuarioses = adminFacade.findAllUsuariosHostess();
            for (Usuario u : elemento.getUsuariosList()) {
                if (usuarioses.contains(u)) {
                    usuarioses.remove(u);
                }
            }
            todosusuarioses = new DualListModel<Usuario>(usuarioses, elemento.getUsuariosList());
            elemento = marketingUserFacade.guardarEventos(elemento);
            FacesUtil.addInfoMessage("Evento guardado con exito", elemento.getNombre());
        } else {
            if (fechainicio.before(nowDate)) {
                FacesUtil.addErrorMessage("Fehas incorrectas", "Fecha inicial antes de la fecha actual");
            } else {
                FacesUtil.addErrorMessage("Fehas incorrectas", "Fecha final antes de la fecha inicial");
            }
        }
    }

    public AdminFacade getAdminFacade() {
        return adminFacade;
    }

    public void setAdminFacade(AdminFacade adminFacade) {
        this.adminFacade = adminFacade;
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

    public List<Tipoevento> getTiposeventos() {
        return tiposeventos;
    }

    public void setTiposeventos(List<Tipoevento> tiposeventos) {
        this.tiposeventos = tiposeventos;
    }

    public List<Casino> getListacasinos() {
        return listacasinos;
    }

    public void setListacasinos(List<Casino> listacasinos) {
        this.listacasinos = listacasinos;
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

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void handleFileUpload(FileUploadEvent event) {
        if (event != null) {
            file = event.getFile();
            FacesUtil.addInfoMessage(event.getFile().getFileName());
        }
    }
}
