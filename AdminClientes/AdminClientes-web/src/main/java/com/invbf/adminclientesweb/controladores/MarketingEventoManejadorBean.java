/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.controladores;

import com.invbf.adminclientesapi.entity.Casino;
import com.invbf.adminclientesapi.entity.Categoria;
import com.invbf.adminclientesapi.entity.Evento;
import com.invbf.adminclientesapi.entity.TipoJuego;
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
    private UploadedFile file;
    private List<Casino> listacasinos;

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
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
        } else {
            elemento = new Evento();
            elemento.setIdCasino(new Casino(0));
            usuarioses = adminFacade.findAllUsuariosHostess();
        }
        listacasinos = marketingUserFacade.findAllCasinos();
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
        fechafinal.setTime(elemento.getFechaFinal());
        if (!fechainicio.before(nowDate) && !fechafinal.before(fechainicio)) {
            elemento = marketingUserFacade.guardarEventos(elemento);
            sessionBean.registrarlog("actualizar", "Eventos", elemento.getNombre());
            if (file != null && file.getContents() != null) {
                marketingUserFacade.guardarImagen(file.getContents(), elemento.getIdEvento(), file.getFileName());
                elemento.setImagen(elemento.getIdEvento() + file.getFileName());
            }
            marketingUserFacade.guardarEventos(elemento);
            sessionBean.actualizarUsuario();
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
